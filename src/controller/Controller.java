package controller;

import gui.MainGUI;
import model.Student;
import model.TA;
import model.enums.StudentState;

import javax.swing.SwingUtilities;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    // Core synchronization primitives
    private final Semaphore availableTAs;      // How many TAs are free to help
    private final Semaphore availableChairs;   // How many chairs are free
    private final Semaphore studentsWaiting;   // Signal: how many students need help

    // Thread-safe counters for GUI
    private final AtomicInteger workingTAs = new AtomicInteger(0);
    private final AtomicInteger sleepingTAs = new AtomicInteger(0);
    private final AtomicInteger waitingStudents = new AtomicInteger(0);
    private final AtomicInteger studentsLeft = new AtomicInteger(0);

    private final MainGUI gui;

    public Controller(MainGUI gui) {
        this.gui = gui;
        // Start with 0 - values will be set when simulation starts
        this.availableTAs = new Semaphore(0, true);      // true = fair ordering
        this.availableChairs = new Semaphore(0, true);
        this.studentsWaiting = new Semaphore(0, true);
    }

    public void startSimulation(int studentCount, int chairCount, int taCount) {
        // Initialize semaphores with user-provided values
        availableTAs.release(taCount);
        availableChairs.release(chairCount);

        // All TAs start sleeping
        sleepingTAs.set(taCount);
        updateGUI();

        // Create and start TA threads
        TA[] tas = new TA[taCount];
        for (int i = 0; i < taCount; i++) {
            tas[i] = new TA(i + 1, this);  // IDs start from 1 for readability
            tas[i].start();
        }

        // Create and start Student threads
        Student[] students = new Student[studentCount];
        for (int i = 0; i < studentCount; i++) {
            students[i] = new Student(i + 1, this);  // IDs start from 1
            students[i].start();
        }
    }

    // ==================== STUDENT METHODS ====================

    /**
     * Called by a student trying to get help.
     * Flow: Check TA first → Check chairs if busy → Wait or leave
     * Returns true if student got help, false if student left
     */
    public boolean getHelp(Student student) throws InterruptedException {
        // STEP 1: Check if any TA is available (non-blocking)
        if (availableTAs.tryAcquire()) {
            // SUCCESS: A TA is available! Student enters immediately
            student.setState(StudentState.GETTING_HELP);
            updateGUI();
            return true;
        }

        // STEP 2: No TA available - All TAs are busy. Check for available chairs
        if (!availableChairs.tryAcquire()) {
            // No chairs available - student must leave
            studentsLeft.incrementAndGet();
            updateGUI();
            return false;
        }

        // STEP 3: Got a chair! Now sit and wait
        student.setState(StudentState.WAITING);
        waitingStudents.incrementAndGet();
        updateGUI();

        // STEP 4: Signal that a student is waiting (wake up a sleeping TA if any)
        studentsWaiting.release();

        // STEP 5: Wait for a TA to become available
        availableTAs.acquire();  // Blocks until a TA is free

        // STEP 6: Got a TA! Stand up from chair and transition to getting help
        waitingStudents.decrementAndGet();
        availableChairs.release();  // Free the chair for next student
        student.setState(StudentState.GETTING_HELP);
        updateGUI();

        return true;
    }

    /**
     * Called by student when done getting help.
     */
    public void releaseTA() {
        availableTAs.release();  // Make TA available again
    }

    // ==================== TA METHODS ====================

    /**
     * Called by TA to wait for a student to help.
     * TA goes to sleep until a student signals.
     */
    public void waitForStudent() throws InterruptedException {
        // Wait for a student to signal (blocks here)
        // Note: TA is already counted as sleeping from initialization
        studentsWaiting.acquire();

        // Woke up! A student needs help - update state from sleeping to working
        sleepingTAs.decrementAndGet();
        workingTAs.incrementAndGet();
        updateGUI();
    }

    /**
     * Called by TA when finished helping a student.
     */
    public void finishHelping() {
        workingTAs.decrementAndGet();
        updateGUI();
    }

    /**
     * Called by TA when going back to sleep.
     */
    public void taGoingToSleep() {
        sleepingTAs.incrementAndGet();
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