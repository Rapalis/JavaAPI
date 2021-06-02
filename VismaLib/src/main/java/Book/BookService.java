package Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) throws IOException {
        bookRepository.addBook(book);
    }

    public void deleteBook(Integer id) throws IOException {
        if(!bookRepository.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book doesn't exists in the database");
        bookRepository.deleteBook(id);
    }

    public Book getBookByID(Integer id) throws IOException {
        if(!bookRepository.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book doesn't exists in the database");
        return bookRepository.getById(id);
    }

    public void takeBook(Integer id, TakenBook takenBook) throws IOException {
        if(!bookRepository.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book does not exists");
        if(Period.between(LocalDate.now(),takenBook.returnDateEndOfKeepingPeriod()).getMonths() > 2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Taking for longer than 2 months");
        if(bookRepository.getTakenBookCount(takenBook.getPersonName()) >= 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Taking more than 3 books aren't allowed");
        if(!bookRepository.isAvialible(id,LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is not available at the moment");
        takenBook.setBookId(id);
        bookRepository.addBookTake(takenBook);
    }

    public List<Book> getAllBooksWithFilters(String author, String category, String language, String isbn, String name, Boolean isAvailable) throws IOException {
        List<Book> allBooks;
        Stream<Book> bookStream;
        if(isAvailable != null)
            allBooks = bookRepository.getAllByAvailability(isAvailable.booleanValue());
        else
            allBooks = bookRepository.getAll();
        bookStream = allBooks.stream();
        if(author != null)
            bookStream = allBooks.stream().filter(b -> b.getAuthor().equals(author));
        if(category != null)
            bookStream = allBooks.stream().filter(b -> b.getCategory().equals(category));
        if(language != null)
            bookStream = allBooks.stream().filter(b -> b.getLanguage().equals(language));
        if(isbn != null)
            bookStream = allBooks.stream().filter(b -> b.getISBN().equals(isbn));
        if(name != null)
            bookStream = allBooks.stream().filter(b -> b.getName().equals(name));
        return bookStream.collect(Collectors.toList());
    }
}
