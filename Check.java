package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import java.io.*;
import java.text.*;
import oracle.jdbc.*;
import java.util.regex.*;
import javax.sql.rowset.*;

/**
 * Servlet implementation class Check
 */
@WebServlet("/Check")
public class Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String acno = request.getParameter("acn");
		String name = request.getParameter("name");
		int acn = Integer.parseInt(acno);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from account where acno = "+acn+" and name = '"+name+"'");
			out.print("<table border='1' width='100%'");
			if(rs.next()){
				out.print("<p>Holder_Name\tAccount_Number\tIFSC_code\tPhone_number\tEmail\tBalance<p>");
				out.print("<tr><td>"+rs.getString(1)+"</td>"+"<td>"+rs.getString(2)+"</td>"+"<td>"+rs.getString(3)+"</td>"+"<td>"+rs.getString(4)+"</td>"+"<td>"+rs.getString(5)+"</td>"+"<td>"+rs.getString(6)+"</td></tr>");
			}
			else{
				out.print("<p  style=\"color:blue;font-size:46px;\">Account not exist</p>");  
	            request.getRequestDispatcher("Check.html").include(request, response); 
			}
			out.print("</table>");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
