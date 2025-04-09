package model;

public class Book {
    private String id;
    private String title;
    private Author author;
    private Category category;
    private boolean isBorrowed;

    public Book(String id, String title, Author author, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isBorrowed = false;
    }

    // Getter metotları
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    // Setter metotları
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Kitap ID: " + id +
                ", Başlık: " + title +
                ", Yazar: " + author.getName() +
                ", Kategori: " + category.getName() +
                ", Ödünç Durumu: " + (isBorrowed ? "Alındı" : "Mevcut");
    }
}
