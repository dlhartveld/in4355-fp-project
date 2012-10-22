package nl.tudelft.ewi.se.in4355.server.jobs

import scala.collection.JavaConversions
import scala.collection.immutable.Range

object LoremIpsum {
  
  private val packageSize = 5;
  private val packages = listPackages;
  
  private var indices = listIndices;
  private var index = 0;
  
  def nextPackage: Sentences = {
    this.synchronized {
      if (index >= 0) {
    	val value = new Sentences(index, JavaConversions.seqAsJavaList(packages(index)));
    	updateIndex;
	    return value;
      }
      return new Sentences();
    }
  }
  
  def updateIndex {
    if (!hasNext) {
      index = -1;
      return;
    }
    
    while (true) {
      index += 1;
      if (index == packages.size) {
        index = 0;
      }
      
      if (indices.contains(index)) {
        return;
      }
    }
  }
  
  def hasNext: Boolean = {
    this.synchronized {
    	return !indices.isEmpty;
    }
  }
  
  def markDone(id: Int) {
    this.synchronized {
    	indices = indices.-(id);
    }
  }
  
  def listPackages: List[List[String]] = {
    allLines.grouped(packageSize).toList;
  }
  
  def listIndices: Set[Int] = {
    new Range(0, packages.length, 1).toSet;
  }
  
  def allLines = {
    scala.io.Source.fromInputStream(resourceStream).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  private def resourceStream = {
    getClass.getResourceAsStream("/loremipsum.txt")
  }
}
