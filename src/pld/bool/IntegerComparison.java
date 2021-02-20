package pld.bool;

import pld.integer.IntegerExpression;

public class IntegerComparison implements BooleanExpression {
    public final ComparisonOperator comparisonOperator;
    public final IntegerExpression left;
    public final IntegerExpression right;

    public IntegerComparison(IntegerExpression left, IntegerExpression right, ComparisonOperator comparisonOperator) {
        this.left = left;
        this.right = right;
        this.comparisonOperator = comparisonOperator;
    }

    @Override
    public String toString() {
        return "IntegerComparison{" +
            "left=" + left +
            ", right=" + right +
            ", operator=" + comparisonOperator +
            '}';
    }
}
