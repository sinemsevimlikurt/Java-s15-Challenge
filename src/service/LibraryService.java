package service;

import interfaces.Searchable;
import model.*;

import java.util.*;

public class LibraryService implements Searchable {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, User> users = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public Book getBookById(String id) {
        return books.get(id);
    }

    public void removeBook(String id) {
        books.remove(id);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public void updateBook(String id, String newTitle) {
        Book book = books.get(id);
        if (book != null) {
            book.setTitle(newTitle);
        }
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public boolean lendBook(String bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);

        if (book == null || user == null || book.isBorrowed()) return false;

        boolean success = user.borrowBook(book);
        if (success) {
            book.setBorrowed(true);
        }
        return success;
    }

    public boolean returnBook(String bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);

        if (book == null || user == null || !user.hasBook(book)) return false;

        boolean success = user.returnBook(book);
        if (success) {
            book.setBorrowed(false);
        }
        return success;
    }

    @Override
    public Book searchById(String id) {
        return books.get(id);
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchByCategory(String categoryName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getCategory().getName().equalsIgnoreCase(categoryName)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}
