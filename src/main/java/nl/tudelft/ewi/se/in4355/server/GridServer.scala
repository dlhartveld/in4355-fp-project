package nl.tudelft.ewi.se.in4355.server

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import java.io.File
import nl.tudelft.ewi.se.in4355.server.jobs.WordCounterJob

class GridServer(port: Int) {

  var server = configuredServer

  def start {
    server.start
  }

  def stop {
    server.stop
  }

  def join {
    server.join
  }

  private def configuredServer: Server = {
    val server = new Server(port)
    server.setHandler(webAppContext)
    server
  }

  private def webAppContext: WebAppContext = {
    var context = new WebAppContext
    context.setWar(webAppPath)
    context
  }

  private def webAppPath = {
    new File("src/main/webapp").getAbsolutePath()
  }

}
