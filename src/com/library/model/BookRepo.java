package com.library.model;

import com.library.service.LibraryBookService;
import com.library.veriable.Data;

import java.util.List;

public class BookRepo implements LibraryBookService { //LibraryBookService arayüzünü uygulayan


    private Data data;

    //Data sınıfının bir örneğini alır
    // bu veri deposunu kullanarak kitaplar üzerinde işlemler yapar.
    public BookRepo(Data data){
        this.data = data;
    }

    @Override //Sisteme yeni bir kitap ekler ve başarılı olduğunu bildirir.
    public void addBook(Book book){
        data.addBook(book);
        System.out.println("Book added successfully.");
    }

    @Override //Belirli bir ID'ye sahip kitabı döndürür.
    public Book getBookById(int id){
        return data.getBookById(id);
    }

    @Override //Tüm kitapları listeler.
    public List<Book> getAllBooks() {
        return data.getAllBooksDatabase();
    }

    @Override //Başlığına göre kitapları listeler.
    public List<Book> getBooksByTitle(String title){
        return data.getBooksByTitle(title);
    }

    @Override //Yazar adına göre kitapları listeler.
    public List<Book> getBooksByAuthor(String authorName){
        return data.getBooksByAuthor(authorName);
    }

    @Override //Var olan bir kitabın bilgilerini günceller.
    // kitabın başlığına göre arama sonucu kitap listesi üzerinden gerçekleştirilir.
    public void updateBook(Book book) {
        Book existingBook = data.getBookById(book.getBookId());
        if (existingBook != null) {
            data.updateBook(book);
            System.out.println("Book information updated.");
        } else {
            System.out.println("Book not found with ID: " + book.getBookId());
        }
    }

    @Override // Bir kitabı sistemden kaldırır.
    public void deleteBook(int id){
        data.deleteBook(id);
    }

    @Override //Kategoriye göre kitapları listeler.
    public List<Book> getBooksByCategory(Category category) {
        return data.getBooksByCategory(category);
    }

    @Override //Author nesnesine göre kitapları listeler.
    public List<Book> getBooksByAuthor(Author author) {
        return data.getBooksByAuthor(author);
    }

    @Override //: Bir kullanıcıya kitap ödünç verir. Bir kullanıcı max 5 kitap alabilir
            // kitap zaten ödünç alınmışsa ödünç verme işlemi gerçekleştirilmez.
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
    @Override  // Kullanıcının ödünç aldığı bir kitabı iade etmesini işler.
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
