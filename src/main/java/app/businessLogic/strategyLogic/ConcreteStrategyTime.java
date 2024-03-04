package app.businessLogic.strategyLogic;

import app.model.Server;
import app.model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public Server addTask(List<Server> servers, Task task) {
        Integer minTime = Integer.MAX_VALUE - 1;
        Server server = null;
        for (Server serverI : servers) {
            Integer time = serverI.getWaitingPeriod().get();
            if (time < minTime) {
                //System.out.println(time);
                minTime = time;
                server = serverI;
            }
        }
        if (server == null) {
            Exception e = new Exception("addTask: server not found");
            throw new RuntimeException(e);
        }
        else {
            server.addTask(task);
            //System.out.println(task.getServiceTime() + " " + task.getArrivalTime());
        }
        return server;
    }
}
