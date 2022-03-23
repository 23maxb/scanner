package ast;

import environment.Environment;

public abstract class Expression
{
    public abstract Object evaluate(Environment e);

    public abstract String toString();

}
