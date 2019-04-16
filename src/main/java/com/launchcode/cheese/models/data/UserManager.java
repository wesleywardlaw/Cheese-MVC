package com.launchcode.cheese.models.data;

import com.launchcode.cheese.models.User;

import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private static HashMap<Integer, User> userTable = new HashMap<>();

    public static Collection<User> getAll(){
        return userTable.values();
    }

    public static User getUserByID(int userID){
        return userTable.get(userID);
    }

    public static void create(String username, String email, String password){
        User user = new User(username, email, password);
        add(user);
    }
    public static void add(User user){
        userTable.put(user.getUserID(), user);
    }
}
