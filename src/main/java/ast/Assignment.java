package ast;

import environment.Environment;

/**
 * Assignment assigns a variable to an environment.
 *
 * @author Max Blennmenan
 * @version 3/23/22
 */
public class Assignment implements Statement
{
    public String variableName;

    /**
     * Gets the name of the variable.
     *
     * @return the name of the variable
     */
    public String getVariableName()
    {
        return variableName;
    }

    /**
     * Sets the variable name
     *
     * @param variableName the name of the variable
     */
    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }

    /**
     * Gets the value of the variable.
     *
     * @return the value of the variable in terms of other ast classes.
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Sets the value of the variable.
     *
     * @param value sets the value of the variable
     */
    public void setValue(Expression value)
    {
        this.value = value;
    }

    public Expression value;

    /**
     * Creates an asisgnment expression that allows a new entry into the hashmap in the environment
     *
     * @param variableName the name of the variable to assign
     * @param value        the value of the variable as an expression
     */
    public Assignment(String variableName, Expression value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    /**
     * Adds a new entry to the given environment's has map.
     *
     * @param e the environment that is used to store the variables
     */
    @Override
    public void exec(Environment e)
    {
        e.setVariable(variableName, value.evaluate(e));
    }

    /**
     * Represents the assignment object as a string
     *
     * @return a string representation of the assignment object
     */
    @Override
    public String toString()
    {
        return "Assignment(" + variableName + " to " + value.toString() + ")";
    }
}