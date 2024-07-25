package com.example.book_api.controllers.api.user;


import com.example.book_api.configurations.TokenUtil;
import com.example.book_api.models.dto.LoginDto;
import com.example.book_api.models.dto.UserDto;
import com.example.book_api.models.otd.JwtResponse;
import com.example.book_api.models.otd.MyResponse;
import com.example.book_api.models.otd.UserResponse;
import com.example.book_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth/user")
public class AuthUserController {

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto user) {
                userService.saveUser(user);
                return new ResponseEntity<>(new MyResponse("Registered Successfully", true), HttpStatus.OK);

    }


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginRequest) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());

            if (!userDetails.isEnabled()) {
                return new ResponseEntity<Object>(new MyResponse("This account is not activated!!! Please check your email to activate the verification code.",
                        false), HttpStatus.OK);
            } else {
                final Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);



                String token = tokenUtil.generateToken(userDetails);
                UserResponse user = UserResponse.to_Dto(userService.findByEmail(userDetails.getUsername()));

                JwtResponse response = new JwtResponse(token, user);
                return new ResponseEntity<Object>(new MyResponse("Logged in Successfully", true, response), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MyResponse(e.getMessage(), false), HttpStatus.OK);

        }
    }




}
