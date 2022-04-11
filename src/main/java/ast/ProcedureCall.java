package ast;

import environment.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

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
        Environment newEnvironment;
        if (env.hasProcedure(procedureCalled))
        {
            newEnvironment = env.global().clone();
            ProcedureDeclaration pro = env.getProcedure(procedureCalled);
            for (int i = 0; i < pro.getParameters().length; i++)
            {
                new Assignment(pro.getParameters()[i].getName(), arguments[i]).exec(newEnvironment);
            }
            pro.getStatement().exec(newEnvironment);
        }
        else
            throw new IllegalArgumentException("Procedure '" + procedureCalled + "' not found");
        for (Map.Entry<String, Object> entry : newEnvironment.getAllVars().entrySet())
            if (env.hasVariable(entry.getKey()) && !env.getProcedure(procedureCalled).hasParameter(entry.getKey()))
            {
                env.setVariable(entry.getKey(), entry.getValue());
            }
    }

    @Override
    public Object evaluate(Environment e)
    {
        exec(e);
        return e.getVariable(procedureCalled);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(arguments).forEachOrdered(argument ->
        {
            sb.append(argument.toString());
            sb.append(", ");
        });
        if (arguments.length > 0)
            sb.delete(sb.length() - 2, sb.length());
        return "Procedure(" + procedureCalled + "(" + sb + ")" + ")";
    }
}
