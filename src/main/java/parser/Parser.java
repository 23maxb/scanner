package parser;

import ast.*;
import ast.Number;
import environment.Environment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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

    private final Scanner scanner;
    private String currentToken;
    public Environment currentEnvironment;

    /**
     * Used to test
     *
     * @param args arguments ig?
     * @throws ScanErrorException    if the file is corrupted
     * @throws FileNotFoundException if the file is not found
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException
    {
        run("C:\\Users\\maxbl\\IdeaProjects\\scanner\\src\\main\\java\\parser\\parserTest7.txt");
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
            //double declaration needed to reassign currentToken DO NOT DELETE
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
    public Program parseProgram() throws ScanErrorException
    {
        ArrayList<ProcedureDeclaration> procedures = new ArrayList<>();
        ArrayList<Statement> a = new ArrayList<>();
        while (scanner.hasNext())
            if (currentToken.compareTo("PROCEDURE") == 0)
                procedures.add(parseDeclaration());
            else
                a.add(parseStatement());
        return new Program(new Block(a), new Environment(procedures));
    }

    public void run() throws ScanErrorException
    {
        parseProgram().exec(new Environment());
    }

    @Contract(" -> new")
    private @NotNull
    ProcedureDeclaration parseDeclaration() throws ScanErrorException
    {
        eat("PROCEDURE");
        String methodName = currentToken;
        eat(currentToken);
        eat("(");
        ArrayList<Variable> parameters = new ArrayList<>();
        for (; ; )
        {
            if (currentToken.compareTo(")") == 0)
            {
                eat(")");
                break;
            }
            else if (currentToken.compareTo(",") == 0)
            {
                eat(",");
            }
            else
            {
                parameters.add(new Variable(currentToken));
                eat(currentToken);
            }
        }
        eat(";");
        return new ProcedureDeclaration(methodName, parseStatement(),
                parameters.toArray(Variable[]::new));
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
        else if (currentToken.compareTo("WHILE") == 0)
        {
            eat("WHILE");
            Expression a = parseConditional();
            eat("DO");
            Block b = (Block) parseStatement();
            return new WhileLoop(a, b);
        }
        else if (currentToken.compareTo("RETURN") == 0)
        {
            eat("RETURN");
            Expression toReturn = parseExpression();

        }
        // varName represents the variable name or the procedure name
        String varName = currentToken;
        eat(currentToken);
        eat(":=");
        Statement toReturn = new Assignment(varName, parseExpression());
        eat(";");
        return toReturn;
    }

    /**
     * Handles conditional statements
     *
     * @return the conditional with everything evaluated
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
                res = new BinOp(res, ">", parseExpression());
            }
            else if (currentToken.compareTo("<") == 0)
            {
                eat("<");
                res = new BinOp(res, "<", parseExpression());
            }
            else if (currentToken.compareTo("<=") == 0)
            {
                eat("<=");
                res = new BinOp(res, "<=", parseExpression());
            }
            else if (currentToken.compareTo(">=") == 0)
            {
                eat(">=");
                res = new BinOp(res, ">=", parseExpression());
            }
            else
            {
                eat("<>");
                res = new BinOp(res, "<>", parseExpression());
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
        //need to check if it's a variable or a procedure call
        String a = currentToken; // a is the name of the variable or procedure
        eat(currentToken);
        if (Objects.equals(currentToken, "(")) // true if it's a procedure call
        {
            eat("(");
            ArrayList<Expression> b = new ArrayList<>();
            for (; ; )
            {
                if (currentToken.compareTo(")") == 0)
                {
                    eat(")");
                    break;
                }
                else if (currentToken.compareTo(",") == 0)
                {
                    eat(",");
                }
                else
                {
                    b.add(parseExpression());
                    eat(currentToken);
                }
            }
            return new ProcedureCall(a, b.toArray(Expression[]::new));
        }
        else
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
