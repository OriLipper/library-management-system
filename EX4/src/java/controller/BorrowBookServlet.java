package controller;

import bean.Book;
import bean.Student;
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
 * The BorrowBookServlet class handles the process of borrowing books from the
 * library. It verifies that the student is logged in, processes the request to 
 * borrow a book, and interacts with the DAO layer to handle the book borrowing 
 * logic. Depending on the outcome, the servlet forwards the user to the 
 * appropriate page with the relevant message.
 */
@WebServlet(urlPatterns = {"/BorrowBookServlet"})
public class BorrowBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It checks if the user is logged in, retrieves the book details from the DAO,
     * and then forwards the user to the BorrowBook.jsp page with a message indicating
     * whether the book was successfully borrowed, unavailable, or not found.
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
            // If there is no session or the student is not logged in, redirect to login page
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }
        
        // Get the book code from the request
        String code = request.getParameter("code");

        // Get the session and retrieve the logged-in student object
        session = request.getSession();
        Student s = (Student) session.getAttribute("student");

        try {
            // DAO handles interaction with the database
            dal.DAO dao = new dal.DAO();

            // Try to borrow the book using the DAO method
            Book b = dao.borrowBook(code, s);

            // Handle the response based on the code returned from the borrowBook method
            switch (b.getCode()) {
                case "-1":
                    // The book isn't available (e.g., no copies left)
                    request.setAttribute("msg", "The book " + b.getTitle() + " by " + b.getAuthor() + " isn't available.");
                    request.setAttribute("color", "red");
                    break;
                case "-2":
                    // No matches found (invalid code or no such book)
                    request.setAttribute("msg", "No matches found.");
                    request.setAttribute("color", "red");
                    break;
                default:
                    // Successfully borrowed the book
                    request.setAttribute("msg", "The book " + b.getTitle() + " by " + b.getAuthor() + " has been borrowed successfully.");
                    request.setAttribute("color", "green");
                    break;
            }

            // Forward to the BorrowBook.jsp page to display the message
            RequestDispatcher rd = request.getRequestDispatcher("BorrowBook.jsp");
            rd.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            // If there's an error with the database, forward to the error page
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
        return "Servlet that handles the process of borrowing books from the library.";
    }
}
