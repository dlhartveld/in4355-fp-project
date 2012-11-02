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
import nl.tudelft.ewi.se.in4355.server.jobs.wordcount.WordCountJob
import java.util.concurrent.Executors

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

    val executor = Executors.newFixedThreadPool(1);
    val future = executor.submit(new WordCountJob("loremipsum.txt"));
  }

  @After def tearDown {
    server.stop
  }

  /*
   * Client stuff
   */

  private val client = new DefaultHttpClient

  def getOrFail(path: String): HttpResponse = {
    LOG.trace("GET: " + path)
    okOrFail(client.execute(httpGetForUri(url + sanitized(path))))
  }

  def postOrFail(path: String): HttpResponse = {
    LOG.trace("POST: " + path)
    okOrFail(client.execute(httpPostForUri(url + sanitized(path))))
  }

  def postOrFail(path: String, contentType: String, contents: String): HttpResponse = {
    LOG.trace("POST: " + path + " - content-type: " + contentType + " - contents: " + contents)
    okOrFail(client.execute(httpPostForUri(url + sanitized(path), contentType, contents)))
  }

  private def okOrFail(response: HttpResponse): HttpResponse = response.getStatusLine().getStatusCode() match {
    case HttpStatus.SC_OK => response
    case _ => {
      fail("Got client response: " + response.getStatusLine() + "\n" + contentsOfResponse(response))
    }
  }

  private def contentsOfResponse(response: HttpResponse) = response.getEntity() match {
    case null => ""
    case _ => EntityUtils.toString(response.getEntity())
  }

  private def httpGetForUri(uri: String): HttpGet = {
    LOG.trace("Creating GET for URI: " + uri)
    new HttpGet(uri)
  }

  private def httpPostForUri(uri: String): HttpPost = {
    LOG.trace("Creating POST for URI: " + uri)
    new HttpPost(uri)
  }

  private def httpPostForUri(uri: String, contentType: String, contents: String): HttpPost = {
    LOG.trace("Creating POST for URI: " + uri + " - content-type: " + contentType + " - contents: " + contents)
    val post = new HttpPost(uri)
    val entity = new StringEntity(contents)
    entity.setContentType(contentType)
    post.setEntity(entity)
    post
  }

  private def sanitized(path: String): String = path match {
    case path if path startsWith "/" => path.tail
    case _ => path
  }

}
