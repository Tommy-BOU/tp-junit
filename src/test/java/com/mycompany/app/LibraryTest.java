package com.mycompany.app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryTest {

    private Library library;
    private Book book;
    private ExternalBookService mockExternalService;

    @BeforeEach
    void setUp() {
        mockExternalService = mock(ExternalBookService.class);
        book = new Book("Mock Book", "Mock Author");
        library = new Library(mockExternalService);
    }

    @AfterEach
    void testEnd(){
        System.out.println("Success");
        System.out.println("========================================================================");
    }

    @BeforeAll
    static void init() {
        System.out.println("Début des tests");
        System.out.println("========================================================================");
    }

    @AfterAll
    static void end() {
        System.out.println("========================================================================");
        System.out.println("Fin des tests");
    }

    @Test
    void addBook_shouldAddBookToLibrary() {
        System.out.println("Test ajout de livre");
        library.addBook(book);
        assertEquals(1, library.getLivres().size());
    }

    @Test
    void borrowBook_shouldReturnTrueIfBookIsAvailable() {
        System.out.println("Test emprunt de livre si disponible");
        library.addBook(book);
        assertTrue(library.borrowBook("Mock Book"));
    }

    @Test
    void borrowBook_shouldReturnFalseIfBookDoesNotExist(){
        System.out.println("Test emprunt de livre si non existant");
        assertFalse(library.borrowBook("Non existing"));
    }

    @Test
    void borrowBook_shouldReturnFalseIfBookIsNotAvailable() {
        System.out.println("Test emprunt de livre si indisponible");
        library.addBook(book);
        library.borrowBook("Mock Book");
        assertFalse(library.borrowBook("Mock Book"));
    }

    @Test
    void returnBook_shouldReturnTrueIfBookIsAvailable() {
        System.out.println("Test retour de livre");
        library.addBook(book);
        library.borrowBook("Mock Book");
        assertTrue(library.returnBook("Mock Book"));
    }

    @Test
    void returnBook_shouldReturnFalseIfBookDoesNotExist() {
        System.out.println("Test retour de livre si non existant");
        assertFalse(library.returnBook("Non existing"));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "5, 2", "30, 13"})
    void countAvailableBooks_shouldReturnNumberOfAvailableBooks(int addedBooks, int borrowedBooks) {
        System.out.println("Test nombre de livres disponibles");
        for (int i = 0; i < addedBooks; i++) {
            library.addBook(new Book("Mock Book " + i, "Mock Author " + i));
        }
        for (int i = 0; i < borrowedBooks; i++) {
            library.borrowBook("Mock Book " + i);
        }

        assertEquals(addedBooks - borrowedBooks, library.countAvailableBooks());
    }

    @Test
    void isBookAvailable_shouldReturnTrueIfBookIsAvailable()
    {
        System.out.println("Test service disponibilité de livre");
        when(mockExternalService.isBookAvailable("Mock Book")).thenReturn(true);
        assertTrue(library.checkExternalAvailability("Mock Book"));
    }

    @Test
    void isBookAvailable_shouldReturnFalseIfBookIsNotAvailable()
    {
        System.out.println("Test service disponibilité de livre");
        when(mockExternalService.isBookAvailable("Mock Book")).thenReturn(false);
        assertFalse(library.checkExternalAvailability("Mock Book"));
    }

    @Test
    void fetchBookDetails_shouldAddBookIfExists(){
        System.out.println("Test service details de livre");
        when(mockExternalService.fetchBookDetails("Mock Book")).thenReturn(book);
        library.importBookFromExternal("Mock Book");
        assertEquals(book, library.getLivres().get(0));
        verify(mockExternalService, times(1)).fetchBookDetails("Mock Book");
    }

    @Test
    void fetchBookDetails_shouldThrowExceptionIfBookIsNull(){
        System.out.println("Test service details de livre");
        when(mockExternalService.fetchBookDetails("Mock Book")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> library.importBookFromExternal("Mock Book"));
        verify(mockExternalService, times(1)).fetchBookDetails("Mock Book");
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 10, 36})
    void checkExternalAvailability_shouldReturnTrueIfBookIsAvailable(int numVerifications){
        System.out.println("Test service disponibilité de livre");
            when(mockExternalService.isBookAvailable("Mock Book")).thenReturn(true);
        for (int i = 0; i < numVerifications; i++) {
            library.checkExternalAvailability("Mock Book");
        }
        verify(mockExternalService, times(numVerifications)).isBookAvailable("Mock Book");
    }

}
