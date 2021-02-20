package pld.command;

import pld.bool.BooleanExpression;

public class IfThenElse implements Command {
    public final BooleanExpression condition;
    public final Command thenCommand;
    public final Command elseCommand;

    public IfThenElse(BooleanExpression condition, Command thenCommand, Command elseCommand) {
        this.condition = condition;
        this.thenCommand = thenCommand;
        this.elseCommand = elseCommand;
    }

    @Override
    public String toString() {
        return "IfThenElse{" +
            "condition=" + condition +
            ", thenCommand=" + thenCommand +
            ", elseCommand=" + elseCommand +
            '}';
    }
}
