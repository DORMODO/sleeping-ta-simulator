package model;

import controller.Controller;
import model.enums.StudentState;

public class Student extends Thread {
    private final int id;
    private StudentState state;
    private final Controller controller;

    public Student(int id, Controller controller) {
        this.id = id;
        this.controller = controller;
        this.state = StudentState.ARRIVING;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // STEP 1: Student arrives at office
                setState(StudentState.ARRIVING);
                System.out.println("Student " + id + " is arriving...");

                // Think about the problem before going to TA (0-2 seconds)
                Thread.sleep((long)(Math.random() * 2000));

                // STEP 2: Try to get help from a TA
                boolean gotHelp = controller.getHelp(this);

                if (gotHelp) {
                    // SUCCESS: Got a TA to help
                    setState(StudentState.GETTING_HELP);
                    System.out.println("Student " + id + " is getting help from TA...");

                    // Getting help takes time (2-4 seconds)
                    Thread.sleep(2000 + (long)(Math.random() * 2000));

                    // STEP 3: Done getting help, leaving
                    setState(StudentState.LEAVING);
                    System.out.println("Student " + id + " got help and is leaving!");

                    // Release the TA
                    controller.releaseTA();

                    // Take a break before coming back with another question (5-10 seconds)
                    Thread.sleep(5000 + (long)(Math.random() * 5000));

                } else {
                    // FAILED: No chairs available, must come back later
                    setState(StudentState.RETURNING_LATER);
                    System.out.println("Student " + id + " will come back later (no chairs)...");

                    // Wait before trying again (3-6 seconds)
                    Thread.sleep(3000 + (long)(Math.random() * 3000));
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Student " + id + " thread interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    public void setState(StudentState newState) {
        this.state = newState;
    }
}