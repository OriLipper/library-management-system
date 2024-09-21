<%@page import="bean.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="bean.Book" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Link to external stylesheet for page styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
        
        <!-- Meta tags for character encoding -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Page Title -->
        <title>Search Book</title>
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

        <!-- Main content section for searching books -->
        <div>
            <!-- Greeting the logged-in student -->
            <%
                Student s = (Student) session.getAttribute("student");
            %>
            <h4>Hello, <%= s.getFirstName() %>!</h4>

            <h1>Search Book</h1>

            <!-- Displaying any messages passed from the SearchBookServlet -->
            <%
                String msg = (String) request.getAttribute("msg");
                String color = (String) request.getAttribute("color");

                if (msg != null && color != null) {
            %>
            <!-- Display the message in the specified color -->
            <h3 style="color:<%=color%>"><%=msg%></h3>  
            <%
                }
            %>

            <!-- Search form for finding books by description -->
            <form action="SearchBookServlet">
                <h2>
                    Book Description: <input type="text" name="desk" required> <br>
                    <input type="submit" value="Search"> 
                    <input type="reset" value="Reset">
                </h2>
            </form>

            <!-- Display the search results -->
            <%
                ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("books");

                if (books != null) {
                    for (Book b : books) {
                        // Set the text color based on book availability
                        String str = (b.getLoans() < b.getCopies()) ? "green" : "red";
            %>
            <!-- Display the book with a link to borrow it if available -->
            <a href="ClickBorrowBookServlet?bookCode=<%=b.getCode()%>">
                <h3 style="color:<%=str%>"><%=b.getTitle() + " by " + b.getAuthor()%></h3>
            </a>
            <%
                    }
                }
            %>
        </div>
    </body>
</html>
