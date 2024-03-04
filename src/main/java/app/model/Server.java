package app.model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static app.businessLogic.SimulationManager.empty;
import static app.businessLogic.SimulationManager.timeLimit;

public class Server extends Thread {
    private Integer id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Integer currentTime;
    private Integer totalServiceTime;
    private Integer totalWaitingTime;
    private Integer totalServedClients;
    private Integer clientsWaiting;

    public Server(Integer id) {
        this.id = id;
        tasks = new ArrayBlockingQueue<>(1000);
        waitingPeriod = new AtomicInteger(0);
        currentTime = 0;
        totalServiceTime = 0;
        totalWaitingTime = 0;
        totalServedClients = 0;
        clientsWaiting = 0;
        this.start();
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        try {
            boolean firstEntry = true;
            Integer taskServiceTime = 0;
            while (currentTime <= timeLimit && !empty) {
                if (!tasks.isEmpty()) {
                    clientsWaiting = tasks.size();
                    if (firstEntry) {
                        firstEntry = false;
                        tasks.peek().setWaitingTime(waitingPeriod.get());
                        taskServiceTime = tasks.peek().getServiceTime();
                    }
                    waitingPeriod.decrementAndGet();
                    tasks.peek().setServiceTime(tasks.peek().getServiceTime() - 1);
                    if (tasks.peek().getServiceTime().equals(0)) {
                        firstEntry = true;
                        totalServedClients++;
                        totalServiceTime += taskServiceTime;
                        totalWaitingTime += tasks.peek().getWaitingTime();
                        tasks.take();
                    }
                }
                //System.out.println(waitingPeriod);
                currentTime++;
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String serverString = new String("Queue " + this.id + ": ");
        if (!tasks.isEmpty()) {
            serverString = serverString.concat( this.waitingPeriod + " " + tasks.toString());
        }
        else {
            serverString = serverString.concat("closed");
        }
        return serverString;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> t = new ArrayList<>();
        for (Task task : tasks) {
            t.add(task);
        }
        return t;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public Integer getTotalServiceTime() {
        return totalServiceTime;
    }

    public Integer getTotalServedClients() {
        return totalServedClients;
    }

    public Integer getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public Integer getClientsWaiting() {
        return clientsWaiting;
    }
}
