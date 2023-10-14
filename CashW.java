package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CashW
 */
@WebServlet("/CashW")
public class CashW extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashW() {
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
		String acno = request.getParameter("acn");
		
		String amountc = request.getParameter("amount");
		
		int acn = Integer.parseInt(acno);
		int amount = Integer.parseInt(amountc);
		int status = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from account where acno = "+acn+" and name = '"+name+"'");
			if(!rs.next()){

				   out.print("<p>Account not exist!!</p>");  
		            request.getRequestDispatcher("CashD.html").include(request, response); 
				
		            acno  = request.getParameter("acn");
		            acn = Integer.parseInt(acno);
		          
			}

			int ba = rs.getInt(6);
			if(amount>ba){
				   out.print("<p  style=\"color:blue;font-size:46px;\"> Not Sufficient amount</p>");  
		            request.getRequestDispatcher("CashW.html").include(request, response); 
		            amountc = request.getParameter("amount");
		    		acn = Integer.parseInt(acno);
				}
				
				ba = ba-amount;
				status = st.executeUpdate("UPDATE account set balance = "+ba+" where acno = "+acn+" ");
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		if(status>0) {
			   out.print("<p  style=\"color:blue;font-size:46px;\">"+amountc+" Cash withdrwal Successfully</p>");  
	            request.getRequestDispatcher("CashD.html").include(request, response); 
		}
		else {
		      out.println("Sorry! unable to save record");  
		}
	}

}
