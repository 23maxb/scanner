package ast;

import environment.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProcedureCall implements Statement, Expression
{

    private final String procedureCalled;
    private final Expression[] arguments;

    public ProcedureCall(String procedureCalled, Expression[] arguments)
    {
        this.procedureCalled = procedureCalled;
        this.arguments = arguments;
    }

    public String getProcedureCalled()
    {
        return procedureCalled;
    }

    @Override
    public void exec(@NotNull Environment env)
    {
        if (env.hasProcedure(procedureCalled))
        {
            Environment newEnvironment = env.clone();
            ProcedureDeclaration pro = env.getProcedure(procedureCalled);
            ArrayList<Statement> statements = new ArrayList<>();
            for (int i = 0; i < pro.getParameters().length; i++)
            {
                new Assignment(pro.getParameters()[i].getName(), arguments[i]).exec(newEnvironment);
                pro.getStatement().exec(newEnvironment);
            }
        }
    }

    @Override
    public Object evaluate(Environment e)
    {
        return e.getVariable(procedureCalled);
    }

    @Override
    public String toString()
    {
        return "Procedure( " + procedureCalled + ")";
    }
}
