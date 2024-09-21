-- Create the Students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,   -- Unique identifier for each student
    first_name VARCHAR(50) NOT NULL,             -- Student's first name
    last_name VARCHAR(50) NOT NULL,              -- Student's last name
    email VARCHAR(100) NOT NULL UNIQUE,          -- Student's email (must be unique)
    password VARCHAR(255) NOT NULL               -- Hashed password for the student
);

-- Create the Books table
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,      -- Unique identifier for each book
    title VARCHAR(255) NOT NULL,                 -- Title of the book
    author VARCHAR(100) NOT NULL,                -- Author of the book
    category VARCHAR(100),                       -- Category/genre of the book
    year INT,                                    -- Year of publication
    copies INT DEFAULT 1,                        -- Total number of copies of the book
    available_copies INT DEFAULT 1               -- Number of available copies for borrowing
);

-- Create the Loans table (tracks which students have borrowed which books)
CREATE TABLE loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,      -- Unique identifier for each loan record
    student_id INT,                              -- Foreign key referencing students
    book_id INT,                                 -- Foreign key referencing books
    borrow_date DATE NOT NULL,                   -- Date when the book was borrowed
    return_date DATE,                            -- Date when the book was returned (can be NULL if not returned yet)
    returned BOOLEAN DEFAULT FALSE,              -- Whether the book has been returned
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE, -- Cascading delete if student is removed
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE           -- Cascading delete if book is removed
);

-- Insert some sample data for testing

-- Add students
INSERT INTO students (first_name, last_name, email, password)
VALUES
('John', 'Doe', 'johndoe@example.com', 'hashedpassword1'),   -- Sample student data
('Jane', 'Smith', 'janesmith@example.com', 'hashedpassword2');

-- Add books
INSERT INTO books (title, author, category, year, copies, available_copies)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 1925, 5, 5),   -- Sample book data
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 1960, 3, 3),
('1984', 'George Orwell', 'Dystopian', 1949, 4, 4);

-- Add loan records (example of loans made by students)
INSERT INTO loans (student_id, book_id, borrow_date, return_date, returned)
VALUES
(1, 1, '2023-09-01', NULL, FALSE),   -- John Doe borrowed "The Great Gatsby"
(2, 2, '2023-09-03', NULL, FALSE);   -- Jane Smith borrowed "To Kill a Mockingbird"

-- View the data
SELECT * FROM students;   -- View all students
SELECT * FROM books;      -- View all books
SELECT * FROM loans;      -- View all loan records
