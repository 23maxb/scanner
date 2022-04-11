package ast;

import environment.Environment;

/**
 * The Program class is the root of the AST. It contains a list of
 * declarations and a list of statements.
 *
 * @author Max Blennemann
 * @version 3/31/22
 */
public class Program implements Statement
{
    public Statement toRun;
    public Environment globalEnvironment;


    /**
     * Constructor for a Program
     *
     * @param s the program to run
     * @param e the environment to use
     */
    public Program(Statement s, Environment e)
    {
        toRun = s;
        globalEnvironment = e;
    }

    /**
     * Executes the program
     *
     * @param e the environment to use
     */
    @Override
    public void exec(Environment e)
    {
        System.out.println(toRun);
        e.setGlobalEnvironment(globalEnvironment);
        toRun.exec(e.global());
    }

    /**
     * Returns the string representation of the program
     *
     * @return the string representation of the program
     */
    @Override
    public String toString()
    {
        return toRun.toString();
    }
}