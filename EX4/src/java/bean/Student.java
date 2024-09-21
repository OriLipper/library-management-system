package bean;

import java.io.Serializable;

/**
 * The Student class represents a student in the library system.
 * It includes attributes such as the student's first name, last name, ID,
 * email, and password. The class implements Serializable to allow the 
 * object to be serialized if needed.
 * This class provides getter and setter methods for each attribute, as well
 * as constructors for different initialization scenarios.
 */
public class Student implements Serializable {

    // Attributes of the Student class
    private String firstName;  // Student's first name
    private String lastName;   // Student's last name
    private String id;         // Student's unique ID
    private String email;      // Student's email address
    private String password;   // Student's password (should be hashed in practice)

    /**
     * Constructor to initialize a Student object with all details.
     * @param firstName The first name of the student
     * @param lastName The last name of the student
     * @param id The unique ID of the student
     * @param email The email address of the student
     * @param password The password for the student account
     */
    public Student(String firstName, String lastName, String id, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Default constructor - Initializes a Student object with default values.
     */
    public Student() { }

    /**
     * Gets the student's password.
     * @return The student's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the student's password.
     * @param password The password to set for the student
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the student's first name.
     * @return The first name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the student's first name.
     * @param firstName The first name to set for the student
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the student's last name.
     * @return The last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the student's last name.
     * @param lastName The last name to set for the student
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the student's unique ID.
     * @return The student's unique ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the student's unique ID.
     * @param id The unique ID to set for the student
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the student's email address.
     * @return The email address of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the student's email address.
     * @param email The email address to set for the student
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
