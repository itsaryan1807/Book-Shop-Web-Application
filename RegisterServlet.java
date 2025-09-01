package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String query = "INSERT INTO booksdata(BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES(?, ?, ?)";
    
    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASS = "aryan1807"; 
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        
        // Get the book Info
        String bookname = req.getParameter("bookname");
        String bookedition = req.getParameter("bookedition");

        float bookprice = 0.0f;
        try {
            bookprice = Float.parseFloat(req.getParameter("bookprice"));  // ✅ Direct parse
        } catch (Exception e) {
            pw.println("<h3 style='color:red'>Invalid or missing book price. Please enter a number.</h3>");
            pw.println("<br><a href='index.html'>Home</a>");
            return;
        }

        // Validation for other fields
        if (bookname == null || bookedition == null || bookname.isEmpty() || bookedition.isEmpty()) {
            pw.println("<h3 style='color:red'>Missing book details. Please enter all fields.</h3>");
            pw.println("<br><a href='index.html'>Home</a>");
            return;
        }

        // Load JDBC driver and save into DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
                 PreparedStatement ps = con.prepareStatement(query)) {
                
                ps.setString(1, bookname);
                ps.setString(2, bookedition);
                ps.setFloat(3, bookprice);
                
                int count = ps.executeUpdate();

                pw.println("<html><head>");
                pw.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
                pw.println("<title>Book Registration Result</title></head><body class='bg-light'>");
                pw.println("<div class='container mt-5'><div class='card shadow p-4 rounded text-center'>");

                if (count > 0) {
                    pw.println("<h2 class='text-success'>✅ Book registered successfully!</h2>");
                } else {
                    pw.println("<h2 class='text-danger'>❌ Failed to register book.</h2>");
                }

                pw.println("<div class='mt-3'>");
                pw.println("<a href='index.html' class='btn btn-info me-2'>🏠 Home</a>");
                pw.println("<a href='bookList' class='btn btn-primary'>📖 Book List</a>");
                pw.println("</div></div></div></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }

        pw.println("<br><a href='home.html' class=\\\"btn btn-info px-4\\\">Home</a>");
        pw.println("<br><a href=\"bookList\" class=\"btn btn-info px-4\">📖 Book List</a>");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
