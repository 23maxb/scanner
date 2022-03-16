package AST;

import java.util.List;

public class Block extends Statement
{
    private List<Statement> statements;

    public Block(List<Statement> statements)
    {
        this.statements = statements;
    }

    //TODO: implement this!
    public void exec(Environment e)
    {
        for (Statement statement : statements)
        {
            statement.exec(e);
        }
    }
}
