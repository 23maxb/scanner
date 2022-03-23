package ast;

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
        if (e.hasVariable(name))
            return e.getVariable(name);
        throw new IllegalArgumentException("The variable '" + name + "' has not been declared.");
    }

    @Override
    public String toString()
    {
        return "Variable(" + name + ")";
    }


}
