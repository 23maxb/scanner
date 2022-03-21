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

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Object value;

    public Assignment(String variableName, Object value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    public void exec(Environment e)
    {
        e.setVariable(variableName, value);
    }
}