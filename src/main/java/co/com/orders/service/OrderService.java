package co.com.orders.service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.com.orders.util.EmailSender;

@Path("/saveOrder")
public class OrderService {
//	// This method is called if TEXT_PLAIN is request
//	  @GET
//	  @Produces(MediaType.TEXT_PLAIN)
//	  public String getCompanies() {
//	    return "{'salarta','otra'}";
//	  }
	  
	@POST
//    @Path("/post")
    //@Consumes(MediaType.TEXT_XML)
    public Response postStrMsg(String msg) {
        String output = "POST:Jersey say : " + msg;
        
        System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://askme.cretetpffptt.us-east-2.rds.amazonaws.com:5432/askme", "askme",
					"askme123456");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			
			PreparedStatement st = null;
			
			try {
				st = connection.prepareStatement("INSERT INTO public.\"ORDER\"(\n" + 
						"	\"order\")\n" + 
						"	VALUES (?)");
				st.setString(1, msg);
				st.executeUpdate();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		} else {
			System.out.println("Failed to make connection!");
		}
        
        
        EmailSender.send("klike21@gmail.com", msg);
        
        return Response.status(200).entity(output).build();
    }
}
