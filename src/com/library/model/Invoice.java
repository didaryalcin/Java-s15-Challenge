package com.library.model;

public class Invoice {
    private int id;
    private User user;
    private Book book;
    private boolean returned;

    public Invoice(int id, User user, Book book, boolean returned) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.returned = returned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", returned=" + returned +
                '}';
    }
}
