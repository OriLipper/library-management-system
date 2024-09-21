package controller;

import dal.DAO;
import bean.Student;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The RegisterServlet class handles the registration of new students.
 * It collects information from the registration form, validates the input,
 * and then registers the student in the system by interacting with the DAO.
 * If a student with the same ID already exists, an error message is displayed.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It retrieves the student details from the form, validates them, and registers the
     * student if the details are valid. If a student with the same ID already exists,
     * it returns an error.
     *
     * @param request servlet request containing the student's registration details
     * @param response servlet response to display success or error messages
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the form parameters for student registration
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate that all required fields are filled in
        if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // If any fields are empty, set an error message and return to the registration page
            request.setAttribute("msg", "You need to fill the whole application.");
            request.setAttribute("color", "red");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        // Create a new Student object with the form details
        Student s = new Student(firstName, lastName, id, email, password);

        try {
            // DAO handles interaction with the database to register the student
            DAO dao = new DAO();
            dao.registerStudent(s);

            // If successful, set a success message and forward back to the login page
            request.setAttribute("msg", "Student has registered successfully.");
            request.setAttribute("color", "green");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            // If the student already exists, show an error message
            request.setAttribute("msg", "A student with the same ID already exists.");
            request.setAttribute("color", "red");
            RequestDispatcher rd = request.getRequestDispatcher("RegisterStudent.jsp");
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
        return "Servlet that handles student registration.";
    }
}
