package com.teutit.auction.service;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WhenCreatingUserInUserService {

    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    
    @Before
    public void onSetup(){
        userService = new UserServiceImpl(userRepository);
    }
    
    @Test
    public void givenCorrectPayloadWithUniqueId_thenItShouldCreateUser(){
        User returnedUser = givenUserRepository();
        
        User user = new User();
        User result = userService.save(user);

        assertThatUserHasBeenCreated(returnedUser, user, result);
    }
    
    @Test
    public void givenExistingUser_thenItShouldThrowAnException(){
        givenUserRepositoryWithExistingUser();
        
        try{
            User user = new User();
            userService.save(user); 
            fail("An exception should have been thrown by now");
        }catch(UserAlreadyExistsException uaee){
        }

        verify(userRepository, never()).save(any(User.class));
    }

    private void assertThatUserHasBeenCreated(User returnedUser, User user, User result) {
        verify(userRepository, times(1)).save(user);
        assertEquals("Returned user should come from the service", returnedUser, result);
    }

    private User givenUserRepository() {
        User returnedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(returnedUser);
        return returnedUser;
    }
    
    private User givenUserRepositoryWithExistingUser() {
        User returnedUser = new User();
        when(userRepository.findOne(anyString())).thenReturn(returnedUser);
        return returnedUser;
    }
}
