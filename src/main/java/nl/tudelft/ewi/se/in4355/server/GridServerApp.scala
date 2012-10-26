package nl.tudelft.ewi.se.in4355.server

import grizzled.slf4j.Logger
import nl.tudelft.ewi.se.in4355.server.jobs.TaskTracker
import nl.tudelft.ewi.se.in4355.server.jobs.WordCounterJob

object GridServerApp {

  val LOG = Logger("GridServer")

  val PORT = 10001;

  val server = new GridServer(PORT)

  def main(args: Array[String]) = {

    LOG.info("Starting server on port: " + PORT + " ...");
    server.start;
    
    new WordCounterJob("loremipsum.txt").submit;

    LOG.info("Server started.");
    server.join;
  }

}
