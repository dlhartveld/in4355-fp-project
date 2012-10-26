package nl.tudelft.ewi.se.in4355.server.jobs

import scala.collection.mutable.LinkedHashMap

object TaskTracker {

  private var tasks = LinkedHashMap[Int, Task[_, _]]();
  private var counter = 0;

  def submitTask(task: Task[_, _]) {
    this.synchronized {
      clearDoneJobs();
      tasks = tasks += (counter -> task);
      counter += 1;
    }
  }

  def hasTasks: Boolean = {
    this.synchronized {
      clearDoneJobs();
      return !tasks.isEmpty;
    }
  }

  def hasMoreData(taskId: Int): Boolean = {
    val task = getTask(taskId);
    if (task != null) {
      return task.hasNext;
    }
    return false;
  }

  def getTask(taskId: Int): Task[_, _] = {
    this.synchronized {
      clearDoneJobs();
      try {
        return tasks(taskId);
      } catch {
        case nsee: NoSuchElementException => return null;
      }
    }
  }

  def getCurrentTaskId(): Int = {
    this.synchronized {
      clearDoneJobs();
      return tasks.keySet.iterator.next;
    }
  }

  private def clearDoneJobs() {
    this.synchronized {
      for (i <- tasks.keySet) {
        val task = tasks(i);
        if (task != null && !task.hasNext) {
          tasks -= i;
        }
      }
    }
  }

}