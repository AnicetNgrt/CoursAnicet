package CommandPattern;

import java.util.Stack;

public class Target {
    
    private Stack<Command> done = new Stack<Command>();
    private Stack<Command> undone = new Stack<Command>();

    public void executeCommand(Command command) {
        command.setTarget(this);
        command.execute();
        done.push(command);
    }

    public void undo() {
        Command last = done.pop();
        last.undo();
        undone.push(last);
    }

    public void redo() {
        Command last = undone.pop();
        last.execute();
        undone.push(last);
    }
}
