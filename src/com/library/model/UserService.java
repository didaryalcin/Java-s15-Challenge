package com.library.model;

import com.library.veriable.Data;
import com.library.service.LibraryUserService;


public class UserService implements LibraryUserService{


    private Data data;

    public UserService(Data data) {
        this.data = data;
    }

    @Override
    public void addUser(User user) {
        data.addUser(user);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        User user = data.getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public void deleteUser(int userId) {
        data.deleteUser(userId);
    }

    public boolean isEmailRegisteredBefore(String email) {
        User user = data.getUserByEmail(email);
        return user != null;
    }
}