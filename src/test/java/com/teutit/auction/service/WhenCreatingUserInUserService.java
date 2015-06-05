package com.teutit.auction.service;

import com.teutit.auction.domain.User;
import com.teutit.auction.exception.UserAlreadyExistsException;
import com.teutit.auction.repository.UserRepository;
import com.teutit.auction.utils.UserUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
        
        User user = new User("id", "password");
        User result = userService.save(user);

        assertUserHasBeenCreated(returnedUser, user, result);
        assertThat("", CoreMatchers.allOf(notNullValue(), instanceOf(String.class)));
    }

    @Test
    public void givenCorrectPayloadWithDuplicatedUser_thenItShouldThrowException(){
        givenUserRepositoryWithExistingUser();

        try{
            userService.save(UserUtils.createUser());
            fail("It should have thrown an exception by now");
        }catch (UserAlreadyExistsException uaee){

        }
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void givenTenUsersInDB_thenItShouldReturnThem(){
        givenUserRepositoryWithTenUsers();

        List<User> result = userService.listUsers();

        verify(userRepository, times(1)).findAll();
        assertEquals(10, result.size());

    }

    private void givenUserRepositoryWithTenUsers() {
        when(userRepository.findAll()).thenReturn(UserUtils.createUserList(10));
    }

    private void assertUserHasBeenCreated(User returnedUser, User user, User result) {
        verify(userRepository, times(1)).save(user);
        assertEquals("Returned user should come from the service", returnedUser, result);
    }

    private User givenUserRepository() {
        User returnedUser = UserUtils.createUser();
        when(userRepository.save(any(User.class))).thenReturn(returnedUser);
        return returnedUser;
    }

    private User givenUserRepositoryWithExistingUser() {
        User returnedUser = UserUtils.createUser();
        when(userRepository.findOne(returnedUser.getId())).thenReturn(returnedUser);
        return returnedUser;
    }
}
