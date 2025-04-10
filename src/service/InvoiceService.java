package service;

import interfaces.Billable;
import model.Book;
import model.Invoice;
import model.User;

public class InvoiceService implements Billable {
    private static final double BOOK_FEE = 10.0;

    @Override
    public Invoice generateInvoice(User user, Book book, boolean isReturn) {
        double amount = isReturn ? -BOOK_FEE : BOOK_FEE;
        return new Invoice("INV-" + System.currentTimeMillis(), user, book, amount, isReturn);
    }
}
