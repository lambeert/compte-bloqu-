package test;

import java.io.IOException;  
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
@WebServlet("/SaveServlet")  
public class SaveServlet extends HttpServlet {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  protected Connection conn = null;
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			try {
			// load JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			System.out.println("Connection successful..");
			}
			catch (SQLException se) { System.out.println(se); }
			catch(ClassNotFoundException e) {e.printStackTrace();}
			}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)   
         throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          String name = request.getParameter("name");
       // String name=request.getParameter("name");  
int status = 0;
try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
	Statement stmt=con.createStatement();
	System.out.println("inserting value");
	String sql="select eid,ename,esal,eadd from emp";
	
	ResultSet rs = stmt.executeQuery(sql);
	out.print("<table border='1' width='100%'");
	while(rs.next()){
		out.print("<tr><td>"+rs.getString(1)+"</td>"+"<td>"+rs.getString(2)+"</td>"+"<td>"+rs.getString(3)+"</td>"+"<td>"+rs.getString(4)+"</td></tr>");
	}
	out.print("</table>");
}catch (Exception e) {
	System.out.println(e);
} 
        out.close();  
    } 
	
	
}  
