package AST;

import environment.Environment;

public class If extends Statement
{
    public Expression condition;
    public Block b;

    public If(Expression condition)
    {
        this.condition = condition;
    }


    @Override
    public void exec(Environment env)
    {
        if ((boolean) condition.evaluate(env))
        {
            b.exec(env);
        }
    }

    @Override
    public String toString()
    {
        return null;
    }
}
