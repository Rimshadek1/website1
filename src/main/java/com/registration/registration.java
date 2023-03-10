package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registration
 */
@WebServlet("/register")
public class registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		String repass = request.getParameter("re_pass");
		RequestDispatcher dispacher = null;
		Connection con = null;
		if(uname==null || uname.equals("")) {
			request.setAttribute("status","Invalidname");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}
		if(uemail==null || uemail.equals("")) {
			request.setAttribute("status","Invaliduemail");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}
		if(upwd==null || upwd.equals("")) {
			request.setAttribute("status","Invalidupwd");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}else if(!upwd.equals(repass)) {
			request.setAttribute("status","Missmatch");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}
		if(umobile==null || umobile.equals("")) {
			request.setAttribute("status","Invalidumobile");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}else if(umobile.length()>10) {
			request.setAttribute("status","Invalidumobilenumber");
			dispacher = request.getRequestDispatcher("registration.jsp");
			dispacher.forward(request, response);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dinepoint1?useSSL=false", "root", "root");
			PreparedStatement pst = con.prepareStatement("insert into point(uname,upwd,uemail,umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			int rowCount = pst.executeUpdate();
			dispacher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
			}
			dispacher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();}
			finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

}}