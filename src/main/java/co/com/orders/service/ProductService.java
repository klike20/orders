package co.com.orders.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.com.orders.dao.GenericDAOImpl;

@Path("/products")
public class ProductService {
	
	// This method is called if TEXT_PLAIN is request
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCompany(@QueryParam("empSurId") String empSurId) {
		  	System.out.println("EmpSurId: "+empSurId);
						
		  	String products = "";
		  	
			Connection connection = GenericDAOImpl.getConnection();
			
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
				
				PreparedStatement st = null;
				ResultSet rs = null;
				
				try {
					st = connection.prepareStatement("SELECT p.\"codigo\", p.\"nombre\" FROM public.\"PRODUCTO\" AS p, public.\"PRODUCTOS_X_EMP\" AS pm WHERE pm.\"empSurId\" = ? AND p.\"productoId\" = pm.\"prodId\"");
					st.setString(1, empSurId);
					rs = st.executeQuery();
					
					while (rs.next()) {
						String code = rs.getString(1);
						String name = rs.getString(2);
						products =  products + code+" "+name+";";
					} 
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						st.close();
						rs.close();
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				
			} else {
				System.out.println("Failed to make connection!");
			}
			
			return products;
	  }
	  
}
