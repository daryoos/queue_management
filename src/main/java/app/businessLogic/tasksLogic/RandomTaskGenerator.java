package app.businessLogic.tasksLogic;

import app.model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class RandomTaskGenerator {

    public RandomTaskGenerator(CopyOnWriteArrayList<Task> allTasks, Integer noTasks, Integer timeLimit, Integer minProcessingTime, Integer maxProcessingTime, Integer minArrivalTime, Integer maxArrivalTime) {
        Random random = new Random();
        Integer id = 1;
        for (Integer i = 0; i < noTasks; i++) {
            Integer service = random.nextInt(minProcessingTime, maxProcessingTime);
            Integer arrival = random.nextInt(minArrivalTime, maxArrivalTime);
            Task task = new Task(id, arrival, service);
            //System.out.println(task.getArrivalTime() + " " + task.getServiceTime());
            id++;
            allTasks.add(task);
            /*
            if (allTasks.containsKey(arrival)) {
                allTasks.get(arrival).add(task);
            } else {
                CopyOnWriteArrayList<Task> temp = new CopyOnWriteArrayList<>();
                temp.add(task);
                allTasks.put(arrival, temp);
            }
            */
        }
        allTasks.sort(Comparator.comparing(Task::getArrivalTime));
    }

}
