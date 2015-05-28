package com.teutit.auction.controller;

import com.teutit.auction.domain.User;
import com.teutit.auction.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    
    @Inject
    public UserController(UserService userService) {
        this.userService = userService;        
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody @Valid final User user) {
        return userService.saveUser(user);
    }
}
