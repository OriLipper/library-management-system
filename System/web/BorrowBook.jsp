<%@page import="bean.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Link to the external CSS file for styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Borrow Book</title>
    </head>
    <body>
        <%
            // Check if a valid session exists. If not, redirect to the login page (index.jsp)
            session = request.getSession(false);
            if (session == null || session.getAttribute("student") == null) {
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }
        %>

        <!-- Navigation menu -->
        <ul>
            <li><a href="HomePage.jsp">Home</a></li>
            <li><a href="BorrowBook.jsp">Borrow Book</a></li>
            <li><a href="ReturnBook.jsp">Return Book</a></li>
            <li><a href="SearchBook.jsp">Search Book</a></li>
            <li><a href="AddBook.jsp">Add Book</a></li>    
            <li><a href="LogoutServlet">Log Out</a></li>  
        </ul>

        <!-- Main content for borrowing a book -->
        <div>
            <%
                // Retrieve the logged-in student from the session and display a greeting
                Student s = (Student) session.getAttribute("student");
            %>
            <h4>Hello <%= s.getFirstName() %></h4>

            <h1>Borrow Book</h1>

            <%
                // Retrieve any messages (like success or error messages) set by the servlet
                String msg = (String) request.getAttribute("msg");
                String color = (String) request.getAttribute("color");

                if (msg != null && color != null) {
            %>
            <!-- Display the message in the appropriate color -->
            <h3 style="color: <%= color %>"> <%= msg %> </h3>  
            <%
                }
            %> 

            <!-- Form for borrowing a book -->
            <form action="BorrowBookServlet">
                <h2>
                    Book Code: <input type="number" name="code" required> <br>
                    <input type="submit" value="Borrow"> 
                    <input type="reset" value="Reset">
                </h2>
            </form>
        </div>

    </body>
</html>
