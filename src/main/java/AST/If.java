package AST;

import environment.Environment;

public class If extends Statement
{
    public Expression condition;
    public Block b;

    public If(Expression condition, Block block)
    {
        this.condition = condition;
        b = block;
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
        return "IF(" + condition + " THEN: " + b + ")";
    }
}
