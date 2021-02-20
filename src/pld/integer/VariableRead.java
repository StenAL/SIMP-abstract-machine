package pld.integer;


import pld.common.VariableName;

public class VariableRead implements IntegerExpression {
    public final VariableName name;

    public VariableRead(VariableName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VariableRead{" +
            "name='" + name + '\'' +
            '}';
    }
}
