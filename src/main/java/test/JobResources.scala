package test

import javax.ws.rs._
import javax.ws.rs.core.Response

@Path("jobs")
class JobResources {
  
  @GET 
  @Produces(Array("application/json"))
  def getNextJob() : Response = {
    // Implement this.
    return Response.ok.entity("[{\"Naam\": \"JSON\"}]").build;
  }
  
  @POST
  @Consumes(Array("application/json"))
  def processJobResults(result: String) : Response = {
    // Implement this.
    return Response.ok.build;
  }
    
}