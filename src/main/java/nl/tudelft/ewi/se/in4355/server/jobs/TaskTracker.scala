package nl.tudelft.ewi.se.in4355.server.jobs

import java.util.concurrent.Executor
import java.util.concurrent.ScheduledThreadPoolExecutor

object TaskTracker {

  private var tasks = List[Task[_, _]]();
  private var pointer = 0;

  def submitTask(task: Task[_, _]) {
    this.synchronized {
      tasks = tasks.+:(task);
    }
  }

  def hasTasks: Boolean = {
    this.synchronized {
      return !tasks.isEmpty;
    }
  }

  def hasMoreData(taskId: Int): Boolean = {
    this.synchronized {
      if (pointer > taskId) {
        return false;
      }
      return tasks(taskId - pointer).hasNext
    }
  }

  def getTask(taskId: Int): Task[_, _] = {
    this.synchronized {
      return tasks(taskId - pointer);
    }
  }

  def getCurrentTaskId(): Int = {
    this.synchronized {
      while (!tasks.isEmpty) {
        val task = tasks(0);
        if (!task.hasNext) {
          tasks = tasks.drop(1);
          pointer += 1;
        } else {
          return pointer;
        }
      }
      return -1;
    }
  }

}