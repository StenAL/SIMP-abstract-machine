package pld.command;

public class Chain implements Command {
    public final Command left;
    public final Command right;

    public Chain(Command left, Command right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Chain{" +
            "left=" + left +
            ", right=" + right +
            '}';
    }
}
