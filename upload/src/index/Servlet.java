package index;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Uploading")
public class Servlet extends HttpServlet {       
   private String dbURL="jdbc:mariadb://localhost:3306/kumar";
   private String dbUser="root";
   private String dbPass="root";
   protected void doPost(HttpServletRequest request,
           HttpServletResponse response) throws ServletException, IOException {
	   String firstName=request.getParameter("firstName");
	   String lastName=request.getParameter("lastName");
	   InputStream inputStream=null;
	   Part filePart=request.getPart("photo");
	   if(filePart !=null) {
		   System.out.println(filePart.getName());
		   System.out.println(filePart.getSize());
		   System.out.println(filePart.getContentType());
		   inputStream=filePart.getInputStream();
	   }
	   Connection conn=null;
	   String message=null;
	   try {
		   DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		   conn=DriverManager.getConnection(dbURL,dbUser,dbPass);
		   String sql="INSERT INTO Student(firstNname,lastNname,photo)values(? ? ?)";
		   PreparedStatement statement =conn.prepareStatement(sql);
		   statement.setString(1,firstName);
		   statement.setString(1,lastName);
		   if(inputStream !=null)
		   {
			   statement.setBlob(3,inputStream);
		   }
		   int rw=statement.executeUpdate();
		   if(rw>0) {
			   message="file upload in database";
		   }
	   }
	   catch(SQLException ex) {
		   String mesaage = "ERROR:"+ex.getMessage();
		   ex.printStackTrace();
	   }finally {
		   if(conn != null) {
			   try {
				   conn.close();
			   }
			   catch(SQLException ex) {
				   ex.printStackTrace();
			   }
		   }
		   request.getAttribute("Message");
		   getServletContext().getRequestDispatcher("/Message.jsp").forward(request,response);
			   }
		   }
	   }
	   
   

   
	