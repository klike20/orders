package co.com.orders.service;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.com.orders.dao.GenericDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/user")
public class UserService {
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser(@QueryParam("data") String data) {
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
				st = connection.prepareStatement("SELECT \"usuarioId\", \"sedeRecepId\" FROM public.\"USUARIO_RECEPTOR\" WHERE \"cedula\" = ? AND \"contrasena\" = ?");
				st.setString(1, user);
				st.setString(2, password);
				rs = st.executeQuery();
				
				if (rs.next()) {
					int usuarioId = rs.getInt(1);
					int sedeRecepId = rs.getInt(2);
					
					return usuarioId+","+sedeRecepId;
					
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
		
		return "{'salarta','otra'}";
	}
}
