package pld.common;

import pld.bool.*;
import pld.command.*;
import pld.integer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator {
    public final List<ControlStackable> controlStack;
    public final List<ResultsStackable> resultStack;
    public final Map<String, IntegerValue> memory;

    public Evaluator(Program p) {
        controlStack = new ArrayList<>();
        resultStack = new ArrayList<>();
        memory = new HashMap<>();

        controlStack.add(p);
    }

    public void run() {
        while (!controlStack.isEmpty()) {
            step();
        }
    }

    public void step() {
        if (controlStack.isEmpty()) {
            return; // done
        }

        ControlStackable top = controlStack.remove(controlStack.size() - 1);
        if (top instanceof IntegerExpression || top instanceof IntegerOperator) {
            processInteger(top);
        } else if (top instanceof BooleanExpression || top instanceof ComparisonOperator || top instanceof LogicalOperator) {
            processBoolean(top);
        } else if (top instanceof Command || top instanceof CommandToken) {
            processCommand(top);
        } else {
            throw new RuntimeException("Invalid call");
        }

        printConfiguration();
    }

    private void processInteger(ControlStackable top) {
        if (top instanceof IntegerOperation) { // transition 2
            IntegerOperation integerOperation = (IntegerOperation) top;
            IntegerExpression left = integerOperation.left;
            IntegerExpression right = integerOperation.right;
            IntegerOperator integerOperator = integerOperation.integerOperator;

            controlStack.add(integerOperator);
            controlStack.add(right);
            controlStack.add(left);

        } else if (top instanceof IntegerValue) { // transition 1
            IntegerValue integerValue = (IntegerValue) top;
            resultStack.add(integerValue);
        } else if (top instanceof VariableRead) { // transition 4
            VariableRead variableRead = (VariableRead) top;

            IntegerValue value = memory.get(variableRead.name.name);
            resultStack.add(value);

        } else if (top instanceof IntegerOperator) { // transition 3
            IntegerOperator integerOperator = (IntegerOperator) top;

            int n2 = ((IntegerValue) resultStack.remove(resultStack.size() - 1)).value;
            int n1 = ((IntegerValue) resultStack.remove(resultStack.size() - 1)).value;
            int result = 0;
            switch (integerOperator) {
                case PLUS:
                    result = n1 + n2;
                    break;
                case MINUS:
                    result = n1 - n2;
                    break;
                case MULT:
                    result = n1 * n2;
                    break;
                case DIV:
                    result = n1 / n2;
                    break;
            }
            resultStack.add(new IntegerValue(result));
        } else {
            throw new RuntimeException("Invalid call");
        }
    }

    private void processBoolean(ControlStackable top) {
        if (top instanceof BooleanValue) {
            BooleanValue booleanValue = (BooleanValue) top;
            resultStack.add(booleanValue);
        } else if (top instanceof LogicalNegation) {
            LogicalNegation logicalNegation = (LogicalNegation) top;
            controlStack.add(LogicalOperator.NEGATION);
            controlStack.add(logicalNegation.expression);
        } else if (top instanceof LogicalAnd) {
            LogicalAnd logicalAnd = (LogicalAnd) top;
            controlStack.add(LogicalOperator.AND);
            controlStack.add(logicalAnd.right);
            controlStack.add(logicalAnd.left);
        } else if (top instanceof LogicalOperator) {
            if (top == LogicalOperator.NEGATION) {
                BooleanValue resultStackTop = (BooleanValue) resultStack.remove(resultStack.size() - 1);
                if (resultStackTop.value) {
                    resultStack.add(new BooleanValue(false));
                } else {
                    resultStack.add(new BooleanValue(true));
                }
            } else if (top == LogicalOperator.AND) {
                BooleanValue b2 = (BooleanValue) resultStack.remove(resultStack.size() - 1);
                BooleanValue b1 = (BooleanValue) resultStack.remove(resultStack.size() - 1);
                resultStack.add(new BooleanValue(b2.value && b1.value));
            }
        } else if (top instanceof IntegerComparison) {
            IntegerComparison integerComparison = (IntegerComparison) top;
            controlStack.add(integerComparison.comparisonOperator);
            controlStack.add(integerComparison.right);
            controlStack.add(integerComparison.left);
        } else if (top instanceof ComparisonOperator) {
            ComparisonOperator comparisonOperator = (ComparisonOperator) top;
            IntegerValue n2 = (IntegerValue) resultStack.remove(resultStack.size() - 1);
            IntegerValue n1 = (IntegerValue) resultStack.remove(resultStack.size() - 1);
            boolean result = false;
            switch (comparisonOperator) {
                case EQUAL_TO:
                    result = n1.value == n2.value;
                    break;
                case GREATER_THAN:
                    result = n1.value > n2.value;
                    break;
                case LESS_THAN:
                    result = n1.value < n2.value;
                    break;
            }
            resultStack.add(new BooleanValue(result));
        } else {
            throw new RuntimeException("Invalid call");
        }
    }

    private void processCommand(ControlStackable top) {
        if (top instanceof Skip) {
            // skip
        } else if (top instanceof VariableAssignment) {
            VariableAssignment variableAssignment = (VariableAssignment) top;
            controlStack.add(CommandToken.ASSIGNMENT);
            controlStack.add(variableAssignment.value);
            resultStack.add(variableAssignment.name);
        } else if (top instanceof CommandToken) {
            if (top == CommandToken.ASSIGNMENT) {
                IntegerValue integerValue = (IntegerValue) resultStack.remove(resultStack.size() - 1);
                VariableName variableName = (VariableName) resultStack.remove(resultStack.size() - 1);
                memory.put(variableName.name, integerValue);
            } else if (top == CommandToken.IF) {
                BooleanValue booleanValue = (BooleanValue) resultStack.remove(resultStack.size() - 1);
                Command c1 = (Command) resultStack.remove(resultStack.size() - 1);
                Command c2 = (Command) resultStack.remove(resultStack.size() - 1);
                if (booleanValue.value) {
                    controlStack.add(c1);
                } else {
                    controlStack.add(c2);
                }
            } else if (top == CommandToken.WHILE) {
                BooleanValue booleanValue = (BooleanValue) resultStack.remove(resultStack.size() - 1);
                BooleanExpression b = (BooleanExpression) resultStack.remove(resultStack.size() - 1);
                Command c = (Command) resultStack.remove(resultStack.size() - 1);
                if (booleanValue.value) {
                    controlStack.add(new While(b, c));
                    controlStack.add(c);
                } else {
                    // while loop is done, do nothing
                }
            }
        } else if (top instanceof Chain) {
            Chain chain = (Chain) top;
            controlStack.add(chain.right);
            controlStack.add(chain.left);
        } else if (top instanceof IfThenElse) {
            IfThenElse ifThenElse = (IfThenElse) top;
            controlStack.add(CommandToken.IF);
            controlStack.add(ifThenElse.condition);
            resultStack.add(ifThenElse.elseCommand);
            resultStack.add(ifThenElse.thenCommand);
        } else if (top instanceof While) {
            While whileCommand = (While) top;
            controlStack.add(CommandToken.WHILE);
            controlStack.add(whileCommand.condition);
            resultStack.add(whileCommand.command);
            resultStack.add(whileCommand.condition);
        }
    }

    public void printConfiguration() {
        System.out.println("Control stack: " + controlStack);
        System.out.println("Result stack: " + resultStack);
        System.out.println("Memory: " + memory);

    }
}
