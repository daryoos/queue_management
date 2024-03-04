package app.businessLogic.strategyLogic;

import app.model.Server;
import app.model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public Server addTask(List<Server> servers, Task task) {
        Integer minTasks = Integer.MAX_VALUE - 1;
        Server server = null;
        for (Server serverI : servers) {
            Integer noTasks = serverI.getTasks().size();
            if (noTasks < minTasks) {
                minTasks = noTasks;
                server = serverI;
            }
        }
        if (server == null) {
            Exception e = new Exception("addTask: server not found");
            throw new RuntimeException(e);
        }
        else {
            server.addTask(task);
        }
        return server;
    }
}
