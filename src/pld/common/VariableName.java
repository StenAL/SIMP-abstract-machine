package pld.common;

public class VariableName implements ResultsStackable {
    public final String name;

    public VariableName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VariableName{" +
            "name='" + name + '\'' +
            '}';
    }
}
