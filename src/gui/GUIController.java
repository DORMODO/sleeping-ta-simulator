package gui;

import controller.Controller;

public class GUIController {

    private MainGUI gui;
    private Controller controller;

    public GUIController(MainGUI gui) {
        this.gui = gui;
        this.controller = new Controller(gui);

        gui.startBtn.addActionListener(e -> {
            int students = Integer.parseInt(gui.tfStudents.getText());
            int chairs = Integer.parseInt(gui.tfChairs.getText());
            int tas = Integer.parseInt(gui.tfTAs.getText());

            controller.startSimulation(students, chairs, tas);
        });
    }
}
