package app.businessLogic;

import app.businessLogic.strategyLogic.SelectionPolicy;
import app.businessLogic.tasksLogic.WaitingTasks;
import app.model.Server;
import app.model.Task;
import app.ui.single_point_access.GUIFrameSinglePointAccess;
import app.ui.view.SimulationView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationManager extends Thread {
    public static Integer timeLimit;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer maxArrivalTime;
    public Integer minArrivalTime;
    public Integer numberOfServers;
    public Integer numberOfClients;
    public SelectionPolicy selectionPolicy;

    private Scheduler scheduler;
    //private SimulationFrame frame;
    private WaitingTasks waitingTasks;

    public static boolean empty;

    private Integer currentTime;
    private Double averageServiceTime;
    private Double averageWaitingTime;
    private Integer totalServedClients;
    private Integer peekHour;

    SimulationView simulationView;

    //FileWriter fw;


    public SimulationManager(Integer timeLimit, Integer maxProcessingTime, Integer minProcessingTime, Integer maxArrivalTime, Integer minArrivalTime, Integer numberOfServers, Integer numberOfClients, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = selectionPolicy;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        waitingTasks = new WaitingTasks(numberOfClients, timeLimit, minProcessingTime, maxProcessingTime, minArrivalTime, maxArrivalTime);
        scheduler.changeStrategy(selectionPolicy);
        empty = false;
        currentTime = 0;
        averageServiceTime = 0.0;
        averageWaitingTime = 0.0;
        totalServedClients = 0;
        peekHour = 0;
        simulationView = new SimulationView();

        /*
        try {
            fw = new FileWriter("C:\\PT\\QueueManagement\\src\\main\\java\\app\\textFiles\\exemplu3.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        GUIFrameSinglePointAccess.changePanel(simulationView.getSimulationPanel(), "sim");
    }

    @Override
    public void run() {
        currentTime = 0;
        Integer totalClientsWaiting = 0;
        Integer maxClientsWaiting = -1;
        while (currentTime <= timeLimit && !empty) {
            averageServiceTime = 0.0;
            averageWaitingTime = 0.0;
            totalServedClients = 0;
            totalClientsWaiting = 0;
            if (waitingTasks.getAllTasks().isEmpty()) {
                empty = true;
                for (Server serverI: scheduler.getServers()) {
                    if (!serverI.getTasks().isEmpty()) {
                        empty = false;
                        break;
                    }
                }
            }
            //System.out.println("Time " + currentTime);
            /*
            try {
                fw.write("Time " + currentTime + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
             */
            simulationView.getSimulationTextArea().setText("Time " + currentTime + "\n");
            CopyOnWriteArrayList<Task> tasks = waitingTasks.getAllTasks();
            if (tasks != null) {
                for (Task taskI : tasks) {
                    if (taskI.getArrivalTime().equals(currentTime)) {
                        Server server = scheduler.dispatchTask(taskI);
                        if (server != null) {
                            taskI.setWaitingTime(server.getWaitingPeriod().get() - taskI.getServiceTime());
                        }
                        else {
                            throw new RuntimeException("dispatch: server null");
                        }
                        waitingTasks.getAllTasks().remove(taskI);
                    }
                }
            }
            //System.out.println("Waiting tasks: " + waitingTasks.toString());
            /*
            try {
                fw.write("Waiting tasks: " + waitingTasks.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

             */
            simulationView.getSimulationTextArea().append("Waiting tasks: " + waitingTasks.toString() + "\n");
            for (Server serverI: scheduler.getServers()) {
                //System.out.println(serverI.toString());
                /*
                try {
                    fw.write(serverI.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                 */
                simulationView.getSimulationTextArea().append(serverI.toString() + "\n");
                averageServiceTime += serverI.getTotalServiceTime();
                averageWaitingTime += serverI.getTotalWaitingTime();
                totalServedClients += serverI.getTotalServedClients();
                totalClientsWaiting += serverI.getClientsWaiting();
            }
            if (totalClientsWaiting > maxClientsWaiting) {
                maxClientsWaiting = totalClientsWaiting;
                peekHour = currentTime;
            }
            //System.out.println();
            /*
            try {
                fw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

             */
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (!totalServedClients.equals(0)) {
            if (!averageServiceTime.equals(0.0)) {
                averageServiceTime /= totalServedClients;
            }
            if (!averageWaitingTime.equals(0.0)) {
                averageWaitingTime /= totalServedClients;
            }
        }
        else {
            averageServiceTime = 0.0;
            averageWaitingTime = 0.0;
        }
        //System.out.println("Average waiting time: " + averageWaitingTime);
        simulationView.getSimulationTextArea().setText("Average waiting time: " + averageWaitingTime + "\n");
        //System.out.println("Average service time: " + averageServiceTime);
        simulationView.getSimulationTextArea().append("Average service time: " + averageServiceTime + "\n");
        //System.out.println("Peek hour: " + peekHour);
        simulationView.getSimulationTextArea().append("Peek hour: " + peekHour);

        /*
        try {
            fw.write("Average waiting time: " + averageWaitingTime + "\n");
            fw.write("Average service time: " + averageServiceTime + "\n");
            fw.write("Peek hour: " + peekHour);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    public Integer getCurrentTime() {
        return currentTime;
    }
    public Scheduler getScheduler() {
        return scheduler;
    }
    public WaitingTasks getWaitingTasks() {
        return waitingTasks;
    }
    public Double getAverageServiceTime() {
        return averageServiceTime;
    }
    public Double getAverageWaitingTime() {
        return averageWaitingTime;
    }
    public Integer getTotalServedClients() {
        return totalServedClients;
    }
    public Integer getPeekHour() {
        return peekHour;
    }
}
