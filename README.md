# Library Management System

A web-based Library Management System that allows students to borrow and return books. The system tracks book availability, student loans, and supports operations such as book search, user registration, and authentication.

## Features

- **User Registration**: Allows students to register and create accounts.
- **Book Search**: Students can search for books by title, author, or category.
- **Borrow and Return Books**: Students can borrow books, and the system tracks available copies.
- **Loan Management**: Tracks loaned books, including loan and return dates.

## Technology Stack

- **Backend**: JSP (JavaServer Pages)
- **Frontend**: HTML, CSS
- **Database**: Derby (Apache Derby Database)
- **Server**: GlassFish (or any compatible servlet container)
- **Java**: Java 8 or higher

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   cd library-management-system
   ```

2. Import the project into your preferred IDE (like NetBeans or IntelliJ IDEA).

3. Configure the database:
   - Make sure the Derby database is running.
   - Run the `library_db_setup.sql` script to set up the database.

4. Deploy the project to your local server (e.g., GlassFish).

5. Access the application in your browser at `http://localhost:8080/library-management`.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Ori Lipper