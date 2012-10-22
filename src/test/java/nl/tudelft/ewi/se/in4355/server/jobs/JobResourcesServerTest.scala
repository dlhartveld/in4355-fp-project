package nl.tudelft.ewi.se.in4355.server.jobs

import org.apache.http.util.EntityUtils
import org.junit.Test
import nl.tudelft.ewi.se.in4355.server.GridServerTestBase
import org.apache.http.HttpEntity
import sun.org.mozilla.javascript.ast.ForLoop

class JobResourcesServerTest extends GridServerTestBase {

  @Test def test {

    var i: Int = 0

    //    while (i < 100) {
    var response = post("/resources/jobs/input", "").getEntity()

    println("Server response:")
    println
    println(EntityUtils.toString(response))
    println
    println

    i = i + 1
    //    }

  }

}
