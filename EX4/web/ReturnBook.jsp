<%@page import="bean.Student"%>
<%@page import="bean.Book"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Link to external stylesheet for page styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
        
        <!-- Meta tags for character encoding -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Page Title -->
        <title>Return Book</title>
    </head>
    <body>
        <!-- Session validation -->
        <%
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

        <!-- Main content for returning books -->
        <div>
            <!-- Greeting the logged-in user -->
            <% 
                Student s = (Student) session.getAttribute("student");
            %>
            <h4>Hello, <%= s.getFirstName() %>!</h4>

            <h1>Return Book</h1>

            <!-- Displaying any messages passed from the ReturnBookServlet -->
            <%
                String msg = (String) request.getAttribute("msg");
                String color = (String) request.getAttribute("color");

                dal.DAO dao = new dal.DAO();
                
                // Fetch the list of books the student has borrowed
                ArrayList<Book> books = dao.getLoanList(s);
                
                // If no message is set and the book list is empty, show a message
                if (msg == null && color == null && books.size() == 0) {
                    msg = "No books found.";
                    color = "Red";
                }

                if (msg != null && color != null) {
            %>
            <!-- Display the message in the appropriate color -->
            <h3 style="color: <%=color%>"><%=msg%></h3>  
            <%
                }
            %>

            <!-- Form for returning books -->
            <form action="ReturnBookServlet">
                <h2>
                    <% 
                        // Loop through the list of borrowed books and display them with checkboxes
                        for (Book b : books) {
                            // Loop to show the book as many times as it has been loaned
                            while (b.getLoans() > 0) {
                    %>
                    <input type="checkbox" name="<%= b.getCode() %>"> <%= b.getTitle() + " by " + b.getAuthor() %> <br>
                    <%
                                // Decrement the loan count
                                b.setLoans(b.getLoans() - 1);
                            }
                        }
                    %>

                    <!-- Show the return button only if there are books to return -->
                    <% if (books.size() != 0) { %>
                    <input type="submit" value="Return">
                    <% } %>
                </h2>
            </form>
        </div>
    </body>
</html>
