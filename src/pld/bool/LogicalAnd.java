package pld.bool;

public class LogicalAnd implements BooleanExpression {
    public final BooleanExpression left;
    public final BooleanExpression right;

    public LogicalAnd(BooleanExpression left, BooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "LogicalAnd{" +
            "left=" + left +
            ", right=" + right +
            '}';
    }
}
