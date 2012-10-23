package nl.tudelft.ewi.se.in4355.server.jobs

import java.io.BufferedReader
import java.io.InputStreamReader
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import com.google.gson.Gson
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

@Path("jobs")
class JobResources {

  val jobSource = new JobSource
  
  @POST 
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob(): Response = {
    if (jobSource.hasNext) {
    	val code = readFile("/wordcounter-1.js");
    	return Response.ok.entity(code).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }
  
  @POST
  @Path("input")
  @Produces(Array("application/json"))
  def getJobInput(): Response = {
    val data = jobSource.nextPackage;
    if (!data.isEmpty) {
    	val serializer = new Gson;
    	return Response.ok.entity(serializer.toJson(data)).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  @POST
  @Path("output")
  @Produces(Array("application/json"))
  @Consumes(Array("application/json"))
  def processJobResults(input: String): Response = {
    val deserializer = new Gson;
    val listType : Type = new TypeToken[WordCount]() {}.getType()
    val results : WordCount = (deserializer.fromJson(input, listType))
    jobSource.markDone(results.id);
    println(deserializer.toJson(results));
    return Response.ok.entity("{ \"hasMoreData\": " + jobSource.hasNext + " }").build;
  }
  
  // TODO: Can this be done more efficiently?
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
