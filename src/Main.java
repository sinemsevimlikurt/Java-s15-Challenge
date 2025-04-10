import interfaces.Billable;
import interfaces.Searchable;
import model.*;
import service.*;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final LibraryService libraryService = new LibraryService(); // Kütüphane yönetimi
    private static final Searchable searchable = libraryService;               // Arama işlemleri için interface
    private static final Billable invoiceService = new InvoiceService();       // Fatura işlemleri

    public static void main(String[] args) {
        seedData(); // Örnek veriler

        boolean exit = false;

        while (!exit) {
            printMenu();
            int choice = readInt("Seçiminizi yapın: ");
            System.out.println();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> searchBook();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> listBooksByCategory();
                case 6 -> listBooksByAuthor();
                case 7 -> borrowBook();
                case 8 -> returnBook();
                case 9 -> listAllBooks();
                case 10 -> listAllUsers();
                case 0 -> exit = true;
                default -> System.out.println("Geçersiz seçim!\n");
            }
        }
        System.out.println("Sistemden çıkılıyor...");
    }

    private static void printMenu() {
        System.out.println("""
                \n========= KÜTÜPHANE SİSTEMİ =========
                1. Yeni Kitap Ekle
                2. Kitap Ara (ID, Başlık, Yazar)
                3. Kitap Bilgilerini Güncelle
                4. Kitap Sil
                5. Kategoriye Göre Kitapları Listele
                6. Yazara Göre Kitapları Listele
                7. Kitap Ödünç Al
                8. Kitap İade Et
                9. Tüm Kitapları Listele
                10. Kullanıcıları Listele
                0. ÇIKIŞ
                """);
    }

    // ---- Menü Fonksiyonları ----

    private static void addBook() {
        String id = read("Kitap ID: ");
        String title = read("Kitap Başlığı: ");
        String authorId = read("Yazar ID: ");
        String authorName = read("Yazar Adı: ");
        String categoryName = read("Kategori Adı: ");

        Author author = new Author(authorId, authorName);
        Category category = new Category(categoryName);
        Book book = new Book(id, title, author, category);

        libraryService.addBook(book);
        System.out.println("Kitap başarıyla eklendi.\n");
    }

    private static void searchBook() {
        System.out.println("""
            1. ID ile Ara
            2. Başlık ile Ara
            3. Yazar ile Ara
            """);
        int option = readInt("Seçiminiz: ");

        switch (option) {
            case 1 -> {
                String id = read("Kitap ID: ");
                Book book = searchable.searchById(id);
                printResult(book);
            }
            case 2 -> {
                String title = read("Başlık: ");
                List<Book> books = searchable.searchByTitle(title);
                books.forEach(System.out::println);
            }
            case 3 -> {
                String author = read("Yazar Adı: ");
                List<Book> books = searchable.searchByAuthor(author);
                books.forEach(System.out::println);
            }
            default -> System.out.println("Geçersiz seçim!");
        }
    }

    private static void updateBook() {
        String id = read("Güncellenecek Kitap ID: ");
        Book book = searchable.searchById(id);

        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return;
        }

        System.out.println("Güncellemek istemediğiniz alanları boş bırakabilirsiniz.");

        String newTitle = read("Yeni Başlık (" + book.getTitle() + "): ");
        if (!newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
        }

        String newCategoryName = read("Yeni Kategori (" + book.getCategory().getName() + "): ");
        if (!newCategoryName.trim().isEmpty()) {
            Category newCategory = new Category(newCategoryName);
            book.setCategory(newCategory);
        }

        System.out.println("Kitap bilgileri güncellendi.\n");
    }

    private static void deleteBook() {
        String id = read("Silinecek Kitap ID: ");
        libraryService.removeBook(id);
        System.out.println("Kitap silindi.\n");
    }

    private static void listBooksByCategory() {
        String category = read("Kategori Adı: ");
        List<Book> books = searchable.searchByCategory(category); // interface üzerinden
        books.forEach(System.out::println);
    }

    private static void listBooksByAuthor() {
        String author = read("Yazar Adı: ");
        List<Book> books = searchable.searchByAuthor(author); // interface üzerinden
        books.forEach(System.out::println);
    }

    private static void borrowBook() {
        String userId = read("Kullanıcı ID: ");
        User user = libraryService.getUserById(userId);
        if (user == null) {
            String name = read("Kullanıcı Adı: ");
            user = new User(userId, name);
            libraryService.addUser(user);
        }

        String bookId = read("Kitap ID: ");
        boolean success = libraryService.lendBook(bookId, userId);
        if (success) {
            Book book = searchable.searchById(bookId); // eski: getBookById
            Invoice invoice = invoiceService.generateInvoice(user, book, false);
            System.out.println("Kitap ödünç verildi. " + invoice);
        } else {
            System.out.println("Kitap alınamadı. Belki alınmış olabilir ya da limit aşılmıştır.");
        }
    }

    private static void returnBook() {
        String userId = read("Kullanıcı ID: ");
        String bookId = read("Kitap ID: ");
        boolean success = libraryService.returnBook(bookId, userId);
        if (success) {
            Book book = searchable.searchById(bookId); // eski: getBookById
            User user = libraryService.getUserById(userId);
            Invoice invoice = invoiceService.generateInvoice(user, book, true);
            System.out.println("Kitap başarıyla iade edildi. " + invoice);
        } else {
            System.out.println("İade işlemi başarısız. Kitap sizde olmayabilir.");
        }
    }

    private static void listAllBooks() {
        libraryService.getAllBooks().forEach(System.out::println);
    }

    private static void listAllUsers() {
        libraryService.getAllUsers().forEach(System.out::println);
    }

    // ---- Yardımcı Fonksiyonlar ----

    private static void seedData() {
        Author a1 = new Author("A1", "Orhan Pamuk");
        Category c1 = new Category("Roman");
        Book b1 = new Book("B1", "Masumiyet Müzesi", a1, c1);
        Book b2 = new Book("B2", "Benim Adım Kırmızı", a1, c1);
        libraryService.addBook(b1);
        libraryService.addBook(b2);

        User u1 = new User("U1", "Sinem");
        libraryService.addUser(u1);
    }

    private static String read(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static int readInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(scanner.nextLine());
    }

    private static void printResult(Book book) {
        if (book != null) System.out.println(book);
        else System.out.println("Kitap bulunamadı.");
    }
}
