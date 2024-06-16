package org.oauth2.shopapp.service;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.EmailManagement;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.UserDTO;
import org.oauth2.shopapp.dto.response.UserResponse;
import org.oauth2.shopapp.entity.Users;
import org.oauth2.shopapp.exception.MongoExceptionHandler;
import org.oauth2.shopapp.exception.NotFoundException;
import org.oauth2.shopapp.exception.UserReadyExistException;
import org.oauth2.shopapp.mapper.UserMapper;
import org.oauth2.shopapp.repository.IUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository  iUserRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;


    public UserResponse createUser(UserDTO userDTO) {
        UserResponse userResponse = null;
        //Kiem tra ton tai chua
        if (iUserRepository.existsByEmail(userDTO.getEmail()))
            throw new UserReadyExistException(ErrorDetail.USER_EXISTED);
        try {
            userResponse = userMapper.toUserResponse(iUserRepository.insert(userMapper.toUser(userDTO)));
            emailService.sendMail(userDTO.getEmail(), EmailManagement.REGISTER_SUBJECT,EmailManagement.registerSuccess(userDTO.getUserName(), userDTO.getEmail()));

        }catch (MongoException exception){
            throw new MongoExceptionHandler(ErrorDetail.DATABASE_ERROR);
        }
        return userResponse;
        //Neu chua thi luu roleUser,  chuyen doi ve User va luu vao DB
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        Users user = iUserRepository.findByEmail(name).orElseThrow(()-> new NotFoundException(ErrorDetail.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers(){
        return iUserRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUserById(String id){
        Users users = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorDetail.USER_NOT_EXISTED)) ;
        return userMapper.toUserResponse(users);
    }

    public String blockUser(String id){
        Users users = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorDetail.USER_NOT_EXISTED)) ;
        users.setIs_active(false);
        iUserRepository.save(users);
        return "Blocked";
    }
    public String unBlockUser(String id){
        Users users = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorDetail.USER_NOT_EXISTED)) ;
        users.setIs_active(true);
        iUserRepository.save(users);
        return "UnBlocked";
    }

}
