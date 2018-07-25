package co.com.orders.service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import co.com.orders.dao.GenericDAOImpl;
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
    public Response postStrMsg(String order) {
        String output = "POST:Jersey say : " + order;
        
        System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
        
        String[] orderArray = order.split(":");
        
        String[] metaData = orderArray[0].split(",");
        
        String userId = metaData[0];
        
        String officeId = metaData[1];
        
        String deliverCompanyId = metaData[2];

//		try {
//
//			Class.forName("org.postgresql.Driver");
//
//		} catch (ClassNotFoundException e) {
//
//			System.out.println("Where is your PostgreSQL JDBC Driver? "
//					+ "Include in your library path!");
//			e.printStackTrace();
//		}
//
//		System.out.println("PostgreSQL JDBC Driver Registered!");
//
//		Connection connection = null;
//		
//		try {
//
//			connection = DriverManager.getConnection(
//					"jdbc:postgresql://askme.cretetpffptt.us-east-2.rds.amazonaws.com:5432/askme", "askme",
//					"askme123456");
//
//		} catch (SQLException e) {
//
//			System.out.println("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
		
		Connection connection = GenericDAOImpl.getConnection();

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			
			PreparedStatement st = null;
			ResultSet rs = null;
			
			try {
				st = connection.prepareStatement("INSERT INTO public.\"ORDER\"(\n" + 
						"	\"orderId\", fecha, \"usuarioId\", \"sedeId\", \"empSurId\")\n" + 
						"	VALUES (nextval('SEQ_ORDER'), current_date, ?, ?, ?);");
				st.setString(1, userId);
				st.setString(2, officeId);
				st.setString(3, deliverCompanyId);
				st.executeUpdate();
				
//				st = connection.prepareStatement("SELECT MAX(\"orderId\") FROM public.\"ORDER\"");
//				rs = st.executeQuery();
//				
//				int lastOrderId = 0;
//				
//				if (rs.next()) {
//					lastOrderId = rs.getInt(1);
//				}
				
				String[] products = orderArray[1].split(";");
				
				int i = 0;
				
				while (i < products.length) {
					
					System.out.println("products[i]: " + products[i]);
					
					String[] product = products[i].split(",");
					
					st = connection.prepareStatement("INSERT INTO public.\"DETALLE\"(\n" + 
							"	\"detalleId\", \"orderId\", \"productoId\", unidad, cantidad, observaciones)\n" + 
							"	VALUES (nextval('SEQ_DETALLE'), (SELECT MAX(\"orderId\") FROM public.\"ORDER\"), ?, ?, ?, ?);");
					st.setString(1, product[0]);
					st.setString(2, product[2]);
					st.setInt(3, Integer.valueOf(product[3]));
					st.setString(4, product[4]);
					st.executeUpdate();
					
					i++;
				}
				
				//connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated catch block
				e.printStackTrace();
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
        
        
        EmailSender.send("klike21@gmail.com", order);
        
        return Response.status(200).entity(output).build();
    }
}
