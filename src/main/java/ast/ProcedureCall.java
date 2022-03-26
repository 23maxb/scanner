package ast;

import environment.Environment;

import java.util.ArrayList;

public class ProcedureCall
{

    private final String procedureCalled;

    public ProcedureCall(String procedureCalled)
    {
        this.procedureCalled = procedureCalled;
    }

    @Override
    public void exec(Environment env, Object... args)
    {
        if (env.hasProcedure(procedureCalled))
        {
            Environment newEnvironment = (Environment) env.getAllVars().clone();
            ArrayList<Statement> statements = new ArrayList<>();
            for (int i = 0; i < args.length; i++)
            {
                new Assignment(parameters[i].getName(), args[i]).exec(newEnvironment);
                statement.exec(newEnvironment);
            }*/
        }
    }

    @Override
    public String toString()
    {
        return "Procedure( " + procedureCalled + ")";
    }
}
