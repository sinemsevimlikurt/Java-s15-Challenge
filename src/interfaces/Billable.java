package interfaces;

import model.Book;
import model.Invoice;
import model.User;

public interface Billable {
    Invoice generateInvoice(User user, Book book, boolean isReturn);
}

