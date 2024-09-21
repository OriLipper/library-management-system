<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Inline CSS for heading styles -->
        <style>
            h1 {
                color: red;
            }
        </style>
        
        <!-- Link to external stylesheet for additional styling -->
        <link href="StyleSheet.css" type="text/css" rel="stylesheet">
        
        <!-- Meta tag for character encoding -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Page Title -->
        <title>Library - Register</title>
    </head>
    <body>
        <!-- Navigation Menu -->
        <ul>
            <li><a href="index.jsp">Login</a></li>
            <li><a href="RegisterStudent.jsp">Register</a></li> 
        </ul>
        
        <!-- Main content section for registration -->
        <div>
            <!-- Page heading -->
            <h1>Register</h1>
            
            <!-- Check for any messages passed from the RegisterServlet -->
            <%
                String msg = (String) request.getAttribute("msg");
                String color = (String) request.getAttribute("color");

                if (msg != null && color != null) {
            %>
            <!-- Display the message in the specified color -->
            <h3 style="color: <%= color %>"><%= msg %></h3> 
            <%
                }
            %>

            <!-- Registration form that sends data to RegisterServlet -->
            <form action="RegisterServlet">
                <h2>
                    First Name: <input type="text" name="firstName" required> <br>
                    Last Name: <input type="text" name="lastName" required><br>
                    ID: <input type="text" name="id" required><br>
                    Email: <input type="email" name="email" required><br>
                    Password: <input type="password" name="password" required><br>
                    
                    <!-- Submit and Reset buttons -->
                    <input type="submit" value="Register"> 
                    <input type="reset" value="Reset"> 
                </h2> 
            </form>
        </div>
    </body>
</html>
