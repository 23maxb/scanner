package ast;

import environment.Environment;

import java.util.List;

/**
 * Creates blocks of statements (basically an array of statements)
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class Block implements Statement
{
    private List<Statement> statements;

    /**
     * Creates a block from a list of statements
     *
     * @param statements the list of statements to use to create the block from
     */
    public Block(List<Statement> statements)
    {
        this.statements = statements;
    }

    /**
     * Executes every item in the block
     *
     * @param e the enviornment to get variables from
     */
    @Override
    public void exec(Environment e)
    {
        for (Statement statement : statements)
        {
            System.out.println(statement.toString());
            statement.exec(e);
        }
    }

    /**
     * Returns a string representation of the Block object
     *
     * @return a string representation of the Block object
     */
    @Override
    public String toString()
    {
        return "Block(" + statements.toString() + ")";
    }
}
