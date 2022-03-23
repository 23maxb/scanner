package AST;

import environment.Environment;

public class WhileLoop extends Statement
{
    public Expression condition;
    public Block b;

    public WhileLoop(Expression condition, Block block)
    {
        this.condition = condition;
        b = block;
    }


    @Override
    public void exec(Environment env)
    {
        while ((boolean) condition.evaluate(env))
        {
            b.exec(env);
        }
    }

    @Override
    public String toString()
    {
        return "WHILE(" + condition + " DO: " + b.toString() + ")";
    }

}
