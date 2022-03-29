package ast;

import environment.Environment;

/**
 * Represents a statement in pascal.
 *
 * @author Max Blennemann
 * @version 3/23/22
 *
 */
public interface Statement
{
    /**
     * Executes the statement and does whatever operation is needed.
     *
     * @param env
     */
    public abstract void exec(Environment env);

    /**
     * Returns a string representation of the object
     * @return a string representation of the object
     */
    @Override
    public abstract String toString();
}
