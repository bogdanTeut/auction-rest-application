package com.teutit.auction.service;

import com.teutit.auction.domain.User;

import java.util.List;

public interface UserService {
    public User save(User user);

    public List<User> listUsers();
}
