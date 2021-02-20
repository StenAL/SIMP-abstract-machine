package pld.integer;

public class IntegerOperation implements IntegerExpression {
    public final IntegerOperator integerOperator;
    public final IntegerExpression left;
    public final IntegerExpression right;

    public IntegerOperation(IntegerExpression left, IntegerExpression right, IntegerOperator integerOperator) {
        this.left = left;
        this.right = right;
        this.integerOperator = integerOperator;
    }

    @Override
    public String toString() {
        return "IntegerOperation{" +
            "left=" + left +
            ", right=" + right +
            ", operator=" + integerOperator +
            '}';
    }
}
