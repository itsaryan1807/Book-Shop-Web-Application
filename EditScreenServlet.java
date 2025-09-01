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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "aryan1807";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM booksdata WHERE id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("bookname");
                    String edition = rs.getString("bookedition");
                    float price = rs.getFloat("bookprice");

                    pw.println("<html><head>");
                    pw.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
                    pw.println("<title>Edit Book</title></head><body class='bg-light'>");
                    pw.println("<div class='container mt-5'><div class='card shadow p-4 rounded'>");
                    pw.println("<h2 class='text-center text-warning mb-4'>✏️ Edit Book</h2>");
                    pw.println("<form action='editScreen' method='post'>");
                    pw.println("<input type='hidden' name='id' value='" + id + "'/>");
                    pw.println("<div class='mb-3'>Book Name: <input type='text' name='bookname' class='form-control' value='" + name + "' required/></div>");
                    pw.println("<div class='mb-3'>Book Edition: <input type='text' name='bookedition' class='form-control' value='" + edition + "' required/></div>");
                    pw.println("<div class='mb-3'>Book Price: <input type='number' step='0.01' name='bookprice' class='form-control' value='" + price + "' required/></div>");
                    pw.println("<div class='text-center'><input type='submit' class='btn btn-warning px-4' value='Update Book'/></div>");
                    pw.println("</form>");
                    pw.println("<br><a href='bookList' class='btn btn-info'>Back to Book List</a>");
                    pw.println("</div></div></body></html>");
                } else {
                    pw.println("<h3>Book not found!</h3>");
                    pw.println("<a href='bookList'>Back to Book List</a>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("bookname");
        String edition = req.getParameter("bookedition");
        float price = Float.parseFloat(req.getParameter("bookprice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE booksdata SET bookname=?, bookedition=?, bookprice=? WHERE id=?");
                ps.setString(1, name);
                ps.setString(2, edition);
                ps.setFloat(3, price);
                ps.setInt(4, id);

                int updated = ps.executeUpdate();
                if (updated > 0) {
                    resp.sendRedirect("bookList");
                } else {
                    resp.getWriter().println("<h3>Failed to update book.</h3>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
