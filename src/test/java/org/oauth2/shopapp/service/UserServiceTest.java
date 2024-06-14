package org.oauth2.shopapp.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oauth2.shopapp.dto.request.UserDTO;
import org.oauth2.shopapp.dto.response.UserResponse;
import org.oauth2.shopapp.entity.Roles;
import org.oauth2.shopapp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    private MockMvc mockMvc;

    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private UserDTO request;
    private UserResponse response;

    @BeforeEach
    void initData() {
//        request = UserDTO.builder().
//                userName("hieu")
//                .email("java@gmail.com")
//                .password("hieu123").
//                build();
        System.out.println("Set Up Parameter Test");

    }
    @AfterEach
    void afterTest(){
        System.out.println("Set Up Parameter Test");

    }
    @Test
    void createUserSuccess()throws Exception{

//        response = UserResponse.builder().
//                userName("hieu")
//                .email("java@gmail.com")
//                .avatar("https://iconape.com/wp-content/png_logo_vector/avatar-10.png").
//
//                build();
        System.out.println("First Test");

    }

    @Test
    void createUser() {
        System.out.println("Second Test");
    }
}
