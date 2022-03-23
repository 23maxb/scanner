package ast;

import environment.Environment;

public abstract class Statement
{
    public abstract void exec(Environment env);

    @Override
    public abstract String toString();
}
