package dal;

import bean.Book;
import bean.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The DAO (Data Access Object) class handles all the database operations
 * for the library system, including adding books, registering students,
 * borrowing books, returning books, and retrieving information about
 * students and books.
 */
public class DAO {

    // Database connection details
    private String username;
    private String password;
    private String urlConnection;

    /**
     * DAO constructor, which initializes the database connection details.
     * It loads the JDBC driver for Apache Derby and sets the connection URL,
     * username, and password for the database.
     * 
     * @throws ClassNotFoundException If the JDBC driver class is not found
     */
    public DAO() throws ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        urlConnection = "jdbc:derby://localhost:1527/LibraryDB";
        username = "root";
        password = "root";
    }

    /**
     * Adds a new book to the database.
     * 
     * @param b The book object containing the book's details
     * @throws SQLException If an SQL error occurs
     */
    public void addBook(Book b) throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        String str = "INSERT INTO ROOT.BOOKS (CODE, TITLE, AUTHOR, CATEGORY, \"DATE\", COPIES, LOANS) \n"
                + "VALUES ('" + b.getCode() + "', '" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getCategory() + "', '" + b.getYear() + "', " + b.getCopies() + ", " + b.getLoans() + ")";
        statement.executeUpdate(str);

        statement.close();
        connection.close();
    }

    /**
     * Registers a new student in the database.
     * 
     * @param s The student object containing the student's details
     * @throws SQLException If an SQL error occurs
     */
    public void registerStudent(Student s) throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        String str = "INSERT INTO ROOT.STUDENTS (ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD) \n"
                + "VALUES ('" + s.getId() + "', '" + s.getFirstName() + "', '" + s.getLastName() + "', '" + s.getEmail() + "', '" + s.getPassword() + "')";
        statement.executeUpdate(str);

        statement.close();
        connection.close();
    }

    /**
     * Allows a student to borrow a book from the library.
     * 
     * @param code The code of the book to be borrowed
     * @param s The student who is borrowing the book
     * @return A Book object representing the borrowed book, or a special code
     * if the book is unavailable (-1) or not found (-2)
     * @throws SQLException If an SQL error occurs
     */
    public Book borrowBook(String code, Student s) throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM BOOKS");
        Book b = new Book("-2");  // Book with code -2 means book not found
        while (rs.next()) {
            if (rs.getString(1).equals(code)) {
                b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
                if (b.getLoans() >= b.getCopies()) {
                    b.setCode("-1");  // Book is unavailable (all copies are loaned out)
                    break;
                }

                // Check if the student already borrowed this book
                boolean flag = false;
                rs = statement.executeQuery("SELECT * FROM LOANS");
                while (rs.next()) {
                    if (rs.getString(1).equals(s.getId()) && rs.getString(2).equals(code)) {
                        // Update the loan count for the student if they already borrowed the book
                        String str = "UPDATE ROOT.LOANS SET LOANS = " + (rs.getInt(3) + 1) + " WHERE STUDENTID = '" + s.getId() + "' AND BOOKCODE = '" + code + "'";
                        statement.executeUpdate(str);
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    // Insert a new loan record if the student hasn't borrowed the book before
                    String str = "INSERT INTO ROOT.LOANS (STUDENTID, BOOKCODE, LOANS) \n"
                            + "VALUES ('" + s.getId() + "','" + b.getCode() + "'," + 1 + ")";
                    statement.executeUpdate(str);
                }

                // Update the number of loans for the book
                String str = "UPDATE ROOT.BOOKS SET LOANS = " + (b.getLoans() + 1) + " WHERE CODE = '" + b.getCode() + "'";
                statement.executeUpdate(str);

                break;
            }
        }

        statement.close();
        connection.close();
        return b;
    }

    /**
     * Allows a student to return a book to the library.
     * 
     * @param code The code of the book to be returned
     * @param SID The ID of the student returning the book
     * @return True if the return is successful, false otherwise
     * @throws SQLException If an SQL error occurs
     */
    public boolean returnBook(String code, String SID) throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        String update = "";
        ResultSet rs = statement.executeQuery("SELECT * FROM LOANS");
        while (rs.next()) {
            if (rs.getString(1).equals(SID) && rs.getString(2).equals(code)) {
                if (rs.getInt(3) > 1) {
                    // Decrease the loan count if more than 1 loan exists
                    update = "UPDATE ROOT.LOANS SET LOANS = " + (rs.getInt(3) - 1) + " WHERE STUDENTID = '" + SID + "' AND BOOKCODE = '" + code + "'";
                } else {
                    // Delete the loan record if only 1 loan exists
                    update = "DELETE FROM ROOT.LOANS WHERE STUDENTID = '" + SID + "' AND BOOKCODE = '" + code + "'";
                }
                break;
            }
        }
        int res = statement.executeUpdate(update);

        boolean flag = false;
        if (res != 0) {
            // If the loan update was successful, decrease the number of loans in the BOOKS table
            rs = statement.executeQuery("SELECT * FROM BOOKS");
            while (rs.next()) {
                if (rs.getString(1).equals(code)) {
                    update = "UPDATE ROOT.BOOKS SET LOANS = " + (rs.getInt(7) - 1) + " WHERE CODE = '" + code + "'";
                    statement.executeUpdate(update);
                    flag = true;
                    break;
                }
            }
        }

        statement.close();
        connection.close();
        return flag;
    }

    /**
     * Retrieves a list of all books from the database.
     * 
     * @return An ArrayList of Book objects representing all the books in the library
     * @throws SQLException If an SQL error occurs
     */
    public ArrayList<Book> getBooks() throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM ROOT.BOOKS");
        ArrayList<Book> books = new ArrayList<>();

        while (rs.next()) {
            books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
        }

        statement.close();
        connection.close();
        return books;
    }

    /**
     * Retrieves a list of all students from the database.
     * 
     * @return An ArrayList of Student objects representing all the students
     * @throws SQLException If an SQL error occurs
     */
    public ArrayList<Student> getStudents() throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM ROOT.STUDENTS");
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()) {
            students.add(new Student(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5)));
        }

        statement.close();
        connection.close();
        return students;
    }

    /**
     * Retrieves the list of books currently borrowed by a specific student.
     * 
     * @param s The student for whom to retrieve the loaned books
     * @return An ArrayList of Book objects representing the books borrowed by the student
     * @throws SQLException If an SQL error occurs
     */
    public ArrayList<Book> getLoanList(Student s) throws SQLException {
        Connection connection = DriverManager.getConnection(urlConnection, username, password);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM ROOT.LOANS");
        ArrayList<Book> books = getBooks();
        ArrayList<Book> sBooks = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString(1).equals(s.getId())) {
                String code = rs.getString(2);
                for (Book b : books) {
                    if (b.getCode().equals(code)) {
                        b.setLoans(rs.getInt(3));
                        sBooks.add(b);
                        break;
                    }
                }
            }
        }

        statement.close();
        connection.close();
        return sBooks;
    }
}
