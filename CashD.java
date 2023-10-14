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
 * Servlet implementation class CashD
 */
@WebServlet("/CashD")
public class CashD extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashD() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String acno = request.getParameter("acn");
		String amountc = request.getParameter("amount");
		String name = request.getParameter("name");
		int acn = Integer.parseInt(acno);
		int amount = Integer.parseInt(amountc);
		int status = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from account where acno = "+acn+"");
			if(!rs.next()){

				   out.print("<p>Account not exist!!</p>");  
		            request.getRequestDispatcher("CashD.html").include(request, response); 
				
		            acno  = request.getParameter("acn");
		            acn = Integer.parseInt(acno);
		          
			}
			int ba = rs.getInt(6);
			ba = ba+amount;
			 status =  st.executeUpdate("UPDATE account set balance = "+ba+" where acno = "+acn+" and name = '"+name+"' ");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		if(status>0) {
			   out.print("<p  style=\"color:blue;font-size:46px;\">"+amountc+" Cash deposited Successfully</p>");  
	            request.getRequestDispatcher("CashD.html").include(request, response); 
		}
		else {
		      out.println("Sorry! unable to save record");  
		}
	}

}
