package AST;

import environment.Environment;

public class BinOp extends Expression
{
    public BinOp(Expression val1, Expression val2, String operator)
    {
        this.val1 = val1;
        this.val2 = val2;
        this.operator = operator;
    }

    public BinOp(Expression val1, String operator, Expression val2)
    {
        this(val1, val2, operator);
    }

    public Expression val1;
    public Expression val2;
    public String operator;

    @Override
    public Object evaluate(Environment e)
    {
        System.out.println(this);
        Object o;
        switch (operator)
        {
            case "+":
                System.out.println("a");
                o = (int) val1.evaluate(e) + (int) val2.evaluate(e);
                break;
            case "-":
                o = (int) val1.evaluate(e) - (int) val2.evaluate(e);
                break;
            case "/":
                o = (int) val1.evaluate(e) / (int) val2.evaluate(e);
                break;
            case "*":
                System.out.println(val1.evaluate(e));
                System.out.println(val2.evaluate(e));
                System.out.println(e);
                o = (((int) val1.evaluate(e)) * (int) (val2.evaluate(e)));
                break;
            case "%":
            case "MOD":
                o = (int) val1.evaluate(e) % (int) val2.evaluate(e);
                break;
            case ">":
                o = (int) val1.evaluate(e) > (int) val2.evaluate(e);
                break;
            case "<":
                o = (int) val1.evaluate(e) < (int) val2.evaluate(e);
                break;
            case ">=":
                o = (int) val1.evaluate(e) >= (int) val2.evaluate(e);
                break;
            case "<=":
                o = (int) val1.evaluate(e) <= (int) val2.evaluate(e);
                break;
            case "<>":
                o = (int) val1.evaluate(e) != (int) val2.evaluate(e);
                break;
            case "==":
                o = (int) val1.evaluate(e) == (int) val2.evaluate(e);
                break;
            case "&&":
                o = (boolean) val1.evaluate(e) && (boolean) val2.evaluate(e);
                break;
            case "||":
                o = (boolean) val1.evaluate(e) || (boolean) val2.evaluate(e);
                break;
            default:
                throw new IllegalArgumentException("Operator '" + operator + "' " +
                        "not recognized!");
        }
        return o;
    }

    @Override
    public String toString()
    {
        return "BinaryOperator(" + val1 + " " + operator + " " + val2 + ")";
    }
}
