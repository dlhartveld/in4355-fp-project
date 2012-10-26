package nl.tudelft.ewi.se.in4355.server.jobs

import org.apache.http.util.EntityUtils
import org.junit.Test
import nl.tudelft.ewi.se.in4355.server.GridServerTestBase
import org.apache.http.HttpEntity
import grizzled.slf4j.Logger
import scala.util.parsing.json.JSON

class JobsResourceServerTest extends GridServerTestBase {

  val LOG = Logger(classOf[JobsResourceServerTest])

  @Test def testThatPostingNewJobsWorks {
    val response = createNewJob("Code for my first job")

    println(response)

    val job = response.get("job").get.asInstanceOf[Double]

    assert(job == 0)
  }

  private def createNewJob(code: String) = {
    val response = post("/resources/jobs/new", "text/javascript", code)
    val contents = EntityUtils.toString(response.getEntity())

    LOG.debug("Retrieved entity: " + contents)

    parse(contents)
  }

  private def parse(json: String) = {
    JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
  }

}
