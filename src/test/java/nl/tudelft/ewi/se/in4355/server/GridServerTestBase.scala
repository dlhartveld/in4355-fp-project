package nl.tudelft.ewi.se.in4355.server

import org.scalatest.junit.JUnitSuite
import org.junit.Before
import org.junit.After
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.HttpResponse
import org.apache.http.HttpRequest
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.HttpStatus

class GridServerTestBase extends JUnitSuite {

  val PORT = 10001
  val HOST = "localhost"

  val url = "http://" + HOST + ":" + PORT + "/"

  /*
   * Server stuff
   */

  private val server = new GridServer(PORT)

  @Before def setUp {
    server.start
  }

  @After def tearDown {
    server.stop
  }

  /*
   * Client stuff
   */

  private val client = new DefaultHttpClient

  def get(path: String): HttpResponse = {
    okOrFail(client.execute(httpGetOfUri(url + path)))
  }

  def post(path: String, contents: String): HttpResponse = {
    okOrFail(client.execute(newHttpPost(url + path, contents)))
  }

  private def okOrFail(response: HttpResponse): HttpResponse = response.getStatusLine().getStatusCode() match {
    case HttpStatus.SC_OK => response
    case _ => fail("Got client response: " + response)
  }

  private def httpGetOfUri(uri: String): HttpGet = {
    new HttpGet(uri)
  }

  private def newHttpPost(uri: String, contents: String): HttpPost = {
    val post = new HttpPost(uri)
    post.setEntity(new StringEntity(contents))
    post
  }

}
