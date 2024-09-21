package controller;

import bean.Book;
import bean.Student;
import dal.DAO;
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
 * The ReturnBookServlet class handles the process of returning books borrowed by a student.
 * It retrieves the list of books the student has borrowed and processes the return request
 * by interacting with the DAO to update the loan status in the database.
 */
@WebServlet(name = "ReturnBookServlet", urlPatterns = {"/ReturnBookServlet"})
public class ReturnBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It retrieves the list of books borrowed by the student and processes the return request.
     * If the return is successful, it forwards a success message; otherwise, it handles errors.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the session and check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            // If no session or student, redirect to the login page
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            // DAO handles interaction with the database
            DAO dao = new DAO();
            
            // Get the logged-in student from the session
            Student s = (Student) session.getAttribute("student");

            // Retrieve the list of books borrowed by the student
            ArrayList<Book> books = dao.getLoanList(s);

            // Iterate over each book and check if it's selected for return
            for (Book b : books) {
                if (request.getParameter(b.getCode()) != null) {
                    // If the book's checkbox is selected, return the book
                    dao.returnBook(b.getCode(), s.getId());
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            // If an error occurs, forward to the error page
            RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
            rd.forward(request, response);
            return;
        }

        // Set a success message after all selected books are returned
        request.setAttribute("msg", "Books have been returned successfully.");
        request.setAttribute("color", "green");

        // Forward the user back to the ReturnBook.jsp page with the success message
        RequestDispatcher rd = request.getRequestDispatcher("ReturnBook.jsp");
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
        return "Servlet that handles the return of borrowed books by students.";
    }
}
