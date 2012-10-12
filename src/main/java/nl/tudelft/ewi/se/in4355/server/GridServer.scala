package nl.tudelft.ewi.se.in4355.server

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import java.io.File

object GridServer {

  val PORT = 10000;

  def main(args: Array[String]) = {

    var server = new Server(PORT);
    server.setHandler(buildWebAppContext);
    server.start;

    server.join;

  }

  def buildWebAppContext = {
    var webAppContext = new WebAppContext();
    webAppContext.setWar(new File("src/main/webapp/").getAbsolutePath());
    webAppContext;
  }

}
