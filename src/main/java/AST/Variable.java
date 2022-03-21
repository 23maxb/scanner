package AST;

import environment.Environment;

public class Variable extends Expression
{
    public String name;

    public Variable(String name)
    {
        this.name = name;
    }

    @Override
    public Object evaluate(Environment e)
    {
        return e.getVariable(name);
    }
}
