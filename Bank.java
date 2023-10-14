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
 * Servlet implementation class Bank
 */
@WebServlet("/Bank")
public class Bank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bank() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String acno = request.getParameter("acn");
		String ph = request.getParameter("number");
		int status = 0;
		int acn = Integer.parseInt(acno);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from account where acno = "+acn+"");
			if(rs.next()){
		          out.print("<p  style=\\\"color:blue;font-size:46px;\\\">Account exist</p></p>");  
		            request.getRequestDispatcher("Nacn.html").include(request, response); 
		            acno  = request.getParameter("Acn");
		            acn = Integer.parseInt(acno);
			}
			PreparedStatement pst = con.prepareStatement("insert into account values(?,?,?,?,?,?)");
			pst.setString(1,name);
			pst.setInt(2,acn);
			pst.setString(3,"StarBank576");
			pst.setString(4,ph);
			pst.setString(5,email);
			pst.setInt(6,1000);
			status = pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		if(status>0) {
			   out.print("<p  style=\"color:blue;font-size:46px;\">Account created Successully</p>");  
	            request.getRequestDispatcher("Nacn.html").include(request, response); 
		}
		else {
		      out.println("Sorry! unable to save record");  
		}
		}

}
