package pld.integer;

public class IntegerValue implements IntegerExpression {
    public final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntegerValue{" +
            "value=" + value +
            '}';
    }
}
