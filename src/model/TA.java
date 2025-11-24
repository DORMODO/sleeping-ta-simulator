package model;

import controller.Controller;
import model.enums.TAState;

/**
 * TA Thread - Represents a Teaching Assistant
 * <p>
 * YOUR TASK: Implement the TA's behavior in the run() method.
 * <p>
 * TA Lifecycle:
 * SLEEPING  (wait for student)  WORKING  (help student)  SLEEPING (repeat)
 */
/* تحياتي ... زهران للصبح  */

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
            try {
                controller.waitForStudent();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            setState(TAState.WORKING);

            // just to make sure, we already have GUI... 
            System.out.println("TA " + id + " is helping a student...");
            
            try {
                Thread.sleep(2000 + (long)(Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
       
            controller.finishHelping();

            setState(TAState.SLEEPING);

        }
    }

    public TAState getTAState() {
        return state;
    }

    public void setState(TAState newState) {
        this.state = newState;
    }
}
