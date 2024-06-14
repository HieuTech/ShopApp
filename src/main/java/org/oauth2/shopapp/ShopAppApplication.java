package org.oauth2.shopapp;

import org.oauth2.shopapp.constant.RoleName;
import org.oauth2.shopapp.entity.Roles;
import org.oauth2.shopapp.entity.Users;
import org.oauth2.shopapp.repository.IRolesRepository;
import org.oauth2.shopapp.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ShopAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAppApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(
            IUserRepository userRepository
            , IRolesRepository rolesRepository

    ) {
        return args ->
        {
            var role = Roles.builder()
                    .roleName(RoleName.ROLE_ADMIN)
                    .build();
            var role1 = Roles.builder()
                    .roleName(RoleName.ROLE_USER)
                    .build();
            rolesRepository.insert(role);
            rolesRepository.insert(role1);


            List<Roles> roles = new ArrayList<>();
            roles.add(rolesRepository.findByRoleName(RoleName.ROLE_USER));
            var user = Users.builder().address("150 Nguyen Van Cu")
                    .createdAt(new Date())

                    .rolesList(roles).
                    build();
            userRepository.insert(user);
        };
    }
}
