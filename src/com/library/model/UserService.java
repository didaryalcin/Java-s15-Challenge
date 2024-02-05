package com.library.model;

import com.library.veriable.Data;
import com.library.service.LibraryUserService;


public class UserService implements LibraryUserService{


    private Data data;

    public UserService(Data data) {
        this.data = data;
    }

    @Override //Şifreyi hash'le ve kullanıcıyı veri tabanına ekle
    public void addUser(User user) {
        if (isEmailRegisteredBefore(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered: " + user.getEmail());
        }
        user.setPassword(hashPassword(user.getPassword()));
        data.addUser(user);
    }

    @Override //// Hash'lenmiş şifre karşılaştırması yap
    public boolean authenticateUser(String email, String password) {
        User user = data.getUserByEmail(email);
        return user != null && user.getPassword().equals(hashPassword(password));
    }


    private String hashPassword(String password) {
        // Hashing şifreyi burada yapabiliriz
        return Integer.toString(password.hashCode());
    }

    @Override //// ID'ye sahip kullanıcıyı siler,kullanıcı bulunamazsa hata gönderir
    public void deleteUser(int userId) {
        User user = data.getUserById(userId);
        if (user != null) {
            data.deleteUser(userId);
        } else {
            throw new IllegalStateException("User not found with ID: " + userId);
        }
    }


    //// e-posta adresinin kayıtlı olup olmadığını kontrol eder.
    public boolean isEmailRegisteredBefore(String email) {
        User user = data.getUserByEmail(email);
        return user != null;
    }
}