package com.example.book_api.controllers;

import com.example.book_api.models.entities.User;
import com.example.book_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    @Autowired
    private UserService userService;
    public User getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  userService.findByEmail(  authentication.getName());
    }
}
