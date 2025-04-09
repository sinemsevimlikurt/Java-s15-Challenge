package model;

import java.util.HashSet;
import java.util.Set;

public class User extends Person {
    private Set<Book> borrowedBooks = new HashSet<>();
    private static final int MAX_BOOKS = 5;

    public User(String id, String name) {
        super(id, name);
    }

    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() >= MAX_BOOKS) return false;
        return borrowedBooks.add(book);
    }

    public boolean returnBook(Book book) {
        return borrowedBooks.remove(book);
    }

    public boolean hasBook(Book book) {
        return borrowedBooks.contains(book);
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "Kullanıcı ID: " + id + ", Adı: " + name + ", Alınan Kitap Sayısı: " + borrowedBooks.size();
    }
}

