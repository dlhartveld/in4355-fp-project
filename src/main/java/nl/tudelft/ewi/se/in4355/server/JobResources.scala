package nl.tudelft.ewi.se.in4355.server

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("jobs")
class JobResources {

  var counter = Counter

  val jobSource = new JobSource

  @POST
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob(): Response = {
    return Response.ok.entity("fetch(function(data) { push(data); });").build;
  }

  @POST
  @Path("input")
  @Produces(Array("application/json"))
  def getJobInput(): Response = {
    return Response.ok.entity(counter.counter).build;
  }

  @POST
  @Path("output")
  @Consumes(Array("text/plain"))
  def processJobResults(result: String): Response = {
    counter.counter = counter.counter + 1;
    return Response.ok.build;
  }

}
