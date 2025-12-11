package gui;

import controller.Controller;

import javax.swing.*;

public class GUIController {

    private final Controller controller;

    public GUIController(MainGUI gui) {
        this.controller = new Controller(gui);

        gui.startBtn.addActionListener(_ -> {
            try {
                int students = Integer.parseInt(gui.tfStudents.getText());
                int chairs = Integer.parseInt(gui.tfChairs.getText());
                int tas = Integer.parseInt(gui.tfTAs.getText());

                if (students <= 0 || chairs < 0 || tas <= 0) {
                    JOptionPane.showMessageDialog(gui,
                            "Please enter valid values:\n- Students > 0\n- Chairs >= 0\n- TAs > 0",
                            "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                controller.startSimulation(students, chairs, tas);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui,
                        "Please enter valid integers for all fields.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
