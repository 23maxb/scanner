package AST;

import environment.Environment;

public class Assignment extends Statement
{
    public String variableName;

    public String getVariableName()
    {
        return variableName;
    }

    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Expression value)
    {
        this.value = value;
    }

    public Expression value;

    public Assignment(String variableName, Expression value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    public void exec(Environment e)
    {
        e.setVariable(variableName, value.evaluate(e));
    }

    @Override
    public String toString()
    {
        return "Assignment(" + variableName + " to " + value.toString() + ")";
    }
}