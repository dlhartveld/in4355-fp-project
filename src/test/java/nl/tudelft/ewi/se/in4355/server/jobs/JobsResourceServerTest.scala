package nl.tudelft.ewi.se.in4355.server.jobs

import org.apache.http.util.EntityUtils
import org.junit.Test
import nl.tudelft.ewi.se.in4355.server.GridServerTestBase
import org.apache.http.HttpEntity
import grizzled.slf4j.Logger
import scala.util.parsing.json.JSON
import org.apache.http.HttpResponse
import org.junit.Ignore

class JobsResourceServerTest extends GridServerTestBase {

  val LOG = Logger(classOf[JobsResourceServerTest])

  @Test
  @Ignore
  def doSmokeTestForCodeAndDataRetrieval() {
    val job = getNextTaskId
    LOG.info("Next job: " + job)

    val code = retrieveCodeForTask(job)
    LOG.info("Code: " + code)

    val tasks = Set[Int]()
    for (i <- 0 until 31) {
      LOG.info("i: " + i)
      val data = retrieveDataForTask(job)
      val task = unwrap(data.get("id"))
      task match {
        case -1 => LOG.info("Received task -1. Done."); return
        case id =>
          LOG.info("Sending result for task: " + task)
          deliverEmptyResult(task)
      }
    }
  }

  private def unwrap(id: Option[Any]): Int = id.get match {
    case id: Double => id.toInt
    case _ => -1
  }

  private def getNextTaskId() = {
    contentsOf(postOrFail("/resources/jobs/")).trim.toInt
  }

  private def retrieveCodeForTask(nextTask: Int) = {
    contentsOf(postOrFail("/resources/jobs/" + nextTask + "/code"))
  }

  private def retrieveDataForTask(nextTask: Int) = {
    parse(contentsOf(postOrFail("/resources/jobs/" + nextTask + "/input")))
  }

  private def deliverEmptyResult(task: Int) = {
    parse(contentsOf(postOrFail("/resources/jobs/" + task + "/output", "application/json", "{\"id\": " + task + ", \"value\": \"\"}")))
  }

  private def parse(json: String) = {
    JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
  }

  private def contentsOf(response: HttpResponse) = response.getEntity match {
    case null => ""
    case _ => EntityUtils.toString(response.getEntity())
  }

}
