package AST;

public class Number extends Expression
{
    public int number;

    @Override
    public Object evaluate(Environment e)
    {
        return number;
    }
}
