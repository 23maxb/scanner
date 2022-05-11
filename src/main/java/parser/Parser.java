package parser;

import ast.*;
import ast.Number;
import emitter.Emitter;
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
    /**
     * Environment to use for the parser (often just a blank environment).
     */
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
        emit("C:\\Users\\maxbl\\IdeaProjects\\scanner\\src\\main\\java\\parser\\hi.txt");
    }

    /**
     * Creates a new parser that can be used to run whatever file is passed
     *
     * @param s the scanner to use
     * @throws ScanErrorException if the scanner encounters an error
     * @precondition scanner is not null
     * @postcondition parser has scanner completely set up
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
     * @precondition currentToken is not null
     * @postcondition currentToken is eaten if it matches expected
     */
    @SuppressWarnings("UnusedReturnValue")
    private String eat(String expected) throws ScanErrorException
    {
        if (currentToken.equals(expected))
        {
            //double declaration needed to reassign currentToken DO NOT MERGE!!!!
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
     * @precondition the file exists
     * @postcondition the file is run and the result is printed
     */
    public static void run(String path) throws FileNotFoundException, ScanErrorException
    {
        new Parser(new Scanner(new FileInputStream(path))).run();
    }

    /**
     * Parses the entire file and then creates a text document that has the mips code in it.
     *
     * @param path The path to look at
     * @throws FileNotFoundException If the file is not found
     * @throws ScanErrorException    if an error is encountered with scanning (end of file usually)
     * @precondition the file exists
     * @postcondition the file is run and the result is printed
     */
    public static void emit(String path) throws FileNotFoundException, ScanErrorException
    {
        new Parser(new Scanner(new FileInputStream(path))).emit();
    }


    /**
     * Runs the program through tokens given by the scanner file.
     *
     * @return the program that is completely parsed
     * @throws ScanErrorException If the scanner encounters an error.
     * @precondition scanner has a file loaded correctly
     * @postcondition there is a new program object created
     */
    public Program parseProgram() throws ScanErrorException
    {
        ArrayList<ProcedureDeclaration> procedures = new ArrayList<>();
        ArrayList<String> vars = new ArrayList<>();
        ArrayList<Statement> a = new ArrayList<>();
        while (scanner.hasNext())
            if (currentToken.compareTo("PROCEDURE") == 0)
                procedures.add(parseDeclaration());
            else if (currentToken.compareTo("VAR") == 0)
            {
                eat("VAR");
                while (currentToken.compareTo(";") != 0)
                    if (currentToken.compareTo(",") == 0)
                        eat(",");
                    else
                    {
                        vars.add(currentToken);
                        eat(currentToken);
                    }
                eat(";");
            }
            else
                a.add(parseStatement());
        System.out.println(vars);
        return new Program(new Block(a), new Environment(procedures, vars));
    }

    /**
     * Parses the program from the linked scanner and then runs it in a completely blank
     * environment.
     *
     * @throws ScanErrorException If the scanner encounters an error.
     * @precondition scanner has a file loaded correctly
     * @postcondition the program is run
     */
    public void run() throws ScanErrorException
    {
        parseProgram().exec(new Environment());
    }

    /**
     * Parses the program from the linked scanner and then compiles the code as a mips file.
     */
    public void emit() throws ScanErrorException
    {
        Emitter a = new Emitter("output.asm");
        parseProgram().compile(a);
        a.close();
    }

    /**
     * Parses a declaration from the scanner.
     *
     * @return the parsed declaration
     * @throws ScanErrorException if the scanner encounters an error
     * @precondition scanner has a file loaded correctly
     * @postcondition the declaration is parsed and loaded into the environment
     */
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
            if (currentToken.compareTo(")") == 0)
            {
                eat(")");
                break;
            }
            else if (currentToken.compareTo(",") == 0)
                eat(",");
            else
            {
                parameters.add(new Variable(currentToken));
                eat(currentToken);
            }
        eat(";");
        return new ProcedureDeclaration(methodName, parseStatement(),
                parameters.toArray(Variable[]::new));
    }

    /**
     * Lists all the tokens in order for debugging
     *
     * @throws ScanErrorException if the scanner gets an error (this will not happen)
     * @precondition scanner has a file loaded correctly
     * @postcondition the tokens are printed
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
     * @precondition scanner has a file loaded correctly
     * @postcondition the number has been parsed
     * @deprecated use {@link #parseExpression()} instead
     */
    private int parseNumber() throws ScanErrorException
    {
        int toReturn = Integer.parseInt(currentToken);
        eat(currentToken);
        return toReturn;
    }

    /**
     * Parses a statement such as WRITELN or setting variable value.
     *
     * @precondition the scanner is at a statement
     * @postcondition the statement has been parsed
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
                b.add(parseStatement());
            eat("END");
            eat(";");
            return new Block(b);
        }
        else if (currentToken.compareTo("IF") == 0)
        {
            eat("IF");
            Expression a = parseConditional();
            eat("THEN");
            Statement b = parseStatement();
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
     * @precondition the conditional is ready to be parsed
     * @postcondition the conditional has been parsed
     */
    private Expression parseConditional() throws ScanErrorException
    {
        Expression res = (parseExpression());
        while ((currentToken.equals(">") || currentToken.equals("<")
                || currentToken.equals("<=") || currentToken.equals(">=") || currentToken.equals(
                "<>")))
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
     * @precondition the expression is ready to be parsed
     * @postcondition the expression has been parsed
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression res = (parseTerm());
        while ((currentToken.equals("+") || currentToken.equals("-")))
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
        return res;
    }

    /**
     * Parse a single term
     *
     * @return the value of the parsed term
     * @throws ScanErrorException if an error is encountered with the scanner
     * @precondition the term is ready to be parsed
     * @postcondition the term has been parsed
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression res = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
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
        return res;
    }

    /**
     * Handles parenthesis and subtracting and regular integers and variables
     *
     * @return the value
     * @throws ScanErrorException if the end of file is reached
     * @precondition the factor is ready to be parsed
     * @postcondition the factor has been parsed
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
                    if (currentToken.compareTo(")") != 0)
                        eat(currentToken);
                }
            }
            return new ProcedureCall(a, b.toArray(Expression[]::new));
        }
        else
            return new Variable(a);
    }  // end of parseFactor

    /**
     * Checks if the current token is an integer.
     *
     * @param a the string to check
     * @return true if the given string can be parsed
     * otherwise false
     * @precondition the string is not null
     * @postcondition the string has been checked
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