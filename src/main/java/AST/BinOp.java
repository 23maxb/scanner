package AST;

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
        return switch (operator)
                {
                    case "+" -> (int) val1.evaluate(e) + (int) val2.evaluate(e);
                    case "-" -> (int) val1.evaluate(e) - (int) val2.evaluate(e);
                    case "/" -> (int) val1.evaluate(e) / (int) val2.evaluate(e);
                    case "*" -> (int) val1.evaluate(e) * (int) val2.evaluate(e);
                    case "%", "MOD" -> (int) val1.evaluate(e) % (int) val2.evaluate(e);
                    case ">" -> (int) val1.evaluate(e) > (int) val2.evaluate(e);
                    case "<" -> (int) val1.evaluate(e) < (int) val2.evaluate(e);
                    case ">=" -> (int) val1.evaluate(e) >= (int) val2.evaluate(e);
                    case "<=" -> (int) val1.evaluate(e) <= (int) val2.evaluate(e);
                    case "<>" -> (int) val1.evaluate(e) != (int) val2.evaluate(e);
                    case "==" -> (int) val1.evaluate(e) == (int) val2.evaluate(e);
                    default -> throw new IllegalArgumentException("Operator '" + operator + "' not recognized!");
                };
    }
}
