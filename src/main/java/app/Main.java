package app;

import app.ui.controller.DataViewController;

public class Main {
    public static void main(String[] args) {

        /*
        Integer timeLimit = 60;
        Integer maxProcessingTime = 9;
        Integer minProcessingTime = 2;
        Integer maxArrivalTime = 30;
        Integer minArrivalTime = 2;
        Integer numberOfServers = 5;
        Integer numberOfClients = 50;
        SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

        new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, maxArrivalTime, minArrivalTime, numberOfServers, numberOfClients, selectionPolicy).start();
        */

        DataViewController dataViewController = new DataViewController();
        dataViewController.startLogic();
    }
}