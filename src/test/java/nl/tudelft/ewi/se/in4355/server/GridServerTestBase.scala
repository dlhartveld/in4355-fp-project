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
import grizzled.slf4j.Logger
import org.apache.http.util.EntityUtils

class GridServerTestBase extends JUnitSuite {

  private val LOG = Logger(classOf[GridServerTestBase])

  private val PORT = 10001
  private val HOST = "localhost"

  private val url = "http://" + HOST + ":" + PORT + "/"

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
    LOG.trace("GET: " + path)
    okOrFail(client.execute(httpGetForUri(url + path)))
  }

  def post(path: String, contents: String): HttpResponse = {
    LOG.trace("POST: " + path + " - contents: " + contents)
    okOrFail(client.execute(httpPostForUri(url + path, contents)))
  }

  private def okOrFail(response: HttpResponse): HttpResponse = response.getStatusLine().getStatusCode() match {
    case HttpStatus.SC_OK => response
    case _ => {
      fail("Got client response: " + response.getStatusLine() + "\n" + EntityUtils.toString(response.getEntity()))
    }
  }

  private def httpGetForUri(uri: String): HttpGet = {
    new HttpGet(uri)
  }

  private def httpPostForUri(uri: String, contents: String): HttpPost = {
    val post = new HttpPost(uri)
    post.setEntity(new StringEntity(contents))
    post
  }

}
