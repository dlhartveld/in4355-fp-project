package nl.tudelft.ewi.se.in4355.server.jobs

import org.apache.http.util.EntityUtils
import org.junit.Test
import nl.tudelft.ewi.se.in4355.server.GridServerTestBase
import org.apache.http.HttpEntity
import grizzled.slf4j.Logger
import scala.util.parsing.json.JSON
import org.apache.http.HttpResponse

class JobsResourceServerTest extends GridServerTestBase {

  val LOG = Logger(classOf[JobsResourceServerTest])

  @Test
  def doSmokeTestForCodeAndDataRetrieval() {
    val nextTask = getNextTaskId
    LOG.info("Next task: " + nextTask)

    val code = retrieveCodeForTask(nextTask)
    LOG.info("Code: " + code)

    for (i <- 0 until 200) {
      val data = retrieveDataForTask(nextTask)
      LOG.info("Data[" + i + ": " + data)
    }
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

  private def parse(json: String) = {
    JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
  }

  private def contentsOf(response: HttpResponse) = response.getEntity match {
    case null => ""
    case _ => EntityUtils.toString(response.getEntity())
  }

}
