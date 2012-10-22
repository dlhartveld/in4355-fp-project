package nl.tudelft.ewi.se.in4355.server.jobs

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import scala.beans.BeanInfo
import nl.tudelft.ewi.se.in4355.server.Counter

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
    return Response.ok.entity(jobSource.next).build;
  }

  @POST
  @Path("output")
  @Consumes(Array("text/plain"))
  def processJobResults(result: String): Response = {
    counter.counter = counter.counter + 1;
    return Response.ok.build;
  }

}
