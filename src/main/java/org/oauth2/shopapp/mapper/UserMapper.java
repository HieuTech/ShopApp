package org.oauth2.shopapp.mapper;

import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.RoleName;
import org.oauth2.shopapp.dto.request.UserDTO;
import org.oauth2.shopapp.dto.response.UserResponse;
import org.oauth2.shopapp.entity.Roles;
import org.oauth2.shopapp.entity.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    final PasswordEncoder passwordEncoder;

    public Users toUser(UserDTO userDTO) {
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(Roles.builder().roleName(RoleName.ROLE_USER).build());
        return Users.builder().
                userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail()).rolesList(rolesList).
        createdAt(new Date()).
                is_active(true).
                build();
    }


    public UserResponse toUserResponse(Users users) {


        List<String> roles = users.getRolesList().stream().map(role -> role.getRoleName().name()).collect(Collectors.toList());

        return UserResponse.builder().
                userName(users.getUserName())
                .avatar("https://iconape.com/wp-content/png_logo_vector/avatar-10.png")
                .rolesList(roles).
                email(users.getEmail()).
                build();
    }
}
