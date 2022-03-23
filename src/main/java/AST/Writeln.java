package AST;

import environment.Environment;

public class Writeln extends Statement
{
    private Expression exp;

    public Writeln(Expression exp)
    {
        this.exp = exp;
    }


    @Override
    public void exec(Environment env)
    {
        exp.evaluate(env);
    }

    @Override
    public String toString()
    {
        return "WRITELN(" + exp.toString() + ")";
    }


}





