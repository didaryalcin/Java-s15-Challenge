package com.library.model;

import java.time.LocalDate;

public class Invoice {
    private final int id; // Fatura tanımlayıcısı final
    private final User user; // Faturanın düzenlendiği kullanıcıfinal
    private final Book book; //  Faturada ödünç alınan kitap
    private boolean returned; //  Kitabın iade edilip edilmediğini belirten boolean bir değer.
    private LocalDate issueDate; // Faturanın oluşturulduğu tarih
    private LocalDate dueDate; // Kitabın iade edilmesi gereken son tarih.

    public Invoice(int id, User user, Book book, boolean returned) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.returned = returned;
        this.issueDate = LocalDate.now();
        this.dueDate = this.issueDate.plusWeeks(2);
    }
    //issueDate otomatik olarak o anki tarihe (LocalDate.now()) ayarlanır
    // dueDate için bu tarihten itibaren iki hafta sonrası belirlenir.


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


    // Kitabın iade durumunu güncelleyen metod.
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
