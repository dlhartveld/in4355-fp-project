package nl.tudelft.ewi.se.in4355.server

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("jobs")
class JobResources {
  
  @POST 
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob() : Response = {
    var counter = Counter
    counter.counter = counter.counter + 1;
    println(counter.counter);
    return Response.ok.entity("setTimeout(function() { alert('count = " + counter.counter + "'); }, 1000);").build;
  }
  
  @GET
  @Path("data")
  @Produces(Array("application/json"))
  def getJobInput() : Response = {
    return Response.ok.entity("").build;
  }
  
  @POST
  @Path("data")
  @Consumes(Array("application/json"))
  def processJobResults(result: String) : Response = {
    // Implement this.
    return Response.ok.build;
  }
    
}