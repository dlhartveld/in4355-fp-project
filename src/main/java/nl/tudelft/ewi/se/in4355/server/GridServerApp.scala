package nl.tudelft.ewi.se.in4355.server

import java.io.InputStreamReader
import java.util.concurrent.Executors

import grizzled.slf4j.Logger
import nl.tudelft.ewi.se.in4355.server.jobs.wordcount.WordCountJob

object GridServerApp {

  val LOG = Logger("GridServer")

  val PORT = 10001;

  val server = new GridServer(PORT)

  def main(args: Array[String]) = {

    LOG.info("Starting server on port: " + PORT + " ...");
    server.start;

    LOG.info("Press ENTER to submit job!");
    val reader = new InputStreamReader(System.in);
    var read = -1;
    do {
      read = reader.read();
    } while (read != 10)
    reader.close()

    LOG.info("Submitting lorem ipsum job...");

    val executor = Executors.newFixedThreadPool(1);
    val future = executor.submit(new WordCountJob("lorem.txt"));

    future.get().printContents;

    LOG.info("Server started.");
    server.join;
  }

}
