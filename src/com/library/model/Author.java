package com.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private String name;
    private String surname;
    List<Book> books;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Author(String name, String surname, List<Book> books) {  // books listesi null gelirse boş bir liste atama
        this.name = name;
        this.surname = surname;
        this.books = books == null ? new ArrayList<>() : new ArrayList<>(books);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }



    // Books listesini güvenli bir şekilde dışarıya verme
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    // Books listesine kitap ekleme
    public void addBook(Book book) {
        if (book != null && !books.contains(book)) {
            books.add(book);
        }
    }

    // Books listesinden kitap çıkarma
    public void removeBook(Book book) {
        books.remove(book);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) && Objects.equals(surname, author.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", books=" + books +
                '}';
    }
}
