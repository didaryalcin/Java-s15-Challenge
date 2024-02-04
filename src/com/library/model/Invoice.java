package com.library.model;

import java.time.LocalDate;

public class Invoice {
    private final int id; // Değişmez olduğunu belirtmek için final
    private final User user; // Değişmez
    private final Book book; // Değişmez
    private boolean returned;
    private LocalDate issueDate; // Çıkış tarihi eklendi
    private LocalDate dueDate; // Son teslim tarihi eklendi

    public Invoice(int id, User user, Book book, boolean returned) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.returned = returned;
        this.issueDate = LocalDate.now();
        this.dueDate = this.issueDate.plusWeeks(2);
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
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
