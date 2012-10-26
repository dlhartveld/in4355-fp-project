package nl.tudelft.ewi.se.in4355.server.jobs

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonCreator
import java.lang.ref.ReferenceQueue.Null

@JsonIgnoreProperties(ignoreUnknown = true)
class Input[T](val id: Int, val value: T) {
  
    @JsonCreator
    def this() {
    	this(id = -1, value = null.asInstanceOf[T]);
    }
}