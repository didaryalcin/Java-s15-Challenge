package com.library.veriable;

import com.library.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Data {
    private AtomicInteger transactionIdCounter = new AtomicInteger(1);

    public int generateNewTransactionId() {
        return transactionIdCounter.getAndIncrement();
    }

    private Map<Integer, Book> books;
    private Map<Integer, User> users;
    private Map<Integer, Invoice> invoices;
    private Map<String, Author> authors;

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

    public Book getBookById(int id){
        return books.get(id);
    }

    public List<Book> getBooksByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return books.values().stream()
                .filter(book -> book.getAuthor().getName().equals(authorName))
                .collect(Collectors.toList());
    }

    public void updateBook(Book book) {
        if(books.containsKey(book.getBookId())){
            books.put(book.getBookId(), book);
            System.out.println("Book information updated.");
        }
        else {
            System.out.println("Book not found.");
        }
    }

    public void deleteBook(int id) {
        if (books.containsKey(id)) {
            books.remove(id);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public List<Book> getBooksByCategory(Category category) {
        return books.values().stream()
                .filter(book -> book.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(Author author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public void borrowBook(User user, Book book) {
        int newTransactionId = generateNewTransactionId();
        Invoice invoice = new Invoice(newTransactionId, user, book, false);
        invoices.put(invoice.getId(), invoice);
        user.getBorrowedBooks().add(book);
        books.put(book.getBookId(), book);
    }

    public void returnBook(User user, Book book) {
        int newTransactionId = generateNewTransactionId();
        Invoice invoice = invoices.get(book.getBookId());
        invoice.setReturned(true);
        user.getBorrowedBooks().remove(book);
    }

    // USER METHODS

    public void addUser(User user){
        users.put(user.getUserId(), user);
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User getUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void deleteUser(int id) {
        users.remove(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Book> getAllBooksDatabase() {
        return books.values().stream().toList();
    }

    @Override
    public String toString() {
        return "Data{" + "books=" + books + ", users=" + users + ", invoices=" + invoices + '}';
    }
}
