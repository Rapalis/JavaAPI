package Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooksEndPoint(@RequestParam(required = false) String author,
                                          @RequestParam(required = false) String category,
                                          @RequestParam(required = false) String language,
                                          @RequestParam(required = false) String ISBN,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) Boolean isAvailable) throws IOException {

        return bookService.getAllBooksWithFilters(author,category,language,ISBN,name,isAvailable);
    }
    @GetMapping(path = "{bookId}")
    public Book getBooksEndPoint(@PathVariable("bookId") Integer id) throws IOException {
        return bookService.getBookByID(id);
    }
    @PostMapping
    public void addBookEndPoint(@RequestBody Book book) throws IOException {
        bookService.addBook(book);
    }
    @PostMapping (path = "{bookId}")
    public void registerBookTakeEndPoint(@RequestBody TakenBook takenBook,@PathVariable("bookId") Integer id) throws IOException {
        bookService.takeBook(id,takenBook);
    }
    @DeleteMapping(path = "{bookId}")
    public void deleteBookEndPoint(@PathVariable("bookId") Integer id) throws IOException {
        bookService.deleteBook(id);
    }
}
