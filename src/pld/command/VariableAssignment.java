package pld.command;

import pld.common.VariableName;
import pld.integer.IntegerExpression;

public class VariableAssignment implements Command {
    public final VariableName name;
    public final IntegerExpression value;

    public VariableAssignment(VariableName name, IntegerExpression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "VariableAssignment{" +
            "name='" + name + '\'' +
            ", value=" + value +
            '}';
    }
}
