package nl.tudelft.ewi.se.in4355.server.jobs

import org.codehaus.jackson.annotate.JsonCreator
import org.codehaus.jackson.annotate.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Sentences(val id: Int, val sentences: java.util.List[String]) {
    @JsonCreator
    def this() {
    	this(id = -1, sentences = new java.util.ArrayList());
    }
    
    def isEmpty: Boolean = sentences.isEmpty
}