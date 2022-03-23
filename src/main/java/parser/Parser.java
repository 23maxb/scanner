package parser;

import AST.*;
import AST.Number;
import environment.Environment;
import scanner.ScanErrorException;
import scanner.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Parser Lab: uses a scanner to do specific pascal operations.
 *
 * @author Max Blennemann
 * @version 3/8/22
 */
public class Parser
{

    private Scanner scanner;
    private String currentToken;
    public Environment currentEnvironment;

    /**
     * Used to test
     *
     * @param args
     * @throws ScanErrorException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException
    {
        run("C:\\Users\\analyst\\IdeaProjects\\scanner2\\src\\main\\java\\parser\\parserTest6.txt");
    }

    /**
     * Creates a new parser that can be used to run whatever file is passed
     *
     * @param s the scanner to use
     * @throws ScanErrorException if the scanner encounters an error
     */
    public Parser(Scanner s) throws ScanErrorException
    {
        this.scanner = s;
        currentToken = scanner.nextToken();
        currentEnvironment = new Environment();
    }

    /**
     * Eats a single token. Meaning the next token will then be expected.
     *
     * @param expected the expected token
     * @return the thing that was eaten
     * @throws ScanErrorException if the scanner reaches end of file or similar error.
     */
    private String eat(String expected) throws ScanErrorException
    {
        String a = currentToken;
        if (currentToken.equals(expected))
        {
            currentToken = scanner.nextToken().replace(" ", "");
            return currentToken;
        }
        else
            throw new IllegalArgumentException("Expected "
                    + expected + " but found "
                    + currentToken + ".");
    }

    /**
     * Runs a file based on the path.
     *
     * @param path The path to look at
     * @throws FileNotFoundException If the file is not found
     * @throws ScanErrorException    if an error is encountered with scanning (end of file usually)
     */
    public static void run(String path) throws FileNotFoundException, ScanErrorException
    {
        new Parser(new Scanner(new FileInputStream(path))).run();
    }

    /**
     * Runs the program through tokens given by the scanner file.
     *
     * @throws ScanErrorException If the scanner encounters an error.
     */
    public void run() throws ScanErrorException
    {
        ArrayList<Statement> a = new ArrayList<>();

        while (scanner.hasNext())
            a.add(parseStatement());
        System.out.println(a);
        (new Block(a)).exec(currentEnvironment);
    }

    /**
     * Lists all the tokens in order for debugging
     *
     * @throws ScanErrorException if the scanner gets an error (this will not happen)
     */
    public void allTokens() throws ScanErrorException
    {
        while (scanner.hasNext())
            System.out.println(scanner.nextToken());
    }

    /**
     * Takes in a number and returns it as an int.
     * precondition: current token is an integer
     * post condition: number token has been eaten
     *
     * @return the value of the parsed integer
     */
    private int parseNumber() throws ScanErrorException
    {
        int toReturn = Integer.parseInt(currentToken);
        eat(currentToken);
        return toReturn;
    }

    /**
     * Parses a statement such as WRITELN or setting variable value.
     */
    private Statement parseStatement() throws ScanErrorException
    {
        if (Objects.equals(currentToken, "WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.compareTo("BEGIN") == 0)
        {
            ArrayList<Statement> b = new ArrayList<>();
            eat("BEGIN");
            while (currentToken.compareTo("END") != 0)
            {
                b.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(b);
        }
        else if (currentToken.compareTo("IF") == 0)
        {
            eat("IF");
            Expression a = parseConditional();
            eat("THEN");
            Block b = (Block) parseStatement();
            return new If(a, b);
        }
        String varName = currentToken;
        eat(currentToken);
        eat(":=");
        Statement toReturn = new Assignment(varName, parseExpression());
        eat(currentToken);
        return toReturn;
    }

    /**
     * Handles any + or /
     *
     * @return the value that the program gets
     * @throws ScanErrorException if there is an error scanning
     */
    private Expression parseConditional() throws ScanErrorException
    {
        Expression res = (parseExpression());
        while ((currentToken.equals(">") || currentToken.equals("<") || currentToken.equals("<=") || currentToken.equals(">=") || currentToken.equals("<>")))
        {
            if (currentToken.equals(">"))
            {
                eat(">");
                res = new BinOp(res, ">", parseTerm());
            }
            else if (currentToken.compareTo("<") == 0)
            {
                eat("<");
                res = new BinOp(res, "<", parseTerm());
            }
            else if (currentToken.compareTo("<=") == 0)
            {
                eat("<=");
                res = new BinOp(res, "<=", parseTerm());
            }
            else if (currentToken.compareTo(">=") == 0)
            {
                eat(">=");
                res = new BinOp(res, ">=", parseTerm());
            }
            else
            {
                eat("<>");
                res = new BinOp(res, "<>", parseTerm());
            }
        }
        return res;
    }

    /**
     * Handles any + or /
     *
     * @return the value that the program gets
     * @throws ScanErrorException if there is an error scanning
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression res = (parseTerm());
        while ((currentToken.equals("+") || currentToken.equals("-")))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                res = new BinOp(res, "+", parseTerm());
            }
            else
            {
                eat("-");
                res = new BinOp(res, "-", parseTerm());
            }
        }
        return res;
    }

    /**
     * Parse a single term
     *
     * @return the value of the parsed term
     * @throws ScanErrorException if an error is encountered with the scanner
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression res = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                res = new BinOp(res, "*", parseFactor());
            }
            else
            {
                eat("/");
                res = new BinOp(res, "/", parseFactor());
            }
        }
        return res;
    }

    /**
     * Handles parenthesis and subtracting and regular integers and variables
     *
     * @return the value
     * @throws ScanErrorException if the end of file is reached
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if (Objects.equals(currentToken, "("))
        {
            eat("(");
            Expression a = parseTerm();
            eat(")");
            return a;
        }
        else if (Objects.equals(currentToken, "-"))
        {
            eat("-");
            return new BinOp(parseFactor(), "*", new Number(-1));
        }
        else if (isInteger(currentToken))
        {
            int toReturn = Integer.parseInt(currentToken);
            eat(currentToken);
            return new Number(toReturn);
        }
        //if the current token is not an integer or a () or a subtraction
        String a = currentToken;
        eat(currentToken);
        return new Variable(a);
    }

    /**
     * Returns true if the given string is an integer
     *
     * @param a the string to check
     * @return true if the given string can be parsed
     * otherwise false
     */
    private static boolean isInteger(String a)
    {
        try
        {
            Integer.parseInt(a);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}
