package co.com.orders.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.com.orders.dao.GenericDAOImpl;
import co.com.orders.util.EmailSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/user")
public class UserService {
	
	// This method is called if TEXT_PLAIN is request
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getUser(@QueryParam("data") String data) {
//		System.out.println("Data: "+data);
//		String[] dataArray = data.split(",");
//		
//		String user = dataArray[0];
//		String password = dataArray[1];
//		
//		Connection connection = GenericDAOImpl.getConnection();
//		
//		if (connection != null) {
//			System.out.println("You made it, take control your database now!");
//			
//			PreparedStatement st = null;
//			ResultSet rs = null;
//			
//			try {
//				st = connection.prepareStatement("SELECT \"usuarioId\", \"sedeRecepId\" FROM public.\"USUARIO_RECEPTOR\" WHERE \"cedula\" = ? AND \"contrasena\" = ?");
//				st.setString(1, user);
//				st.setString(2, password);
//				rs = st.executeQuery();
//				
//				if (rs.next()) {
//					int usuarioId = rs.getInt(1);
//					int sedeRecepId = rs.getInt(2);
//					
//					return usuarioId+","+sedeRecepId;
//					
//				} else {
//					return "failed";
//				}
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				try {
//					st.close();
//					rs.close();
//					connection.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			
//		} else {
//			System.out.println("Failed to make connection!");
//		}
//		
//		return "failed";
//	}
	
	
	@GET
	public Response getUser(@QueryParam("data") String data) {
		String result = "";
		
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
					
					result =  usuarioId+","+sedeRecepId;
					
				} else {
					result =  "failed";
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
				
		Response.ResponseBuilder rb = Response.ok(result);
		Response response = rb.header("Access-Control-Allow-Origin", "*")
                 .build();
		return response;
		
	}
	
	@POST
    public Response saveUser(String userInfo) {
        String output = "POST:Jersey say : " + userInfo;
        
		System.out.println("Data: "+userInfo);
		String[] dataArray = userInfo.split(",");
        
		Connection connection = GenericDAOImpl.getConnection();

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			
			PreparedStatement st = null;
			
			try {
				
				st = connection.prepareStatement("INSERT INTO public.\"USUARIO_RECEPTOR\"(\n" + 
						"	\"usuarioId\", cedula, contrasena, celular, telefono, correo, \"sedeRecepId\")\n" + 
						"	VALUES (nextval('SEQ_USUARIO_RECEPTOR'), ?, ?, ?, ?, ?, ?);");
				st.setString(1, dataArray[0]);
				st.setString(2, dataArray[1]);
				st.setString(3, dataArray[2]);
				st.setString(4, dataArray[3]);
				st.setString(5, dataArray[4]);
				st.setString(6, dataArray[5]);
				st.executeUpdate();
				
				//connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				try {
					st.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		} else {
			System.out.println("Failed to make connection!");
		}
        
                
        return Response.status(200).entity(output).build();
    }
}
