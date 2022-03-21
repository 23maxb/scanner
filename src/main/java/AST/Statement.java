package AST;

import environment.Environment;

public abstract class Statement
{
    public abstract void exec(Environment env);

    public abstract String toString();
}
