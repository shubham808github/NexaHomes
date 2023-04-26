package com.nexahomes.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nexahomes?useSSL=false", "root", "Shubham@8080");

		    String uemail = request.getParameter("username");
		    String upwd = request.getParameter("password");

		    PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE uemail = ? AND upwd = ?");
		    pst.setString(1, uemail);
		    pst.setString(2, upwd);

		    ResultSet rs = pst.executeQuery();

		    if (rs.next()) {
		        HttpSession session = request.getSession();
		        session.setAttribute("name", rs.getString("uname"));
		        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		        dispatcher.forward(request, response);
		    } else {
		        request.setAttribute("status", "failed");
		        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		        dispatcher.forward(request, response);
		    }

		    con.close();
		} catch (ClassNotFoundException | SQLException | ServletException | IOException e) {
		    e.printStackTrace();
		    // Handle exceptions
		}
	}
}
