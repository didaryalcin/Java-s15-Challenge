package com.library.main;

import com.library.model.*;
import com.library.veriable.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static Scanner scanner = new Scanner(System.in);
    private static Data data = new Data();
    private static UserService userService = new UserService(data);
    private static BookRepo bookRepo = new BookRepo(data);
    private static User currentUser = null;

    public static void main(String[] args) {
        loginOrRegister();
    }

    private static void loginOrRegister(){
        while (true) {
            if (currentUser == null) {
                System.out.println("1. Login");
                System.out.println("2. New User");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        currentUser = login();
                        if (currentUser != null) {
                            mainMenu();
                        }
                        break;
                    case 2:
                        newUser();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } else {
                mainMenu();
            }
        }
    }

    private static User login() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userService.authenticateUser(email, password)) {
            System.out.println("Login successful!");
            mainMenu();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
        return null;
    }

    private static void newUser() {
        System.out.print("Enter a new email: ");
        String email = scanner.nextLine();

        if (userService.isEmailRegisteredBefore(email)) {
            System.out.println("This email is already registered. Please choose a different email.");
            return;
        }

        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();

        User newUser = new User(data.generateNewTransactionId(), name, surname, email, password, new ArrayList<>());
        userService.addUser(newUser);

        System.out.println("New user registered successfully!");
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("1. Add New Book");
            System.out.println("2. Select Book");
            System.out.println("3. Update Book Info");
            System.out.println("4. Delete Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Generate Invoice");
            System.out.println("8. List Books");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    selectBook();
                    break;
                case 3:
                    updateBookInfo();
                    break;
                case 4:
                    deleteBook();
                    break;
                /*case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    generateInvoice();
                    break;*/
                case 8:
                    listBook();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void addNewBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine();

        System.out.print("Enter author surname: ");
        String authorSurname = scanner.nextLine();

        System.out.print("Enter book category (HISTORY, SCIENCEFICTION, ...): ");
        Category category = Category.valueOf(scanner.nextLine());

        Author author = new Author(authorName, authorSurname);
        Book newBook = new Book(data.generateNewTransactionId(), title, author, category);
        bookRepo.addBook(newBook);
    }

    private static void selectBook() {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book selectedBook = bookRepo.getBookById(bookId);
        if (selectedBook != null) {
            System.out.println("Selected Book: " + selectedBook);
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void updateBookInfo(){
        System.out.println("Enter the book ID you want to update: ");
        int bookId= scanner.nextInt();
        scanner.nextLine();

        Book bookToUpdate = bookRepo.getBookById(bookId);

        if(bookToUpdate != null){
            System.out.println("Current book information: ");
            System.out.println("Title: " + bookToUpdate.getTitle());
            System.out.println("Author: " + bookToUpdate.getAuthor());
            System.out.println("Category: " + bookToUpdate.getCategory());

            System.out.println("Enter new information: ");
            System.out.println("New title (press Enter to keep current title): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()){
                bookToUpdate.setTitle(newTitle);
            }
            System.out.println("New author name (press Enter to keep current author): ");
            String newAuthorName = scanner.nextLine();
            if(!newAuthorName.isEmpty()){
                bookToUpdate.getAuthor().setName(newAuthorName);
            }
            System.out.println("New author surname (press Enter to keep current author): ");
            String newAuthorSurname = scanner.nextLine();
            if (!newAuthorSurname.isEmpty()){
                bookToUpdate.getAuthor().setSurname(newAuthorSurname);
            }
            System.out.print("New category (press Enter to keep current category): ");
            String newCategoryStr = scanner.nextLine();
            if (!newCategoryStr.isEmpty()){
                try {
                    Category newCategory = Category.valueOf(newCategoryStr.toUpperCase());
                    bookToUpdate.setCategory(newCategory);
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid category. Book information not updated.");
                    return;
                }
            }
            bookRepo.updateBook(bookToUpdate);
            System.out.println("Book information updated successfully.");
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    private static void deleteBook() {
        System.out.print("Enter the book ID you want to delete: ");
        listBook();
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book bookToDelete = bookRepo.getBookById(bookId);

        if (bookToDelete != null) {
            System.out.println("Are you sure you want to delete this book? (yes/no)");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                bookRepo.deleteBook(bookId);
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book deletion canceled.");
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    private static void listBook(){
        List<Book> allBooks = bookRepo.getAllBooks();
        if (!allBooks.isEmpty()){
            System.out.println("Book list: ");
            for (Book book: allBooks){
                System.out.println(book);
            }
        }if (allBooks.isEmpty()){
            System.out.println("Library is empty.");
        }
        else {
            System.out.println("Invalid choice. Please choose again.");
        }
    }
}
