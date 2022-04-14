package ast;

import environment.Environment;

/**
 * A Writeln object creates an object that is capable of printing something.
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class Writeln implements Statement
{
    private final Expression exp;

    /**
     * Creates the writeln object.
     *
     * @param exp the expression to be printed when execute is called
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Prints out the given expression
     *
     * @param env the environment to pull variables from
     */
    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.evaluate(env));
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "WRITELN(" + exp.toString() + ")";
    }
}





