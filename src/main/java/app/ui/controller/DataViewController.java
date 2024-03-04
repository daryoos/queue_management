package app.ui.controller;

import app.businessLogic.SimulationManager;
import app.businessLogic.strategyLogic.SelectionPolicy;
import app.ui.single_point_access.GUIFrameSinglePointAccess;
import app.ui.view.DataView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class DataViewController {
    DataView dataView;
    public void startLogic() {
        dataView = new DataView();

        GUIFrameSinglePointAccess.changePanel(dataView.getMainPanel(), "data");

        dataView.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer numberOfClients = parseInt(dataView.getNoClientsField().getText());
                Integer numberOfServers = parseInt(dataView.getNoQueuesField().getText());
                Integer timeLimit = parseInt(dataView.getTimeLimitField().getText());
                Integer maxProcessingTime = parseInt(dataView.getServiceMaxField().getText());
                Integer minProcessingTime = parseInt(dataView.getServiceMinField().getText());
                Integer maxArrivalTime = parseInt(dataView.getArrivalMaxField().getText());
                Integer minArrivalTime = parseInt(dataView.getArrivalMinField().getText());

                SimulationManager simulationManager = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, maxArrivalTime, minArrivalTime, numberOfServers, numberOfClients, SelectionPolicy.SHORTEST_TIME);
                simulationManager.start();
            }
        });
    }
}
