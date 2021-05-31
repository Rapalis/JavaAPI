package Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class BookRepository {
    private final static String bookDataFile = "books.json";
    private final static String takenBooksFile = "taken.json";
    private final static String bookIdFile = "bookIDCounter.txt";
    private final static ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    private AtomicInteger idCounter;

    public BookRepository() throws IOException {
        File file = new File(bookDataFile);
        File takenFile = new File(takenBooksFile);
        File idFile = new File(bookIdFile);

        if(!takenFile.exists()) {
            takenFile.createNewFile();
            FileWriter writer = new FileWriter(takenFile);
            writer.write("[]");
            writer.close();
        }
        if(!file.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("[]");
            writer.close();
        }
        if(!idFile.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(idFile);
            writer.write("0");
            writer.close();
            idCounter = new AtomicInteger(0);
        }
        else{
            Scanner reader = new Scanner(idFile);
            idCounter = new AtomicInteger(reader.nextInt());
        }

    }
    public boolean exists(Integer guid) throws IOException {
        return !readCurrentBooks().stream().filter(b -> b.getGUID().equals(guid) ).collect(Collectors.toList()).isEmpty();
    }


    public void addBookTake(TakenBook takenBook) throws IOException {
        List<TakenBook> currentTakenBooks = readCurrentTakenBooks();
        currentTakenBooks.add(takenBook);
        objectMapper.writeValue(new File(takenBooksFile), currentTakenBooks);
    }

    public List<Book> getAllByAvailability(boolean availability) throws IOException {
        List<Book> currentBooks = readCurrentBooks();
        List<Book> filteredBooks = new ArrayList<Book>();

        for(int i = 0; i < currentBooks.size(); i++){
            if(isAvialible(currentBooks.get(i).getGUID(),LocalDate.now()) == availability){
                filteredBooks.add(currentBooks.get(i));
            }
        }
        return filteredBooks;
    }

    public boolean isAvialible(Integer id,LocalDate date) throws IOException {
        List<TakenBook> currentTakenBooks = readCurrentTakenBooks();
        currentTakenBooks = currentTakenBooks.stream().filter(b -> id.equals(b.getBookId())).collect(Collectors.toList());
        if(currentTakenBooks.size() == 0)
            return true;
        for(TakenBook value:currentTakenBooks){
            if(Period.between(date,value.returnDateEndOfKeepingPeriod()).getDays() > 0)
                return false;
        }

        return true;
    }
    public Integer getTakenBookCount(String personName) throws IOException {
        List<TakenBook> currentTakenBooks = readCurrentTakenBooks();
        long count = currentTakenBooks.stream().filter(b -> b.getPersonName().equals(personName)).count();
        return new Integer((int) count);
    }
    public void addBook(Book book) throws IOException {
        List<Book> currentBooks = readCurrentBooks();
        while(exists(idCounter.incrementAndGet()))
        {
            if(idCounter.intValue() == Integer.MAX_VALUE)
                throw new IllegalStateException("Out of bounds");
        }
        book.setGUID(idCounter.intValue());
        currentBooks.add(book);
        objectMapper.writeValue(new File(bookDataFile), currentBooks);
        saveIdCounter();
    }

    public void deleteBook(Integer id) throws IOException {
        List<Book> currentBooks = readCurrentBooks();
        currentBooks.removeIf(b -> b.getGUID().equals(id));
        objectMapper.writeValue(new File(bookDataFile), currentBooks);
    }

    private List<Book> readCurrentBooks() throws IOException {
        return new ArrayList<Book>(Arrays.asList(objectMapper.readValue(new File(bookDataFile), Book[].class)));
    }

    private List<TakenBook> readCurrentTakenBooks() throws IOException {
        return new ArrayList<TakenBook>(Arrays.asList(objectMapper.readValue(new File(takenBooksFile), TakenBook[].class)));
    }

    private void saveIdCounter () throws IOException {
        FileWriter writer = new FileWriter(bookIdFile);
        writer.write(String.valueOf(idCounter.intValue()));
        writer.close();
    }

    public List<Book> getAll() throws IOException {
        return readCurrentBooks();
    }

    public Book getById(Integer id) throws IOException {
        List<Book> currentBooks = readCurrentBooks();
        return currentBooks.stream().filter(b -> b.getGUID().equals(id)).collect(Collectors.toList()).get(0);
    }
}
