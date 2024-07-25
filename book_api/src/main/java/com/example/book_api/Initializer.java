package com.example.book_api;

import com.example.book_api.models.dto.UserDto;
import com.example.book_api.models.entities.Role;
import com.example.book_api.services.RoleService;
import com.example.book_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    // Save Role Users when runtime
    @Override
    public void run(String... args) throws Exception {

       if(roleService.findAll().isEmpty()) {
           Role role_Admin = new Role();
           role_Admin.setRole("Admin");
           roleService.save(role_Admin);

           Role role_User = new Role();
           role_User.setRole("User");
           roleService.save(role_User);



       }

        //____________Save Admin User_____

        if(userService.findAll().isEmpty()) {
            UserDto admin = new UserDto();
            admin.setUsername("Admin");
            admin.setPassword("12345678");
            admin.setEmail("admin@gmail.com");
            userService.saveAdmin(admin);
        }


    }
}
