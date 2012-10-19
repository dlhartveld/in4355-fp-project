package nl.tudelft.ewi.se.in4355.server

import org.scalatest.junit.JUnitSuite
import org.junit.Before
import org.junit.After
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.HttpResponse
import org.apache.http.HttpRequest
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.HttpGet

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
    client.execute(httpGetOfUri(url + path))
  }

  def httpGetOfUri(uri: String): HttpGet = {
    new HttpGet(uri)
  }

}
