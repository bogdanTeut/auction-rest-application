package com.teutit.auction.controller;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return userService.save(user);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getMessage();
    }
}
