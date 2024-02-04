package com.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String passwordHash; // Şifre hash'lenmiş olarak saklanmalı
    private List<Book> borrowedBooks;

    public User(int userId, String name, String surname, String email, String password, List<Book> borrowedBooks) { // Değiştirilen isim: user_id yerine userId
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.setPassword(password); // Şifre hash'lenerek atanmalı
        this.borrowedBooks = borrowedBooks != null ? borrowedBooks : new ArrayList<>();
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                // Şifre yazdırılmamalı
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }

    public void removeBorrowedBook(Book book) {
    }

    public void info(String s) {
    }

    public void warn(String s) {
    }
}
