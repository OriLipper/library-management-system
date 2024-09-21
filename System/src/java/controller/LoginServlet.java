package controller;

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
 * The LoginServlet class handles user login functionality. It validates
 * the user's email and password against the list of students from the database.
 * If the credentials are correct, the student is logged in and redirected to 
 * the homepage; otherwise, an error message is displayed.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It retrieves the email and password from the request, fetches students from
     * the database, and checks if the credentials match any student record.
     * If successful, the user is logged in; otherwise, an error is shown.
     *
     * @param request servlet request containing login credentials (email, password)
     * @param response servlet response to display messages or redirect the user
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the login credentials from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // List to store students fetched from the database
        ArrayList<Student> students = null;

        try { 
            // DAO handles interaction with the database to retrieve the list of students
            dal.DAO dao = new dal.DAO();
            students = dao.getStudents();
        } catch (ClassNotFoundException | SQLException e) {
            // If there's an error with the database, forward to the error page
            RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
            rd.forward(request, response);
            return;
        }

        // Loop through the list of students and check if the credentials match
        for (Student s : students) {
            if (s.getEmail().equals(email) && s.getPassword().equals(password)) {
                // If credentials match, create a session and set the student attribute
                HttpSession session = request.getSession();
                session.setAttribute("student", s);

                // Forward the user to the HomePage.jsp
                RequestDispatcher rd = request.getRequestDispatcher("HomePage.jsp");
                rd.forward(request, response);
                return; // Exit after a successful login
            }
        }

        // If no match is found, set an error message and forward back to login page
        request.setAttribute("msg", "Username or password is incorrect.");
        request.setAttribute("color", "red");

        // Forward the user back to index.jsp with the error message
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
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
        return "Servlet that handles user login and session management.";
    }
}
