package nl.tudelft.ewi.se.in4355.server

import org.codehaus.jackson.annotate.JsonCreator
import org.codehaus.jackson.annotate.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Count(var word: String, var count: Integer) {
    @JsonCreator
    def this() {
    	this(word = "", count = 0);
    }
};