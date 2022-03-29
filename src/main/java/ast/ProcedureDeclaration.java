package ast;

import environment.Environment;

import java.util.ArrayList;

/**
 * Represents an abstract syntax tree in a pascal program.
 *
 * @author Max Blennemann
 * @author 3/25/22
 */
public class ProcedureDeclaration
{
    public Statement statement;
    public String name;
    public Environment env;
    public Variable[] parameters;

    /**
     * Declares a procedure.
     *
     * @param statement  the statement(s) to run if the program is called
     * @param name       the name of the method
     * @param parameters the parameters that are required for this method to run
     */
    public ProcedureDeclaration(Statement statement, String name,
                                Variable[] parameters)
    {
        this.statement = statement;
        this.name = name;
        this.parameters = parameters;
        env = new Environment();
    }

    /**
     * Creates a procedure declaration object that creates a method that can be referenced later
     *
     * @param name       Method name
     * @param statement  the statement to run usually a block
     * @param parameters the parameters that are required for this method to run
     */
    public ProcedureDeclaration(String name, Statement statement,
                                Variable[] parameters)
    {
        this(statement, name, parameters);
    }

    /**
     * Gets the statement
     *
     * @return the statement to be run when this procedure is called
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Sets a new procedure to be run instead
     *
     * @param statement the new statement to be run
     */
    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Environment getEnv()
    {
        return env;
    }

    public void setEnv(Environment env)
    {
        this.env = env;
    }

    public Variable[] getParameters()
    {
        return parameters;
    }

    public void setParameters(Variable[] parameters)
    {
        this.parameters = parameters;
    }

    @Override
    public String toString()
    {
        return "Method: (" + name + "," + statement + ")";
    }

    public void exec(Environment currentEnvironment)
    {
        currentEnvironment.addProcedure(name, this);

    }
}
