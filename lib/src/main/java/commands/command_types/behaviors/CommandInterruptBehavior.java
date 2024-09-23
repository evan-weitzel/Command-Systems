package commands.command_types.behaviors;

/**
 * Defines how a Command should act on interrupt
 */
public enum CommandInterruptBehavior {
    /**Finish after the execution of execute() */
    FINISH,
    /**Attempts to end immediately by calling interrupt() on the command's thread */
    END
}
