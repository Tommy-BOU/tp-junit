package com.mycompany.app;

public interface ExternalBookService {
    boolean isBookAvailable(String title);
    Book fetchBookDetails(String title);
}