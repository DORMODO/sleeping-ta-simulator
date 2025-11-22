package gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    // Input fields
    public JTextField tfStudents;
    public JTextField tfChairs;
    public JTextField tfTAs;

    // Output labels
    public JLabel lblWorking;
    public JLabel lblSleeping;
    public JLabel lblWaiting;
    public JLabel lblLeft;

    public JButton startBtn;

    public MainGUI() {
        setTitle("Sleeping TA Simulator");
        setSize(250, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Students:");
        l1.setBounds(20, 20, 100, 30);
        add(l1);

        tfStudents = new JTextField();
        tfStudents.setBounds(120, 20, 80, 30);
        add(tfStudents);

        JLabel l2 = new JLabel("Chairs:");
        l2.setBounds(20, 60, 100, 30);
        add(l2);

        tfChairs = new JTextField();
        tfChairs.setBounds(120, 60, 80, 30);
        add(tfChairs);

        JLabel l3 = new JLabel("TAs:");
        l3.setBounds(20, 100, 100, 30);
        add(l3);

        tfTAs = new JTextField();
        tfTAs.setBounds(120, 100, 80, 30);
        add(tfTAs);

        startBtn = new JButton("Start Simulation");
        startBtn.setBounds(20, 150, 180, 40);
        add(startBtn);

        lblWorking = new JLabel("TAs Working: 0");
        lblWorking.setBounds(20, 210, 300, 30);
        add(lblWorking);

        lblSleeping = new JLabel("TAs Sleeping: 0");
        lblSleeping.setBounds(20, 240, 300, 30);
        add(lblSleeping);

        lblWaiting = new JLabel("Students Waiting: 0");
        lblWaiting.setBounds(20, 270, 300, 30);
        add(lblWaiting);

        lblLeft = new JLabel("Students that will come later: 0");
        lblLeft.setBounds(20, 300, 300, 30);
        add(lblLeft);


        new GUIController(this);

        setVisible(true);
    }
}