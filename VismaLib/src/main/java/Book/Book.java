package Book;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonSerialize
public class Book {
    private String name;
    private String author;
    private String category;
    private String language;
    private LocalDate publicationDate;
    private String ISBN;
    private Integer GUID;


    public Book() {

    }
    public Book(String name, String author, String category, String language, LocalDate publicationDate, String isbn) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publicationDate = publicationDate;
        ISBN = isbn;
    }
    public Book(String name, String author, String category, String language, LocalDate publicationDate, String isbn, Integer guid) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publicationDate = publicationDate;
        ISBN = isbn;
        GUID = guid;
    }
    public Integer getGUID() {
        return GUID;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getPublicationDate() {
        return publicationDate.toString();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = LocalDate.parse(publicationDate);
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setGUID(Integer GUID) {
        this.GUID = GUID;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", publicationDate=" + publicationDate +
                ", ISBN='" + ISBN + '\'' +
                ", GUID=" + GUID +
                '}';
    }

}
