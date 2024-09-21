package controller;

import bean.Book;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The AddBookServlet class is responsible for handling the addition of new books 
 * to the library system. It checks if the user is logged in and processes the 
 * request to add a new book. The servlet ensures that all required fields are 
 * provided and interacts with the DAO layer to store the new book details in the database.
 */
@WebServlet(name = "AddBookServlet", urlPatterns = {"/AddBookServlet"})
public class AddBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. It ensures the session is valid, checks for required fields, and
     * interacts with the DAO to add a new book to the system.
     *
     * @param request servlet request containing book details
     * @param response servlet response to return status messages
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the current session, if it exists.
        HttpSession session = request.getSession(false);

        // If the session is null or there is no logged-in student, redirect to the login page.
        if (session == null || session.getAttribute("student") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Retrieve the session and the parameters from the request.
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        String year = request.getParameter("date");
        String copies = request.getParameter("copies");

        // Check if any required fields are missing.
        if (code.isEmpty() || title.isEmpty() || author.isEmpty() || category.isEmpty() || year.isEmpty() || copies.isEmpty()) {
            request.setAttribute("msg", "You have to fill all of the details.");
            request.setAttribute("color", "red");
            RequestDispatcher rd = request.getRequestDispatcher("AddBook.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            // Create a new DAO instance to interact with the database.
            dal.DAO dao = new dal.DAO();

            // Add the new book to the database using the DAO method.
            dao.addBook(new Book(code, title, author, category, year, Integer.parseInt(copies)));

            // If successful, display a success message to the user.
            request.setAttribute("msg", "The book " + title + " by " + author + " has been added successfully.");
            request.setAttribute("color", "green");
        } catch (SQLException | ClassNotFoundException e) {
            // If an error occurs (e.g., a book with the same code already exists), display an error message.
            request.setAttribute("msg", "A book with the same code already exists.");
            request.setAttribute("color", "red");
        }

        // Forward the request back to the AddBook.jsp page to display the result.
        RequestDispatcher rd = request.getRequestDispatcher("AddBook.jsp");
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
        return "Servlet that handles the addition of new books to the library system.";
    }
}
