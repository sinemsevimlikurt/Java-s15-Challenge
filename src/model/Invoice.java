package model;

public class Invoice {
    private String id;
    private User user;
    private Book book;
    private double amount;
    private boolean isRefund;

    public Invoice(String id, User user, Book book, double amount, boolean isRefund) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.isRefund = isRefund;
    }

    @Override
    public String toString() {
        return "Fatura ID: " + id +
                ", Kullanıcı: " + user.getName() +
                ", Kitap: " + book.getTitle() +
                ", Tutar: " + amount + "₺" +
                ", " + (isRefund ? "İade" : "Ücretlendirme");
    }
}
