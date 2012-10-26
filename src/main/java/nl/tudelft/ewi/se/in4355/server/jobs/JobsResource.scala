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
class JobsResource {

  val taskTracker = TaskTracker;
  val jobTracker = JobTracker

  @POST
  @Path("code")
  @Consumes(Array("text/javascript"))
  @Produces(Array("application/json"))
  def createJob(code: String) = {
    val id = jobTracker.addNewJob(code)
    Response.ok("{\"job\"" + id + "}").build
  }

  @POST
  @Path("code")
  @Produces(Array("text/javascript"))
  def getNextJob(): Response = {
    if (taskTracker.hasTasks) {
      return Response.ok.entity(taskTracker.getCurrentTask.code).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  @POST
  @Path("input")
  @Produces(Array("application/json"))
  def getJobInput(): Response = {
    val data = taskTracker.getCurrentTask.nextBatch;
    if (data != null) {
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
    val listType: Type = new TypeToken[Output[_]]() {}.getType()
    val results: Output[_] = (deserializer.fromJson(input, listType))
    taskTracker.getCurrentTask.markDone(results.id, input);
    println(deserializer.toJson(results));
    return Response.ok.entity("{ \"hasMoreData\": " + taskTracker.getCurrentTask.hasNext + " }").build;
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
    } while (line != null);
    return result.toString;
  }

}
