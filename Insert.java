package test;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Insert
 */
@WebServlet("/Insert")
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Insert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = request.getParameter("name");
		String esal = request.getParameter("esal");
int sal = Integer.parseInt(esal);
		String addres = request.getParameter("address");
int status = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			PreparedStatement pst = con.prepareStatement("insert into emp(ename,esal,eadd) values(?,?,?)");
			pst.setString(1, name);
			pst.setInt(2, sal);
			pst.setString(3, addres);
			status = pst.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);
		}
	     if(status>0){  
	            pw.print("<p>Record saved successfully!</p>");  
	            request.getRequestDispatcher("index.html").include(request, response);  
	        }else{  
	            pw.println("Sorry! unable to save record");  
	        } 
	}

}
