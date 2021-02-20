package pld.command;

import pld.bool.BooleanExpression;

public class While implements Command {
    public final BooleanExpression condition;
    public final Command command;

    public While(BooleanExpression condition, Command command) {
        this.condition = condition;
        this.command = command;
    }

    @Override
    public String toString() {
        return "While{" +
            "condition=" + condition +
            ", command=" + command +
            '}';
    }
}
