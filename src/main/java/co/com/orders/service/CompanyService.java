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

@Path("/companies")
public class CompanyService {
	// This method is called if TEXT_PLAIN is request
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCompany(@QueryParam("data") String data) {
		  	System.out.println("Data: "+data);
			String[] dataArray = data.split(",");
			
			String user = dataArray[0];
			String password = dataArray[1];
			
			Connection connection = GenericDAOImpl.getConnection();
			
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
				
				PreparedStatement st = null;
				ResultSet rs = null;
				
				try {
					st = connection.prepareStatement("SELECT \"empresaId\" FROM public.\"EMP_RECEPTORA\" WHERE \"nit\" = ? AND \"contrasena\" = ?");
					st.setString(1, user);
					st.setString(2, password);
					rs = st.executeQuery();
					
					if (rs.next()) {
						int companyId = rs.getInt(1);
						
						return ""+companyId;
						
					} else {
						return "failed";
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
			
			return "failed";
	  }
	  
	  @GET
	  @Path("/offices")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getOffices(@QueryParam("company") String companyId) {
		  
		  	String offices = "";
		  
		  	System.out.println("Company: "+companyId);
			
			Connection connection = GenericDAOImpl.getConnection();
			
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
				
				PreparedStatement st = null;
				ResultSet rs = null;
				
				try {
					st = connection.prepareStatement("SELECT \"sedeId\", \"nombre\" FROM public.\"SEDE_EMP_RECEP\" WHERE \"empRecepId\" = ?");
					st.setString(1, companyId);
					rs = st.executeQuery();
					
					while (rs.next()) {
						int officeId = rs.getInt(1);
						String name = rs.getString(2);
						offices =  officeId+","+name+";";
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
			
			return offices;
	  }
	  
	  	
	  
}
