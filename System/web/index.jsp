<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Page Title -->
        <title>Login</title>
        
        <!-- Meta tags for character encoding and responsive design -->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <!-- Link to external stylesheet for page styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <!-- Navigation menu -->
        <ul>
            <li><a href="index.jsp">Login</a></li>
            <li><a href="RegisterStudent.jsp">Register</a></li> 
        </ul>

        <!-- Main content section for login -->
        <div>
            <h1>Login</h1>

            <!-- Check for messages (like errors or success) passed from the servlet -->
            <%
                String msg = (String) request.getAttribute("msg");
                String color = (String) request.getAttribute("color");

                if (msg != null && color != null) {
            %>
            <!-- Display the message in the provided color -->
            <h3 style="color: <%= color %>"><%= msg %></h3> 
            <%
                }
            %>

            <!-- Login form that submits data to the LoginServlet -->
            <form action="LoginServlet">
                <h2>
                    Email: <input type="email" name="email" required> <br>
                    Password: <input type="password" name="password" required><br>
                    <!-- Submit and reset buttons -->
                    <input type="submit" value="Login"> 
                    <input type="reset" value="Reset">
                </h2> 
            </form>
        </div>
    </body>
</html>
