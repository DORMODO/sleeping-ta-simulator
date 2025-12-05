package model;

import controller.Controller;
import model.enums.StudentState;

/**
 * Student Thread - Represents a student seeking help from TAs
 * <p>
 * YOUR TASK: Implement the Student's behavior in the run() method.
 * <p>
 * Student Lifecycle:
 * ARRIVING → Try to get help → GETTING_HELP → LEAVING → (break) → ARRIVING
 * (repeat)
 * ↓
 * (no chairs)
 * ↓
 * RETURNING_LATER → (wait) → ARRIVING (repeat)
 */
public class Student extends Thread {
    private int id;
    private StudentState state;
    private Controller controller;

    public Student(int id, Controller controller) {
        this.id = id;
        this.controller = controller;
        this.state = StudentState.ARRIVING;
    }

    @Override
    public void run() {
        while (true) {
            try {

                // TODO: Step 1 - Student arrives at office
                setState(StudentState.ARRIVING);
                System.out.println("Student " + id + " is arriving.");

                // TODO: Think about the problem (sleep 0-2 seconds randomly)
                Thread.sleep((long) (Math.random() * 2000));

                // TODO: Step 2 - Try to get help from Controller
                boolean gotHelp = controller.getHelp(this);

                // TODO: Step 3 - Check if got help (true/false)

                // IF GOT HELP (true):
                if (gotHelp == true) {
                    // TODO: Change state to GETTING_HELP
                    setState(StudentState.GETTING_HELP);
                    // TODO: Print: "Student X is getting help from TA..."
                    System.out.println("Student " + id + " is getting help from TA...");

                    // TODO: Getting help takes time (sleep 2-4 seconds)
                    Thread.sleep((long) (Math.random() * 2000) + 2000);
                    // TODO: Change state to LEAVING
                    setState(StudentState.LEAVING);
                    // TODO: Print: "Student X got help and is leaving!"
                    System.out.println("Student " + id + " got help and is leaving!");
                    // TODO: Release the TA back to controller
                    controller.releaseTA();
                    // TODO: Take a break before coming back (sleep 5-10 seconds)
                    Thread.sleep((long) (Math.random() * 5000) + 5000);

                }
                // IF NO HELP (false - no chairs available):
                if (gotHelp == false) {
                    // TODO: Change state to RETURNING_LATER
                    setState(StudentState.RETURNING_LATER);

                    // TODO: Wait before trying again (sleep 3-6 seconds)
                    Thread.sleep((long) (Math.random() * 3000) + 3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public StudentState getSTDState() {
        return state;
    }

    public void setState(StudentState newState) {
        this.state = newState;
    }
}