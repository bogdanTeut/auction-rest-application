package com.teutit.auction.controller;

import com.teutit.auction.domain.User;
import com.teutit.auction.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WhenCreatingUserInUserController {
    
    @Mock
    private UserService userService;
    
    private UserController userController;
    
    @Before
    public void onSetup(){
        userController = new UserController(userService);
    }
    
    @Test
    public void givenCorrectPayloadWithUniqueId_thenItShouldCreateUser(){
        User returnedUser = givenUserService();

        User user = new User();
        User result = userController.createUser(user);

        assertUserCreated(returnedUser, user, result);
    }

    private void assertUserCreated(User returnedUser, User user, User result) {
        verify(userService, times(1)).saveUser(user);
        assertEquals("Returned user should come from the service", returnedUser, result);
    }

    private User givenUserService() {
        User returnedUser = new User();
        when(userService.saveUser(any(User.class))).thenReturn(returnedUser);
        return returnedUser;
    }

}
