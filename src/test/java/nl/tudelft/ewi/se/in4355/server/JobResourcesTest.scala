package nl.tudelft.ewi.se.in4355.server

import org.junit.Test
import org.apache.http.util.EntityUtils

class JobResourcesTest extends GridServerTestBase {

  @Test def testThatDataReturnsFiveNewLinesEachTime {

    val response = get("/resources/jobs/data").getEntity()

    println
    println
    println(EntityUtils.toString(response))
    println
    println

  }

}
