package model;

import controller.Controller;
import model.enums.StudentState;


/**
 * Student Thread - Represents a student seeking help from TAs
 * <p>
 * YOUR TASK: Implement the Student's behavior in the run() method.
 * <p>
 * Student Lifecycle:
 * ARRIVING → Try to get help → GETTING_HELP → LEAVING → (break) → ARRIVING (repeat)
 *                   ↓
 *               (no chairs)
 *                   ↓
 *             RETURNING_LATER → (wait) → ARRIVING (repeat)
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

            // TODO: Step 1 - Student arrives at office

            // TODO: Think about the problem (sleep 0-2 seconds randomly)
            // HINT: Thread.sleep((long)(Math.random() * 2000));

            // TODO: Step 2 - Try to get help from Controller

            // TODO: Step 3 - Check if got help (true/false)

            // IF GOT HELP (true):
            {
                // TODO: Change state to GETTING_HELP
                // TODO: Print: "Student X is getting help from TA..."

                // TODO: Getting help takes time (sleep 2-4 seconds)

                // TODO: Change state to LEAVING
                // TODO: Print: "Student X got help and is leaving!"

                // TODO: Release the TA back to controller

                // TODO: Take a break before coming back (sleep 5-10 seconds)
            }

            // IF NO HELP (false - no chairs available):
            {
                // TODO: Change state to RETURNING_LATER

                // TODO: Wait before trying again (sleep 3-6 seconds)
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