package nl.tudelft.ewi.se.in4355.server

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.ArrayList
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import java.io.InputStreamReader

@Path("jobs")
class JobResources {
  
  @POST 
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob() : Response = {
    val code = readFile("/wordcounter-1.js");
    return Response.ok.entity(code).build;
  }
  
  @POST
  @Path("input")
  @Produces(Array("application/json"))
  def getJobInput() : Response = {
    val lines = new ArrayList[String]();
    lines.add("Hello world");
    lines.add("Lorum ipsum dolor");
    lines.add("Hello dolor");
    
    return Response.ok.entity(lines).build;
  }
  
  @POST
  @Path("output")
  @Consumes(Array("application/json"))
  def processJobResults(results: ArrayList[Count]): Response = {
    println(results.size);
    return Response.ok.build;
  }
  
  def readFile(fileName: String): String = {
	var line = "";
    val result = new StringBuilder();
    val file = getClass.getResourceAsStream(fileName);
    val reader = new BufferedReader(new InputStreamReader(file));
    do {
      line = reader.readLine;
      if (line != null) {
    	  result.append(line + "\n");
      }
    }
    while (line != null);
    return result.toString;
  }
  
}
