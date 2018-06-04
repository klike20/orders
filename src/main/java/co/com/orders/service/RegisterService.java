package co.com.orders.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class RegisterService {
//	// This method is called if TEXT_PLAIN is request
//	  @GET
//	  @Produces(MediaType.TEXT_PLAIN)
//	  public String getCompanies() {
//	    return "{'salarta','otra'}";
//	  }
	  
	@POST
//    @Path("/post")
    //@Consumes(MediaType.TEXT_XML)
    public Response postStrMsg( String msg) {
        String output = "POST:Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }
}
