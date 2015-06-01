package com.teutit.auction.service;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import java.util.List;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //@Inject
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        User existingUser = userRepository.findOne(user.getId());
        if (existingUser != null){
            throw new UserAlreadyExistsException(String.format("There is already an user with id= %s", user.getId()));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
