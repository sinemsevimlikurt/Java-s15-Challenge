package interfaces;

import model.Book;

import java.util.List;

public interface Searchable {
    Book searchById(String id);
    List<Book> searchByAuthor(String authorName);
    List<Book> searchByCategory(String categoryName);
    List<Book> searchByTitle(String title);
}

