package nl.tudelft.ewi.se.in4355.server.jobs

import java.util.concurrent.Executor
import java.util.concurrent.ScheduledThreadPoolExecutor

object TaskTracker {

  private var tasks = List[Task[_, _]]();
  
  def submitTask(task: Task[_, _]) {
    tasks = tasks.+:(task);
  }
  
  def hasTasks = !tasks.isEmpty;
  
  def getCurrentTask(): Task[_, _] = {
    while (!tasks.isEmpty) {
      val task = tasks(0);
      if (!task.hasNext) {
        tasks = tasks.drop(1);
      }
      else {
        return task;
      }
    }
    return null;
  }
  
}