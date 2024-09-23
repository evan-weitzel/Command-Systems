package commands.command_types.base;

import commands.command_types.behaviors.CommandInterruptBehavior;
import commands.command_types.exceptions.InterruptableThreadException;

/**
 * The Command abstract class is unable to be implemented. Its children must implement the init(), execute(), end(), and endCase() methods.
 * @author Evan Weitzel
 * @author evandane2005@gmail.com
 * @version 1.0
 * @since 1.0
 */
public abstract class Command  {
    private CommandInterruptBehavior interruptBehavior;
    private SleepState sleepState;
    private boolean interrupted;
    private Thread activeThread, effectThread;

    private enum SleepState {
        SLEEPING,
        WAKING,
        AWAKE
    }
    
    /**
     * Unusable Constructor!
     */
    public Command() {}

    /**
     * Called at the start of run()
     */
    public abstract void init();

    /**
     * Repeatedly called until endCase() is true
     */
    public abstract void execute();
    
    /**
     * Called at the end of run();
     */
    public abstract void end();

    /**
     * Determines if the code needs to stop executing
     * 
     * @return Whether the code has met its end condition
     */
    public abstract boolean endCase();

    /**
     * Executes init(), execute(), and end() of a class on a seperate thread
     */
    public void run() {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                init();
                do {
                    execute();
                } while (!isFinished());
                end();
            }
        );
        activeThread.start();
    }

    /**
     * Executes init(), execute(), and end() of a class on a seperate thread
     * 
     * @param timeDelayInSeconds how many seconds between each call of execute
     */
    public void run(double timeDelayInSeconds) {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                init();
                try {
                    do {
                        execute();
                        Thread.sleep((long) timeDelayInSeconds * 1000);
                    } while (!isFinished());
                    end();
                } catch (InterruptedException e) {}
            }
        );
        activeThread.start();
    }

    /**
     * Executes init(), execute(), and end() of a class on a seperate thread
     * 
     * @param initialDelay the time in seconds before the initialize call
     * @param timeDelayInSeconds the time in seconds between each call of execute
     */
    public void run(double initialDelay, double timeDelayInSeconds) {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                try {
                    Thread.sleep((long) initialDelay * 1000);
                    init();
                    do {
                        execute();
                        Thread.sleep((long) timeDelayInSeconds * 1000);
                    } while (!isFinished());
                    end();
                } catch (InterruptedException e) {}
            }
        );
        activeThread.start();
    }

    /**
     * Executes init(), execute(), and end() of a class on the main thread
     */
    public void runOnMain() {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                init();
                do {
                    execute();
                } while (!isFinished());
                end();
            }
        );
        activeThread.start();
        try {
            activeThread.join();
        } catch (InterruptedException e) {}
    }
    
    /**
     * Executes init(), execute(), and end() of a class on the main thread
     * 
     * @param timeDelayInSeconds how many seconds between each call of execute
     */
    public void runOnMain(double timeDelayInSeconds) {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                init();
                try {
                    do {
                        execute();
                        Thread.sleep((long) timeDelayInSeconds * 1000);
                    } while (!isFinished());
                    end();
                } catch (InterruptedException e) {}
            }
        );
        activeThread.start();
        try {
            activeThread.join();
        } catch (InterruptedException e) {}
    }

    /**
     * Executes init(), execute(), and end() of a class on the main thread
     * 
     * @param initialDelay the time in seconds before the initialize call
     * @param timeDelayInSeconds the time in seconds between each call of execute
     */
    public void runOnMain(double initialDelay, double timeDelayInSeconds) {
        sleepState = SleepState.WAKING;
        activeThread = new Thread(() -> {
                sleepState = SleepState.AWAKE;
                try {
                    Thread.sleep((long) initialDelay * 1000);
                    init();
                    do {
                        execute();
                        Thread.sleep((long) timeDelayInSeconds * 1000);
                    } while (!isFinished());
                    end();
                } catch (InterruptedException e) {}
            }
        );
        activeThread.start();
        try {
            activeThread.join();
        } catch (InterruptedException e) {}
    }
    
    /**
     * Stops the current thread (ends Main if called when running on Main) based on what the interrupt behavior of the Command is.
     * @throws InterruptableThreadException may interrupt a thread while it is asleep
     */
    public void interrupt() throws InterruptableThreadException {
        if (sleepState != SleepState.SLEEPING) {
            while (sleepState != SleepState.AWAKE) {}
            effectThread = new Thread(() -> {
                if (this.interruptBehavior == CommandInterruptBehavior.END) {
                    interrupted = true;
                    System.out.println("Interrupted");
                    activeThread.interrupt();
                } else {
                    System.out.println("Will end after execute() finishes");
                    interrupted = true;
                }
            });
            effectThread.start();
        } else {throw new InterruptableThreadException();}
    }


    private boolean isFinished() {
        if (interrupted) {
            return true;
        } else {
            return endCase();
        }
    }

    /**
     * Gets current InterruptBehavior of the Command
     * 
     * @return The Command's Interrupt Behavior
     */
    public CommandInterruptBehavior getInterruptBehavior() {
        return this.interruptBehavior;
    }

    /**
     * Sets current InterruptBehavior of the Command
     * @param ib the interrupt behavioir you want when you interrupt the command
     */
    public void setInterruptBehavioir(CommandInterruptBehavior ib) {
        this.interruptBehavior = ib;
    }
}