package app.businessLogic.strategyLogic;

import app.model.Server;
import app.model.Task;

import java.util.List;

public interface Strategy {
    public Server addTask(List<Server> servers, Task task);
}
