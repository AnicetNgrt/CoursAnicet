package CommandPattern;

public abstract class Command {

    protected Target target = null;
    protected boolean executed = false;

    public abstract void execute();

    public abstract void undo();

	public void setTarget(Target target) {
        this.target = target;
	}
}
