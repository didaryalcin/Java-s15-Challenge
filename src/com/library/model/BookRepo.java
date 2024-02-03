package com.library.model;

import com.library.service.LibraryBookService;
import com.library.veriable.Data;

import java.util.List;

public class BookRepo implements LibraryBookService {

    private Data data; // Değiştirilen isim: Database yerine Data
    public BookRepo(Data data){ // Değiştirilen isim: Database yerine Data
        this.data = data; // Değiştirilen isim: Database yerine Data
    }

    @Override
    public void addBook(Book book){
        data.addBook(book); // Değiştirilen isim: database yerine data
        System.out.println("Book added successfully.");
    }

    @Override
    public Book getBookById(int id){
        return data.getBookById(id); // Değiştirilen isim: database yerine data
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
    public void updateBook(Book book){
        if(data.getBooksByTitle(book.getTitle()).equals(book.getTitle())){
            data.updateBook(book);
            System.out.println("Book information updated.");
        }else {
            System.out.println("Book not found.");
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
        if (user.getBorrowedBooks().size() < 5) {
            if (book.isBorrowed()) {
                data.borrowBook(user, book);
            } else {
                System.out.println("Book is already borrowed by another user.");
            }
        } else {
            System.out.println("User has reached the borrowing limit.");
        }
    }

    @Override
    public void returnBook(User user, Book book) {
        data.returnBook(user, book);
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
