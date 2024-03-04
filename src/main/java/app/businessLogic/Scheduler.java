package app.businessLogic;

import app.businessLogic.strategyLogic.ConcreteStrategyQueue;
import app.businessLogic.strategyLogic.ConcreteStrategyTime;
import app.businessLogic.strategyLogic.SelectionPolicy;
import app.businessLogic.strategyLogic.Strategy;
import app.model.Server;
import app.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private ArrayList<Server> servers;
    Integer maxNoServers;
    Integer maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(Integer maxNoServers, Integer maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>(maxNoServers);
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(i + 1);
            servers.add(server);
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public Server dispatchTask(Task task) {
        return strategy.addTask(servers, task);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
