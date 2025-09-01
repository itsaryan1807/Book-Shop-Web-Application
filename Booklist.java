package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class Booklist extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String query = "SELECT * FROM booksdata;";
    
    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASS = "aryan1807"; 
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
                 PreparedStatement ps = con.prepareStatement(query)) {
                
                ResultSet set = ps.executeQuery();
                

                pw.println("<html><head>");
                pw.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
                pw.println("<title>Book List</title></head><body class='bg-light'>");
                pw.println("<div class='container mt-5'>");
                pw.println("<h2 class='text-center mb-4 text-primary'>📚 Available Books</h2>");
                pw.println("<table class='table table-striped table-bordered text-center'>");
                pw.println("<thead class='table-dark'><tr>");
                pw.println("<th>ID</th><th>Name</th><th>Edition</th><th>Price</th><th>Actions</th>");
                pw.println("</tr></thead><tbody>");

                while (set.next()) {
                    int id = set.getInt("id");
                    String name = set.getString("bookname");
                    String edition = set.getString("bookedition");
                    float price = set.getFloat("bookprice");

                    pw.println("<tr>");
                    pw.println("<td>" + id + "</td>");
                    pw.println("<td>" + name + "</td>");
                    pw.println("<td>" + edition + "</td>");
                    pw.println("<td>" + price + "</td>");
                    pw.println("<td>");
                    pw.println("<a href='editScreen?id=" + id + "' class='btn btn-warning btn-sm me-2'>Edit</a>");
                    pw.println("<a href='deleteurl?id=" + id + "' class='btn btn-danger btn-sm'>Delete</a>");
                    pw.println("</td>");
                    pw.println("</tr>");
                }

                pw.println("</tbody></table>");
                pw.println("<div class='mt-3 text-center'>");
                pw.println("<a href='home.html' class='btn btn-info me-2'>🏠 Home</a>");
                pw.println("<a href='home.html' class='btn btn-success'>➕ Add New Book</a>");
                pw.println("</div></div></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
