package app.businessLogic.tasksLogic;

import app.model.Task;

import java.util.concurrent.CopyOnWriteArrayList;

public class WaitingTasks {
    CopyOnWriteArrayList<Task> allTasks;

    public WaitingTasks(Integer noTasks, Integer timeLimit, Integer minProcessingTime, Integer maxProcessingTime, Integer minArrivalTime, Integer maxArrivalTime) {
        allTasks = new CopyOnWriteArrayList<>();
        new RandomTaskGenerator(allTasks, noTasks, timeLimit, minProcessingTime, maxProcessingTime, minArrivalTime, maxArrivalTime);
    }

    public CopyOnWriteArrayList<Task> getAllTasks() {
        return allTasks;
    }

    @Override
    public String toString() {
        if (allTasks.isEmpty()) {
            return "closed";
        }
        String tasksString = new String(allTasks.toString());
        return tasksString;
    }
}
