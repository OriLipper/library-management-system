package bean;

import java.io.Serializable;

/**
 * The Book class represents a book in the library system.
 * It includes attributes such as the book's code, title, author, category,
 * year of publication, number of copies available, and number of loans.
 * Implements Serializable interface to allow for object serialization.
 * This class provides getter and setter methods for each attribute, as well
 * as constructors for different initialization scenarios.
 */
public class Book implements Serializable {
    
    // Attributes of the Book class
    private String code;      // Unique identifier for the book
    private String title;     // Title of the book
    private String author;    // Author of the book
    private String category;  // Category/Genre of the book
    private String year;      // Year of publication
    private int copies;       // Number of copies available in the library
    private int loans;        // Number of copies currently loaned out

    /**
     * Default constructor - Initializes a Book object with default values.
     */
    public Book() { }
    
    /**
     * Constructor to initialize a Book object with a book code.
     * @param code Unique identifier for the book
     */
    public Book(String code) {
        this.code = code;
    }

    /**
     * Constructor to initialize a Book object with basic book details.
     * @param code Unique identifier for the book
     * @param title Title of the book
     * @param author Author of the book
     * @param category Category/Genre of the book
     * @param year Year of publication
     * @param copies Number of copies available in the library
     */
    public Book(String code, String title, String author, String category, String year, int copies) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.copies = copies;
    }

    /**
     * Constructor to initialize a Book object with all attributes.
     * @param code Unique identifier for the book
     * @param title Title of the book
     * @param author Author of the book
     * @param category Category/Genre of the book
     * @param year Year of publication
     * @param copies Number of copies available
     * @param loans Number of copies currently loaned out
     */
    public Book(String code, String title, String author, String category, String year, int copies, int loans) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.copies = copies;
        this.loans = loans;
    }

    /**
     * Gets the unique code of the book.
     * @return The book's code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code of the book.
     * @param code The book's code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the title of the book.
     * @return The book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title The book's title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     * @return The book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * @param author The book's author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the category/genre of the book.
     * @return The book's category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category/genre of the book.
     * @param category The book's category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the year of publication of the book.
     * @return The book's year of publication
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year of publication of the book.
     * @param year The book's year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Gets the number of copies available in the library.
     * @return The number of copies available
     */
    public int getCopies() {
        return copies;
    }

    /**
     * Sets the number of copies available in the library.
     * @param copies The number of copies to set
     */
    public void setCopies(int copies) {
        this.copies = copies;
    }

    /**
     * Gets the number of copies currently loaned out.
     * @return The number of loans
     */
    public int getLoans() {
        return loans;
    }

    /**
     * Sets the number of copies currently loaned out.
     * @param loans The number of loans to set
     */
    public void setLoans(int loans) {
        this.loans = loans;
    }
}
