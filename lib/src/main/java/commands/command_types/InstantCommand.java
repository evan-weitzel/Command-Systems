package commands.command_types;

import com.google.common.base.Supplier;

import commands.command_types.base.Command;

/**
 * Allows you to create a command using lambda expressions for init(), execute(), and/or end() and declaring how many times you want it to execute. 
 * Ideal for commands where instance variables aren't necessary
 * @author Evan Weitzel
 * @author evandane2005@gmail.com
 * @version 1.0
 * @since 1.0
 */
public class InstantCommand extends Command {
    private Runnable init, execute, end;
    private final Supplier<Boolean> endCase;
    private int i = 0;

    public static void main(String[] args) {
        
    }

    /**
     * Creates an InstantCommand using runnable expressions
     * @param execute Runnable being called
     * @param executions The number of times execute should be called
     */
    public InstantCommand(Runnable execute, int executions) {
        this.init = ()->{};
        this.execute = execute;
        this.end = ()->{};
        this.endCase = ()->{
            if (i >= executions - 1) {
                return true;
            } else {
                i++;
                return false;
            }
        };
    }

    /**
     * Creates an InstantCommand using runnable expressions
     * @param execute Runnable being called
     * @param end Runnable called after execute has finished all its executions
     * @param executions The number of times execute should be called
     */
    public InstantCommand(Runnable execute, Runnable end, int executions) {
        this.init = ()->{};
        this.execute = execute;
        this.end = end;
        this.endCase = ()->{
            if (i >= executions - 1) {
                return true;
            } else {
                i++;
                return false;
            }
        };
    }
    
    /**
     * Creates an InstantCommand using runnable expressions
     * @param init Runnable called before the first call of execute
     * @param execute Runnable being called
     * @param end Runnable called after execute has finished all its executions
     * @param executions The number of times execute should be called
     */
    public InstantCommand(Runnable init, Runnable execute, Runnable end, int executions) {
        this.init = init;
        this.execute = execute;
        this.end = end;
        this.endCase = ()->{
            if (i >= executions - 1) {
                return true;
            } else {
                i++;
                return false;
            }
        };
    }


    @Override
    public void init() {
        init.run();
    }

    @Override
    public void execute() {
        execute.run();
    }

    @Override
    public void end() {
        end.run();
    }

    @Override
    public boolean endCase() {
        return endCase.get();
    }
}
