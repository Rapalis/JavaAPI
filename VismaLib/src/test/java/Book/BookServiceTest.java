package Book;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoFramework;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    private BookService testingSubject;
    private AutoCloseable closeable;

    @BeforeEach
    void init(){
        closeable = MockitoAnnotations.openMocks(this);
        testingSubject = new BookService(bookRepository);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void testGetAllBooks() throws IOException {
        testingSubject.getAllBooksWithFilters(null,null,null,null,null,null);
        verify(bookRepository).getAll();
    }

    @Test
    void testGetByAvailability() throws IOException {
        testingSubject.getAllBooksWithFilters(null,null,null,null,null,true);
        verify(bookRepository).getAllByAvailability(true);
    }

    @Test
    void testDeleteBookWhenNotTaken() throws IOException {
        testingSubject.deleteBook(1);
        verify(bookRepository).deleteBook(1);
    }

    @Test
    void testDeleteBookWhenTaken() throws IOException {
        given(bookRepository.exists(any())).willReturn(false);
        Exception exception = assertThrows(ResponseStatusException.class,() -> testingSubject.deleteBook(1));
        String expectedMessage = "This book doesn't exists in the database";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}