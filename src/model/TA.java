package model;

import controller.Controller;
import model.enums.TAState;

public class TA extends Thread {
    private final int id;
    private TAState state;
    private final Controller controller;

    public TA(int id, Controller controller) {
        this.id = id;
        this.controller = controller;
        this.state = TAState.SLEEPING;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // STEP 1: Wait for a student (TA is sleeping here)
                controller.waitForStudent();

                // STEP 2: Woke up! Now helping a student
                setState(TAState.WORKING);
                System.out.println("TA " + id + " is helping a student...");

                // STEP 3: Help takes time (2-4 seconds)
                Thread.sleep(2000 + (long)(Math.random() * 2000));

                // STEP 4: Done helping
                System.out.println("TA " + id + " finished helping student.");
                controller.finishHelping();

                // STEP 5: Back to sleeping (loop continues)
                setState(TAState.SLEEPING);
                controller.taGoingToSleep();
            }
        } catch (InterruptedException e) {
            System.out.println("TA " + id + " thread interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    public void setState(TAState newState) {
        this.state = newState;
    }
}