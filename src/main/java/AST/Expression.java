package AST;

import environment.Environment;

public abstract class Expression
{
    public abstract Object evaluate(Environment e);

}
