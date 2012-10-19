package nl.tudelft.ewi.se.in4355.server

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import java.io.File
import grizzled.slf4j.Logger

object GridServerApp {

  val LOG = Logger("GridServer")

  val PORT = 10001;

  val server = new GridServer(PORT)

  def main(args: Array[String]) = {

    LOG.info("Starting server on port: " + PORT + " ...");
    server.start;

    LOG.info("Server started.");
    server.join;

  }

}
