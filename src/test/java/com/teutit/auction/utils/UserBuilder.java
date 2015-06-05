package com.teutit.auction.utils;

import com.teutit.auction.domain.User;

public class UserBuilder {
    private User user = new User();

    public UserBuilder(String id) {
        user.setId(id);
    }
    
    public UserBuilder password(String password){
        user.setPassword(password);
        return this;
    }
    
    public User build(){
        return user;
    }
}
