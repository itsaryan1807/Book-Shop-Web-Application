package com.idiot.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteScreenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "aryan1807";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM booksdata WHERE id=?");
                ps.setInt(1, id);

                int deleted = ps.executeUpdate();
                if (deleted > 0) {
                    resp.sendRedirect("bookList");
                } else {
                    resp.getWriter().println("<h3>Failed to delete book.</h3>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}

