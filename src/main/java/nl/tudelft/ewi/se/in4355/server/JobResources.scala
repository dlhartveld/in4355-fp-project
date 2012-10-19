package nl.tudelft.ewi.se.in4355.server

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("resources/jobs")
class JobResources {
  
  var counter = Counter
  
  @POST 
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob() : Response = {
    val builder = new StringBuilder();
    builder.append("	$.ajax({ ")
    builder.append("		url: 'http://localhost:10000/resources/resources/jobs/input', ")
    builder.append("		type: 'post', dataType: 'json', ")
    builder.append("		success: function(data) { ")
    builder.append("			alert(data.val); ")
    builder.append("			startNextJob(); ")
    builder.append("		}, ")
    builder.append("		error: function(jqXHR, textStatus, errorThrown) { ")
    builder.append("			alert(jqXHR.responseText); ")
    builder.append("		} ")
    builder.append("	}); ")
    
    return Response.ok.entity(builder.toString).build;
  }
  
  @POST
  @Path("input")
  @Produces(Array("application/json"))
  def getJobInput() : Response = {
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
