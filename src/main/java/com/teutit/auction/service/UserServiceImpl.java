package com.teutit.auction.service;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        User existingUser = userRepository.findOne(user.getId());
        if (existingUser != null){
            throw new UserAlreadyExistsException(String.format("There is already an user with id=%s", user.getId()));
        }
        return userRepository.save(user);
    }
}
