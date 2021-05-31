package Book;

import java.time.LocalDate;

public class TakenBook {
    private Integer bookId;
    private String personName;
    private LocalDate endOfKeepingPeriod;

    public TakenBook() {
    }

    public TakenBook(String personName, LocalDate endOfKeepingPeriod) {
        this.personName = personName;
        this.endOfKeepingPeriod = endOfKeepingPeriod;
    }
    public TakenBook(Integer bookId, String personName, LocalDate endOfKeepingPeriod) {
        this.bookId = bookId;
        this.personName = personName;
        this.endOfKeepingPeriod = endOfKeepingPeriod;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getPersonName() {
        return personName;
    }

    public String getEndOfKeepingPeriod() {
        return endOfKeepingPeriod.toString();
    }

    public LocalDate returnDateEndOfKeepingPeriod()
    {
        return endOfKeepingPeriod;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setEndOfKeepingPeriod(LocalDate endOfKeepingPeriod) {
        this.endOfKeepingPeriod = endOfKeepingPeriod;
    }
    public void setEndOfKeepingPeriod(String endOfKeepingPeriod) {
        this.endOfKeepingPeriod = LocalDate.parse(endOfKeepingPeriod);
    }
}
