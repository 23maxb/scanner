package ast;

import environment.Environment;

public class Number extends Expression
{
    public int number;

    public Number(int number)
    {
        this.number = number;
    }


    @Override
    public Object evaluate(Environment e)
    {
        return number;
    }

    @Override
    public String toString()
    {
        return "Number(" + number + ")";
    }
}
