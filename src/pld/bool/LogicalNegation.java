package pld.bool;

public class LogicalNegation implements BooleanExpression {
    public final BooleanExpression expression;

    public LogicalNegation(BooleanExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "LogicalNegation{" +
            "expression=" + expression +
            '}';
    }
}
