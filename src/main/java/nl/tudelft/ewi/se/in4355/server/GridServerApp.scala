package nl.tudelft.ewi.se.in4355.server

import grizzled.slf4j.Logger
import nl.tudelft.ewi.se.in4355.server.jobs.wordcount.WordCountJob
import java.util.concurrent.Executors

object GridServerApp {

  val LOG = Logger("GridServer")

  val PORT = 10001;

  val server = new GridServer(PORT)

  def main(args: Array[String]) = {

    LOG.info("Starting server on port: " + PORT + " ...");
    server.start;

    val executor = Executors.newFixedThreadPool(1);
    val future = executor.submit(new WordCountJob("game-of-thrones.txt"));

    future.get().printContents;

    LOG.info("Server started.");
    server.join;
  }

}
