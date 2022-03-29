package ast;

import environment.Environment;

/**
 * Supports operations like adding and subtracting in addition to conditional operators
 * like plus and minus
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class BinOp implements Expression
{
    /**
     * Creates a BinOp object that will be used to evaluate something
     *
     * @param val1     The first value of the operation
     * @param val2     the second value of the operation
     * @param operator the operator to use
     */
    public BinOp(Expression val1, Expression val2, String operator)
    {
        this.val1 = val1;
        this.val2 = val2;
        this.operator = operator;
    }

    /**
     * Creates a BinOp object that will be used to evaluate something
     *
     * @param val1     The first value of the operation
     * @param val2     the second value of the operation
     * @param operator the operator to use
     */
    public BinOp(Expression val1, String operator, Expression val2)
    {
        this(val1, val2, operator);
    }

    public Expression val1;
    public Expression val2;
    public String operator;

    /**
     * Evaluates a BinOp using a switch case statement
     *
     * @param e the environment with variable values to use
     * @return the evaluated result
     */
    @Override
    public Object evaluate(Environment e)
    {
        return switch (operator)
                {
                    case "+" -> (int) val1.evaluate(e) + (int) val2.evaluate(e);
                    case "-" -> (int) val1.evaluate(e) - (int) val2.evaluate(e);
                    case "/" -> (int) val1.evaluate(e) / (int) val2.evaluate(e);
                    case "*" -> (((int) val1.evaluate(e)) * (int) (val2.evaluate(e)));
                    case "%", "MOD" -> (int) val1.evaluate(e) % (int) val2.evaluate(e);
                    case ">" -> (int) val1.evaluate(e) > (int) val2.evaluate(e);
                    case "<" -> (int) val1.evaluate(e) < (int) val2.evaluate(e);
                    case ">=" -> (int) val1.evaluate(e) >= (int) val2.evaluate(e);
                    case "<=" -> (int) val1.evaluate(e) <= (int) val2.evaluate(e);
                    case "<>" -> (int) val1.evaluate(e) != (int) val2.evaluate(e);
                    case "==" -> (int) val1.evaluate(e) == (int) val2.evaluate(e);
                    case "&&" -> (boolean) val1.evaluate(e) && (boolean) val2.evaluate(e);
                    case "||" -> (boolean) val1.evaluate(e) || (boolean) val2.evaluate(e);
                    default -> throw new IllegalArgumentException("Operator '" + operator + "' " +
                            "not recognized!");
                };
    }

    /**
     * Returns a string representation of the binary operator.
     *
     * @return a string representation of the binary operator.
     */
    @Override
    public String toString()
    {
        return "BinaryOperator(" + val1 + " " + operator + " " + val2 + ")";
    }
}
