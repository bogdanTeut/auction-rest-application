package com.teutit.auction.utils;

import com.teutit.auction.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static List<User> createUserList(int howMany){
        List<User> usersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            usersList.add(new User("id" + i, "password" + i));
        }
        return usersList;
    }

    public static User createUser(){
        return new User("id", "password");
    };

}
