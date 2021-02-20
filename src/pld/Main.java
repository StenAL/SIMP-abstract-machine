package pld;

import pld.bool.*;
import pld.command.*;
import pld.common.*;
import pld.integer.*;

public class Main {
    public static void main(String[] args) {
        IntegerExpression expr1 = new IntegerOperation(new IntegerValue(3), new IntegerValue(5), IntegerOperator.MINUS); // 3 - 5
        IntegerExpression expr2 = new IntegerOperation(new IntegerValue(7), new IntegerValue(2), IntegerOperator.MULT); // 7 * 2
        BooleanExpression expr3 = new IntegerComparison(expr1, expr2, ComparisonOperator.GREATER_THAN); // 3 - 5 > 7 * 2
        BooleanExpression expr4 = new LogicalNegation(expr3); // !(3 - 5 > 7 * 2)
        Evaluator evaluator = new Evaluator(expr4);

        int x = 5;
        int n = 5;
        evaluator = new Evaluator(pow(x, n));
        evaluator.printConfiguration();
        System.out.println();

        while (evaluator.controlStack.size() > 0) {
            evaluator.step();
            System.out.println();
        }
    }

    public static Program pow(int x, int n) {
        if (n < 0) {
            return new Skip();
        }

        Command initializeResult = new VariableAssignment(new VariableName("result"), new IntegerValue(1));
        Command initializeX = new VariableAssignment(new VariableName("x"), new IntegerValue(x));
        Command initializeN = new VariableAssignment(new VariableName("n"), new IntegerValue(n));
        Command initialization = new Chain(new Chain(initializeResult, initializeX), initializeN);

        BooleanExpression condition = new IntegerComparison(new VariableRead(new VariableName("n")), new IntegerValue(0), ComparisonOperator.GREATER_THAN);
        Command multiplyResult = new VariableAssignment(new VariableName("result"), new IntegerOperation(new VariableRead(new VariableName("result")), new VariableRead(new VariableName("x")), IntegerOperator.MULT));
        Command decrementN = new VariableAssignment(new VariableName("n"), new IntegerOperation(new VariableRead(new VariableName("n")), new IntegerValue(1), IntegerOperator.MINUS));
        Command whileLoop = new While(condition, new Chain(multiplyResult, decrementN));

        return new Chain(initialization, whileLoop);
    }
}
