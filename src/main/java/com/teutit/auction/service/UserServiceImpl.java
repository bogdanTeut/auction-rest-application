package com.teutit.auction.service;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
   
    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;        
    }

    @Override
    @Transactional
    public User save(User user) {
        User existing = userRepository.findOne(user.getId());
        if(existing != null){
            throw new UserAlreadyExistsException(String.format("There already exists a user with id=%s", user.getId()));            
        }
        return userRepository.save(user);
    }
}
