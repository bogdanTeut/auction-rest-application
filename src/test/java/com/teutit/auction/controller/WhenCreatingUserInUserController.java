package com.teutit.auction.controller;

import com.teutit.auction.domain.User;
import com.teutit.auction.service.UserService;
import com.teutit.auction.utils.UserUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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

        User user = UserUtils.createUser();
        User result = userController.createUser(user);

        assertUserHasBeenCreated(returnedUser, user, result);
    }

    @Test
    public void givenTenUsersInDB_thenItShouldReturnThem(){
        givenUserServiceWithTenUsers();

        userController.listUsers();

        verify(userService, times(1)).listUsers();
    }

    private void assertUserHasBeenCreated(User returnedUser, User user, User result) {
        verify(userService, times(1)).save(user);
        assertEquals("Returned user should come from the service", returnedUser, result);
    }

    private User givenUserService() {
        User returnedUser = UserUtils.createUser();
        when(userService.save(any(User.class))).thenReturn(returnedUser);
        return returnedUser;
    }

    private void givenUserServiceWithTenUsers() {
        when(userService.listUsers()).thenReturn(UserUtils.createUserList(10));
    }

}
