package com.library.veriable;

import com.library.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Data {
    private AtomicInteger invoiceIdCounter = new AtomicInteger(1);

    public int generateNewTransactionId() {
        return invoiceIdCounter.getAndIncrement();
    }
    //invoiceIdCounter: Fatura oluşturulduğunda işlem ID'si üretmek için kullanılır.

    private Map<Integer, Book> books = new ConcurrentHashMap<>();
    private Map<Integer, User> users = new ConcurrentHashMap<>();
    private Map<Integer, Invoice> invoices = new ConcurrentHashMap<>();
    private Map<String, Author> authors = new ConcurrentHashMap<>();

    public Data(){
        books = new HashMap<>();
        users = new HashMap<>();
        authors = new HashMap<>();
        invoices = new HashMap<>();

        User user1 = new User(1, "Didar", "Yalçın", "ddrylcn@gmail.com", "1234", new ArrayList<>());
        User user2 = new User(2, "Siera", "Moe", "siera@example.com", "password", new ArrayList<>());
        User user3 = new User(3, "Row", "Smith", "row@example.com", "pass123", new ArrayList<>());

        users.put(user1.getUserId(), user1);
        users.put(user2.getUserId(), user2);
        users.put(user3.getUserId(), user3);

        Author author1 = new Author("J.R.R.", "Tolkien", new ArrayList<>());
        Author author2 = new Author("Stephen", "King", new ArrayList<>());
        Author author3 = new Author("Isaac","Asimov", new ArrayList<>());
        authors.put(author1.getName(), author1);
        authors.put(author2.getName(), author2);
        authors.put(author3.getName(), author3);

        Book book1 = new Book(1, "Hobbit", author1, Category.FANTASY);
        Book book2 = new Book(2, "Göz", author2, Category.FEAR);
        Book book3 = new Book(3, "Foundation", author3, Category.SCIENCEFICTION);

        books.put(book1.getBookId(), book1);
        books.put(book2.getBookId(), book2);
        books.put(book3.getBookId(), book3);
    }

    public void addBook(Book book){
        books.put(book.getBookId(), book);
    }
    //addBook Yeni bir kitap ekler.

    public Book getBookById(int id){
        return books.get(id);
    }
    //getBookById: ID'si verilen kitabı döndürür.

    public List<Book> getBooksByTitle(String title) {
        //getBooksByTitle: Belirli bir başlığa sahip kitapları listeler.

        return books.values().stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String authorName) {
        //getBooksByAuthor: Belirli bir yazar adına sahip kitapları listeler.

        return books.values().stream()
                .filter(book -> book.getAuthor().getName().equals(authorName))
                .collect(Collectors.toList());
    }

    public void updateBook(Book book) {
        //updateBook: Var olan bir kitabın bilgilerini günceller.

        if(books.containsKey(book.getBookId())){
            books.put(book.getBookId(), book);
            System.out.println("Book information updated.");
        }
        else {
            System.out.println("Book not found.");
        }
    }

    public void deleteBook(int id) {
        //deleteBook: Bir kitabı sistemden kaldırır.

        if (books.containsKey(id)) {
            books.remove(id);
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }

    public List<Book> getBooksByCategory(Category category) {
        //getBooksByCategory: Belirli bir kategoriye ait kitapları listeler.

        return books.values().stream()
                .filter(book -> book.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(Author author) {
        //getBooksByAuthor(Author author): Belirli bir yazarın tüm kitaplarını listeler.

        return books.values().stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());


    }

    public void borrowBook(User user, Book book) {

        int newTransactionId = generateNewTransactionId();
        Invoice invoice = new Invoice(newTransactionId, user, book, false);
        invoices.put(invoice.getId(), invoice);
        user.addBorrowedBook(book); // addBorrowedBook metodunu User sınıfında tanımladım
        user.getBorrowedBooks().add(book);
        books.put(book.getBookId(), book);
    }

    public void returnBook(User user, Book book) {
        Invoice invoice = invoices.get(book.getBookId());
        invoice.setReturned(true);
        user.removeBorrowedBook(book); // removeBorrowedBook metodunu User sınıfında tanımladım
    }

    //////// USER METHOD (KULLANICI İŞLEMLERİ)  ////////

    public void addUser(User user){
        //addUser: Yeni bir kullanıcı ekler.
        users.put(user.getUserId(), user);
    }

    public User getUserById(int id) {
        //getUserById: ID'si verilen kullanıcıyı döndürür.
        return users.get(id);
    }

    public User getUserByEmail(String email) {
        //getUserByEmail: E-posta adresine göre kullanıcıyı döndürür.
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User user) {
        //updateUser: Var olan bir kullanıcının bilgilerini günceller.
        users.put(user.getUserId(), user);
    }

    public void deleteUser(int id) {
        //deleteUser: Bir kullanıcıyı sistemden kaldırır.
        users.remove(id);
    }

    public List<User> getAllUsers() {
        //getAllUsers: Sisteme kayıtlı tüm kullanıcıları listeler.
        return new ArrayList<>(users.values());
    }

    public List<Book> getAllBooksDatabase() {
        //getAllBooksDatabase: Sisteme kayıtlı tüm kitapları döndürür.
        return books.values().stream().toList();
    }

    @Override
    public String toString() {
        return "Data{" + "books=" + books + ", users=" + users + ", invoices=" + invoices + '}';
    }
}
