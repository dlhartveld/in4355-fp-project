package nl.tudelft.ewi.se.in4355.server

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpStatus
import grizzled.slf4j.Logger

class GridServerTest extends GridServerTestBase with JUnitSuite {

  val LOG = Logger(classOf[GridServerTest])

  @Test def run {

    val response = getOrFail("/")

    LOG.info("Server says yes: " + response.getStatusLine)

  }

}
