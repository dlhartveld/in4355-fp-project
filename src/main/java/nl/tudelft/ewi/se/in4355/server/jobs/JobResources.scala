package nl.tudelft.ewi.se.in4355.server.jobs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

import org.codehaus.jackson.annotate.JsonIgnoreProperties

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

@Path("jobs")
class JobResources {

  val tracker = TaskTracker;

  @POST
  @Path("")
  @Produces(Array("text/plain"))
  def getNextTaskId(): Response = {
    if (tracker.hasTasks) {
      return Response.ok.entity(tracker.getCurrentTaskId.toString).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  @POST
  @Path("{id}/code")
  @Produces(Array("text/javascript"))
  def getNextJob(@PathParam("id") taskId: Int): Response = {
    if (tracker.hasTasks) {
      return Response.ok.entity(tracker.getTask(taskId).code).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  @POST
  @Path("{id}/input")
  @Produces(Array("application/json"))
  def getJobInput(@PathParam("id") taskId: Int): Response = {
    val data = tracker.getTask(taskId).nextBatch;
    if (data != null) {
      val serializer = new Gson;
      return Response.ok.entity(serializer.toJson(data)).build;
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  @POST
  @Path("{id}/output")
  @Produces(Array("application/json"))
  @Consumes(Array("application/json"))
  def processJobResults(@PathParam("id") taskId: Int, input: String): Response = {
    val deserializer = new Gson;
    val listType: Type = new TypeToken[Output[_]]() {}.getType()
    val results: Output[_] = (deserializer.fromJson(input, listType))
    tracker.getTask(taskId).markDone(results.id, input);

    return Response.ok.entity("{ \"hasMoreData\": " + tracker.hasMoreData(taskId) + " }").build;
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
