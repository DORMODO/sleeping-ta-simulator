package controller;

import gui.MainGUI;
import model.Student;
import model.TA;

import javax.swing.SwingUtilities;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    private final Semaphore availableTAs;
    private final Semaphore availableChairs;
    private final Semaphore studentsWaiting;

    // Counters for GUI
    private final AtomicInteger workingTAs = new AtomicInteger(0);
    private final AtomicInteger sleepingTAs = new AtomicInteger(0);
    private final AtomicInteger waitingStudents = new AtomicInteger(0);
    private final AtomicInteger studentsLeft = new AtomicInteger(0);

    private MainGUI gui;
    private TA[] tas;
    private Student[] students;

    public Controller(MainGUI gui) {
        this.gui = gui;
        this.availableTAs = new Semaphore(0, true);
        this.availableChairs = new Semaphore(0, true);
        this.studentsWaiting = new Semaphore(0, true);
    }

    public void startSimulation(int studentCount, int chairCount, int taCount) {
        // TODO: Set the semaphore permits based on user input

        // TODO: Initialize the sleeping TAs counter

        // Create and start TA threads
        tas = new TA[taCount];
        for (int i = 0; i < taCount; i++) {
            tas[i] = new TA(i + 1, this);
            tas[i].start();
        }

        // Create and start Student threads
        students = new Student[studentCount];
        for (int i = 0; i < studentCount; i++) {
            students[i] = new Student(i + 1, this);
            students[i].start();
        }
    }

    // ==================== STUDENT METHODS ====================

    /**
     * Called by a student trying to get help.
     * <p>
     * LOGIC TO IMPLEMENT:
     * 1. Try to get a chair (non-blocking) - if no chairs, student must leave
     * 2. If got a chair, increment waiting counter and update GUI
     * 3. Signal a TA that a student is waiting
     * 4. Wait (blocking) for a TA to become available
     * 5. Once TA is available, decrement waiting counter and free the chair
     *
     * @return true if student got help, false if no chairs available
     */
    public boolean getHelp(Student student) throws InterruptedException {
        // TODO
        return true;
    }

    /**
     * Called by student when done getting help.
     */
    public void releaseTA() {
        // TODO: Make the TA available again
    }

    // ==================== TA METHODS ====================

    /**
     * Called by TA to wait for a student to help.
     * TA goes to sleep until a student signals.
     *
     * LOGIC TO IMPLEMENT:
     * 1. TA is going to sleep - update counters
     * 2. Wait (blocking) for a student to signal
     * 3. TA woke up - update counters (now working)
     */
    public void waitForStudent() throws InterruptedException {
        // TODO
    }

    /**
     * Called by TA when finished helping a student.
     */
    public void finishHelping() {
        // TODO: Update the working TAs counter
        updateGUI();
    }

    // ==================== GUI UPDATE ====================

    private void updateGUI() {
        SwingUtilities.invokeLater(() -> {
            gui.lblWorking.setText("TAs Working: " + workingTAs.get());
            gui.lblSleeping.setText("TAs Sleeping: " + sleepingTAs.get());
            gui.lblWaiting.setText("Students Waiting: " + waitingStudents.get());
            gui.lblLeft.setText("Students that will come later: " + studentsLeft.get());
        });
    }
}