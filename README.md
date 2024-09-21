# Library Management System

A comprehensive web-based Library Management System designed to manage book borrowing and returns. This system allows students to register, search for books, borrow books, and return them, with all operations tracked via a database. Built using JavaServer Pages (JSP) for the backend and an Apache Derby database for data storage, this system is suitable for small to medium-sized academic institutions or libraries.

## Features

- **User Registration & Authentication**:
  - Students can create accounts with unique credentials.
  - Passwords are securely stored in the database.
  
- **Book Search**:
  - Search by title, author, or category.
  - Real-time updates on available and borrowed books.
  
- **Borrowing and Returning**:
  - Students can borrow available books, and the system tracks borrowed and returned books.
  - Users are notified of successful borrowing or when no copies are available.
  
- **Loan Management**:
  - Tracks the date a book is borrowed and the expected return date.
  - Admins can easily view loaned books and manage returns.

## Project Structure

### Backend

- **JSP (JavaServer Pages)**: Used for dynamic server-side rendering of HTML and handling HTTP requests. It allows the system to interact with the database and perform operations like user registration, book borrowing, and searching.
  
- **Servlets**: Java Servlets handle requests and responses in the background. Servlets such as `LoginServlet`, `BorrowBookServlet`, `ReturnBookServlet`, and others perform the core logic of the system, such as authenticating users and interacting with the database.

- **Java Beans**: Java Beans (`Book`, `Student`) are used to encapsulate the data models for books and students. These are simple Java classes that define the structure of data and provide getter/setter methods.

### Database

- **Apache Derby**: A lightweight, open-source, relational database management system (RDBMS) used for data persistence. It stores student records, books, and loan details. The database schema includes tables for:
  - **Students**: Holds information like names, emails, and passwords.
  - **Books**: Stores information about the books available in the library.
  - **Loans**: Tracks borrowed books, including dates and student information.

### Frontend

- **HTML/CSS**: Simple, clean front-end design that uses HTML and CSS to create the user interface for student interactions such as book search, registration, and loan management.

## Setup Instructions

### 1. Prerequisites

To run this project locally, you will need the following installed:

- **JDK (Java Development Kit) Version 8 or higher**
- **Apache Derby**: Used as the database.
- **GlassFish Server (or any compatible servlet container)**: To deploy and run the JSP application.
- **IDE**: NetBeans, IntelliJ IDEA, or Eclipse for easy project setup.

### 2. Cloning the Repository

Clone the repository to your local machine using Git:
```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

### 3. Setting Up the Database

1. **Start Apache Derby**:
   - Ensure Apache Derby is running on your machine.

2. **Create the Database**:
   - In your IDE or via the command line, run the `library_db_setup.sql` script located in the root folder to create the necessary database tables:
   ```bash
   ij> CONNECT 'jdbc:derby://localhost:1527/LibraryDB;create=true';
   ij> RUN 'library_db_setup.sql';
   ```

3. **Verify the Database**:
   - Ensure the `students`, `books`, and `loans` tables are successfully created by running a few `SELECT` queries.

### 4. Configuring and Deploying the Application

1. **Import the Project**:
   - Open the project in your IDE (e.g., NetBeans or IntelliJ IDEA).
   
2. **Set Up the Server**:
   - In your IDE, configure the GlassFish (or Tomcat) server as the target runtime.
   
3. **Build and Deploy**:
   - Build the project in your IDE, and deploy it to the server.

4. **Access the Application**:
   - Open your browser and navigate to:
   ```bash
   http://localhost:8080/library-management
   ```

### 5. Testing the System

Once deployed, you can test the following features:

- **Student Registration**: Create new student accounts via the registration page.
- **Login**: Authenticate with the registered email and password.
- **Book Search**: Use the search feature to find books by title, author, or category.
- **Borrow/Return Books**: Borrow available books and return them when finished. The system will automatically update the number of available copies.

## Example Workflow

1. **Register**: A new student registers on the platform using their email and personal details.
   
2. **Login**: The student logs in and is directed to their dashboard where they can see the library's functionalities.

3. **Search for Books**: The student uses the search bar to find books based on their title or author.

4. **Borrowing a Book**: The student selects an available book to borrow. The system updates the number of available copies and registers the loan in the database.

5. **Returning a Book**: When the student returns the book, the system updates the loan record and restores the available copies in the library.

## Technology Stack

- **Java**: Core programming language used for server-side logic and database interaction.
- **JSP (JavaServer Pages)**: Handles dynamic content rendering and request/response management.
- **GlassFish**: Application server for deploying JSP and Servlets.
- **Apache Derby**: Database for storing student, book, and loan information.
- **HTML/CSS**: Provides the frontend user interface.

## Contribution

Contributions are welcome! If you have suggestions, feel free to submit a pull request or open an issue on GitHub. Please make sure to follow the coding standards and document your changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

- **Ori Lipper** - Initial work on the Library Management System.
