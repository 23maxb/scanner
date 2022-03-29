package ast;

import environment.Environment;

/**
 * The Expression abstract class
 *
 * @author Max Blennemann
 * @version 2/23/22
 */
public interface Expression
{
    /**
     * Evaluates the Expression
     *
     * @param e the enviornment to pull variable values from
     * @return the evaluated value
     */
    public abstract Object evaluate(Environment e);

    /**
     * Evaluates the Expression
     *
     * @param e the enviornment to pull variable values from
     * @return the evaluated value
     */
    public default Object eval(Environment e)
    {
        return evaluate(e);
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    public abstract String toString();

}
