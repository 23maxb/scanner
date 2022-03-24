package ast;

import environment.Environment;

/**
 * Creates a statement that can be executed if a condition is true
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class If extends Statement
{
    public Expression condition;
    public Block b;

    /**
     * Creates a statement that can be executed if a condition is true
     *
     * @param condition the condition to check
     * @param block     the block to run if the condition is true
     */
    public If(Expression condition, Block block)
    {
        this.condition = condition;
        b = block;
    }

    /**
     * Executes the value with the environment given in the parameter.
     *
     * @param env the enviornment to pull variables from
     */
    @Override
    public void exec(Environment env)
    {
        if ((boolean) condition.evaluate(env))
        {
            b.exec(env);
        }
    }

    /**
     * Returns a String representation of the object
     *
     * @return a String representation of the object
     */
    @Override
    public String toString()
    {
        return "IF(" + condition + " THEN: " + b + ")";
    }
}
