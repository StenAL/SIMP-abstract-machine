package pld.bool;

public class BooleanValue implements BooleanExpression {
    public final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
            "value=" + value +
            '}';
    }
}
