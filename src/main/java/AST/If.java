package AST;

public class If extends Statement
{
    public Expression condition;
    public Block b;

    public If(Expression condition, Environment env)
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
}
