package commands.command_types.exceptions;

/**
 * Signals that a thread tied to Command has been interrupted during a point where it is asleep
 */
public class InterruptableThreadException extends Exception {

    /**
     * Creates an InterrutableThreadException object
     */
    public InterruptableThreadException() {
        super();
    }
}
