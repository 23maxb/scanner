package AST;

import environment.Environment;

public class WhileLoop extends Statement
{
    public Expression condition;
    public Block b;

    public WhileLoop(Expression condition, Environment env)
    {
        this.condition = condition;
    }


    @Override
    public void exec(Environment env)
    {
        while ((boolean) condition.evaluate(env))
        {
            b.exec(env);
        }
    }
}
