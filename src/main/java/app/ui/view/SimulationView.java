package app.ui.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class SimulationView {
    @Getter
    private JPanel SimulationPanel;
    @Getter
    private JTextArea simulationTextArea;
    public SimulationView() {
        SimulationPanel = new JPanel();
        SimulationPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        simulationTextArea = new JTextArea();
        simulationTextArea.setLineWrap(true);
        SimulationPanel.add(simulationTextArea, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(500, 500), null, 0, false));
    }
}
