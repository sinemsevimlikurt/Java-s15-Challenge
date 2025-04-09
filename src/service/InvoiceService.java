package service;

import model.*;

public class InvoiceService {
    private static final double BOOK_FEE = 10.0;

    public Invoice generateInvoice(User user, Book book, boolean isReturn) {
        double amount = isReturn ? -BOOK_FEE : BOOK_FEE;
        return new Invoice("INV-" + System.currentTimeMillis(), user, book, amount, isReturn);
    }
}

