package controller;

import bean.Book;
import bean.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The ClickBorrowBookServlet class handles the borrowing process when a user 
 * clicks on a specific book to borrow it from the search results. 
 * It checks the user's session, validates the book code, 
 * and interacts with the DAO layer to handle the borrowing process.
 */
@WebServlet(name = "ClickBorrowBookServlet", urlPatterns = {"/ClickBorrowBookServlet"})
public class ClickBorrowBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It checks if the user is logged in, retrieves the book details from the DAO,
     * and then forwards the user to the SearchBook.jsp page with a message indicating
     * whether the book was successfully borrowed or not.
     *
     * @param request servlet request containing the book's code
     * @param response servlet response to display messages or errors
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the session to ensure that the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            // If no session or the student is not logged in, redirect to login page
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Retrieve the book code from the request
        String bookCode = request.getParameter("bookCode");
        ArrayList<Book> books = null;

        try {
            // DAO handles interaction with the database
            dal.DAO dao = new dal.DAO();
            
            // Fetch all books from the database
            books = dao.getBooks();

            // Iterate through the list of books to find the one with the matching code
            for (Book b : books) {
                if (b.getCode().equals(bookCode)) {
                    // If the book is found, proceed with borrowing
                    session = request.getSession();
                    Student s = (Student) session.getAttribute("student");

                    // Attempt to borrow the book
                    Book bBook = dao.borrowBook(bookCode, s);

                    // If the book is not available
                    if (bBook.getCode().equals("-1")) {
                        request.setAttribute("msg", "The book " + b.getTitle() + " by " + b.getAuthor() + " isn't available.");
                        request.setAttribute("color", "red");
                    } else {
                        // If the book was borrowed successfully
                        request.setAttribute("msg", "The book " + b.getTitle() + " by " + b.getAuthor() + " has been borrowed successfully.");
                        request.setAttribute("color", "green");
                    }

                    // Forward to the SearchBook.jsp page to display the message
                    RequestDispatcher rd = request.getRequestDispatcher("SearchBook.jsp");
                    rd.forward(request, response);
                    return; // Ensure the loop is not continued after forwarding
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // If there's a database error, forward to the error page
            RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
            rd.forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method by calling processRequest.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method by calling processRequest.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Provides a brief description of the servlet.
     *
     * @return A string containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet that handles the borrowing of books after the user clicks on a book from the search results.";
    }
}
