<%@page import="bean.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Link to the external CSS file for styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
        
        <!-- Set the character encoding for the page -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Meta description for search engines -->
        <meta name="description" content="Netanya Academic Library">
        
        <!-- Meta keywords for search engines -->
        <meta name="keywords" content="HTML, CSS, Library, Netanya, Academic">

        <!-- Page title -->
        <title>Home - Netanya Academic Library</title>
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

        <!-- Main content section -->
        <div>
            <%
                // Retrieve the logged-in student from the session and display a greeting
                Student s = (Student) session.getAttribute("student");
            %>
            <h4>Hello, <%= s.getFirstName() %>!</h4>

            <h1><u>Welcome to Netanya Academic Library</u></h1>
            <p>
                The Netanya Academic College Library was established in 1995 as an integral part of the college. Since then, it has become a key source of information services for students and faculty.<br>
                The library offers book lending, photocopying, and access to advanced academic material search systems, along with assistance from library staff.
            </p>

            <!-- Library activity times -->
            <h2 style="color:red">Activity Hours</h2>
            <p>
                Sunday - Thursday: 08:30 - 19:45<br>
                Fridays: 08:30 - 11:45<br>
                <br>
                Special hours and changes for holidays will be posted on the library's website.
            </p>

            <!-- Contact details -->
            <h2 style="color:red">Contact Us</h2>
            <p>
                Netanya Academic College - Library<br>
                1 University Street, Kiryat Yitzhak Rabin, Netanya 42365<br>
                Phone: 09-8607733<br>
                Fax: 09-8607780<br>
                Email: yaatz@netanya.ac.il
            </p>

            <!-- Library collection overview -->
            <h2 style="color:red">Library Collection</h2>
            <p>
                The library collection is tailored to the college's fields of study and includes around 40,000 printed books and over 1,000 magazines in Hebrew and English. There is also access to online databases with bibliographic data and full-text journals, e-books, and more.<br>
                The library is equipped with 20 computers for database access and offers a wireless network for mobile users.
            </p>

            <!-- Movie room details -->
            <h2 style="color:red">Movie Room</h2>
            <p>
                The library provides a movie room where students can watch films required for various courses. This room is equipped with viewing devices (DVD and video players) and screens.
            </p>

            <!-- Contact details repeated for easy access -->
            <h2 style="color:red">Contact Us</h2>
            <p>
                Netanya Academic College - Library<br>
                1 University Street, Kiryat Yitzhak Rabin, Netanya 42365<br>
                Phone: 09-8607733<br>
                Fax: 09-8607780<br>
                Email: yaatz@netanya.ac.il
            </p>
        </div>
    </body>
</html>
