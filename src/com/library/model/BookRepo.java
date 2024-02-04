package com.library.model;

import com.library.service.LibraryBookService;
import com.library.veriable.Data;

import java.util.List;

public class BookRepo implements LibraryBookService {

    private Data data;
    public BookRepo(Data data){
        this.data = data;
    }

    @Override
    public void addBook(Book book){
        data.addBook(book);
        System.out.println("Book added successfully.");
    }

    @Override
    public Book getBookById(int id){
        return data.getBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return data.getAllBooksDatabase();
    }

    @Override
    public List<Book> getBooksByTitle(String title){
        return data.getBooksByTitle(title);
    }

    @Override
    public List<Book> getBooksByAuthor(String authorName){
        return data.getBooksByAuthor(authorName);
    }

    @Override
    public void updateBook(Book book) {
        Book existingBook = data.getBookById(book.getBookId());
        if (existingBook != null) {
            data.updateBook(book);
            System.out.println("Book information updated.");
        } else {
            System.out.println("Book not found with ID: " + book.getBookId());
        }
    }

    @Override
    public void deleteBook(int id){
        data.deleteBook(id);
    }

    @Override
    public List<Book> getBooksByCategory(Category category) {
        return data.getBooksByCategory(category);
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return data.getBooksByAuthor(author);
    }

    @Override
    public void borrowBook(User user, Book book) {
        if (user.getBorrowedBooks().size() < 5 && !book.isBorrowed()) {
            data.borrowBook(user, book);
            book.setBorrowed(true);
            user.addBorrowedBook(book); // Bu metot User sınıfında olmalı.
            System.out.println("Book borrowed successfully.");
        } else if (book.isBorrowed()) {
            System.out.println("Book is already borrowed by another user.");
        } else {
            System.out.println("User has reached the borrowing limit.");
        }
    }
    @Override
    public void returnBook(User user, Book book) {
        if (book.isBorrowed() && book.getBorrower().equals(user)) {
            data.returnBook(user, book);
            book.setBorrowed(false);
            book.setBorrower(null);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("This book was not borrowed by this user.");
        }
    }

    public double calculateFee(Book book) {
        double baseFee = 5.0; // Temel ücret
        double additionalFee = 2.0; // Ek ücret (örneğin kategorisine göre)

        if (book.getCategory() == Category.HISTORY) {
            additionalFee = 3.0;
        } else if (book.getCategory() == Category.SCIENCEFICTION) {
            additionalFee = 4.0;
        }

        // Toplam ücreti hesapla
        return baseFee + additionalFee;
    }
}
