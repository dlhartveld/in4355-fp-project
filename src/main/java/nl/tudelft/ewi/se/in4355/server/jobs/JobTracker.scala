package nl.tudelft.ewi.se.in4355.server.jobs

object JobTracker {

  private var jobCounter = JobCounter

  val jobs = scala.collection.mutable.Map.empty[Int, String]

  def addNewJob(code: String) = {
    val id = jobCounter.next
    jobs.put(id, code)
    id
  }

  def codeForJob(id: Int) = {
    jobs.get(id)
  }

  private object JobCounter {

    var counter = 0

    def next = {
      synchronized {
        val result = counter
        counter = counter + 1
        result
      }
    }

  }

}
