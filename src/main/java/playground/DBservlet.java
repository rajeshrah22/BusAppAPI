package playground;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBservlet
 */
@WebServlet("/DBservlet")
public class DBservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String DB_URL = "jdbc:mysql://localhost:3306/testdb";
	String USER = "root";
	String PASS = "Rahulrah#1234";
	String QUERY = "SELECT UserID, Password FROM UserLogin";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =	response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(QUERY);
			 while (rs.next()) {
		            // Retrieve by column name
		           
		           out.print(", First: " + rs.getString("UserID"));
		           out.println(", Last: " + rs.getString("Password"));
		         }
		}
	catch(SQLException e) 	{
		e.printStackTrace();
	}	

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
