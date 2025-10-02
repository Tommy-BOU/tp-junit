package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> livres = new ArrayList<>();
    private ExternalBookService externalService;

    public Library() {}

    public Library(ExternalBookService externalService) {
        this.externalService = externalService;
    }

    public void addBook(Book book) {
        livres.add(book);
    }

    public boolean borrowBook(String title) {
        for (Book b : livres) {
            if (b.getTitre().equals(title) && b.isDisponible()) {
                b.setDisponible(false);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String title) {
        for (Book b : livres) {
            if (b.getTitre().equals(title) && !b.isDisponible()) {
                b.setDisponible(true);
                return true;
            }
        }
        return false;
    }

    public int countAvailableBooks() {
        int count = 0;
        for (Book b : livres) {
            if (b.isDisponible()) {
                count++;
            }
        }
        return count;
    }

    public List<Book> getLivres() {
        return livres;
    }

    public boolean checkExternalAvailability(String title) {
        if (externalService == null) {
            throw new IllegalStateException("External service not configured");
        }
        return externalService.isBookAvailable(title);
    }

    public void importBookFromExternal(String title) {
        if (externalService == null) {
            throw new IllegalStateException("External service not configured");
        }
        Book book = externalService.fetchBookDetails(title);
        if (book == null) {
            throw new IllegalArgumentException("Book not found in external service: " + title);
        }
        addBook(book);
    }
}
