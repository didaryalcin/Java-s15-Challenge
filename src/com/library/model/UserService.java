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

    private String hashPassword(String password) {
        // Hashing şifreyi burada yapabiliriz
        return Integer.toString(password.hashCode());
    }

    @Override
    public void deleteUser(int userId) {
        User user = data.getUserById(userId);
        if (user != null) {
            data.deleteUser(userId);
        } else {
            throw new IllegalStateException("User not found with ID: " + userId);
        }
    }


    public boolean isEmailRegisteredBefore(String email) {
        User user = data.getUserByEmail(email);
        return user != null;
    }
}