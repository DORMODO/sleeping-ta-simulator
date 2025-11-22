package model;

import controller.Controller;
import model.enums.TAState;

/**
 * TA Thread - Represents a Teaching Assistant
 * <p>
 * YOUR TASK: Implement the TA's behavior in the run() method.
 * <p>
 * TA Lifecycle:
 * SLEEPING → (wait for student) → WORKING → (help student) → SLEEPING (repeat)
 */
public class TA extends Thread {
    private int id;
    private TAState state;
    private Controller controller;

    public TA(int id, Controller controller) {
        this.id = id;
        this.controller = controller;
        this.state = TAState.SLEEPING;
    }

    @Override
    public void run() {
        while (true) {

            // TODO: Step 1 - Wait for a student to arrive

            // TODO: Step 2 - Woke up! Change state to WORKING

            // TODO: Print a message: "TA X is helping a student..."

            // TODO: Step 3 - Simulate helping (sleep for 2-4 seconds)
            // HINT: Thread.sleep(2000 + (long)(Math.random() * 2000));

            // TODO: Step 4 - Done helping

            // TODO: Step 5 - Notify controller that TA finished

            // TODO: Step 6 - Go back to SLEEPING state

        }
    }

    public TAState getTAState() {
        return state;
    }

    public void setState(TAState newState) {
        this.state = newState;
    }
}
