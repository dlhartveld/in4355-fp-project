package nl.tudelft.ewi.se.in4355.server.jobs

class JobSource {

  val input = LoremIpsum

  def nextPackage = {
    input.nextPackage;
  }
  
  def markDone(id: Int) {
    input.markDone(id)
  }
  
  def hasNext = {
    input.hasNext;
  }
  
}
