package nl.tudelft.ewi.se.in4355.server.jobs

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonCreator

@JsonIgnoreProperties(ignoreUnknown = true)
case class Output[T](val id: Int, val value: T) {
  @JsonCreator
  def this() {
    this(id = -1, value = null.asInstanceOf[T]);
  }
}