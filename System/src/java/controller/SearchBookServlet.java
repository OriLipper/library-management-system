package controller;

import bean.Book;
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
 * The SearchBookServlet class handles the search functionality for books.
 * It retrieves the search input from the user and searches for books based on
 * various criteria such as the book's code, title, category, author, or year.
 * If any matching books are found, they are displayed to the user.
 */
@WebServlet(name = "SearchBookServlet", urlPatterns = {"/SearchBookServlet"})
public class SearchBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It retrieves the search input from the form, interacts with the DAO to get
     * the list of all books, and filters the books that match the search criteria.
     * If no matching books are found, an error message is shown.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the session to ensure that the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            // If no session or student, redirect to the login page
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Get the search input (the descriptor) from the request
        String desk = request.getParameter("desk");

        // List to store matching books
        ArrayList<Book> matchBooks = new ArrayList<>();
        ArrayList<Book> books = null;

        try {
            // DAO handles interaction with the database
            dal.DAO dao = new dal.DAO();
            // Retrieve the list of all books from the database
            books = dao.getBooks();
        } catch (ClassNotFoundException | SQLException e) {
            // If there's a database error, forward to the error page
            RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
            rd.forward(request, response);
            return;
        }

        // If books were retrieved from the database, search through them
        if (books != null) {
            for (Book b : books) {
                // Check if the search descriptor matches the book's code, title, category, author, or year
                if (b.getCode().equals(desk) || b.getTitle().equals(desk) || b.getCategory().equals(desk) || b.getAuthor().equals(desk) || b.getYear().equals(desk)) {
                    matchBooks.add(b); // Add matching books to the result list
                }
            }
        }

        // If no matching books are found, show an error message
        if (matchBooks.isEmpty()) {
            request.setAttribute("msg", "No books found.");
            request.setAttribute("color", "red");
        } else {
            // If books are found, set the matching books as a request attribute
            request.setAttribute("books", matchBooks);
        }

        // Forward the user to SearchBook.jsp with the results or error message
        RequestDispatcher rd = request.getRequestDispatcher("SearchBook.jsp");
        rd.forward(request, response);
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
        return "Servlet that handles searching for books based on a user-provided descriptor.";
    }
}
