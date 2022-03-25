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
     * @param env        the enviornment to be run in
     * @param parameters the parameters that are required for this method to run
     */
    public ProcedureDeclaration(Statement statement, String name, Environment env,
                                Variable[] parameters)
    {
        this.statement = statement;
        this.name = name;
        this.env = env;
        this.parameters = parameters;
    }

    public ProcedureDeclaration(String name, Statement statement, Environment env,
                                Variable[] parameters)
    {
        this(statement, name, env, parameters);
    }

    public Statement getStatement()
    {
        return statement;
    }

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
}
