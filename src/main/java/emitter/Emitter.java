package emitter;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Emitter class. This class is responsible for emitting the code to a file.
 *
 * @author Max Blennemann
 * @version 4/27/22
 */
public class Emitter
{
    private String currentMethod;
    private final PrintWriter out;
    private final String fileName;

    //creates an emitter for writing to a new file with given name
    public Emitter(String outputFileName)
    {
        currentMethod = null;
        fileName = outputFileName;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //prints one line of code to file (with non-labels indented)
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * Sets the current method
     *
     * @param method the new method path
     */
    public void setCurrentMethod(String method)
    {
        currentMethod = method;
    }

    /**
     * Adds the method's name and how the program got there.
     *
     * @param methodName the name of the method
     */
    public void addMethod(String methodName)
    {
        currentMethod = currentMethod == null ? methodName : currentMethod + "." + methodName;
    }

    /**
     * Resets the value of currentMethod to null (meaning the program is in the global scope)
     *
     * @return the current method
     */
    public void resetMethod()
    {
        currentMethod = null;
    }

    public String getFileName()
    {
        return fileName;
    }

    //closes the file.  should be called after all calls to emit.
    public void close()
    {
        out.close();
        System.out.println("Successfully finished writing to: " + fileName);
    }

    private int labelCount = 0;

    /**
     * Creates a new label every time.
     *
     * @return a new label
     */
    public String label()
    {
        return "label" + labelCount++;
    }

    /**
     * Pushes a new value onto the stack.
     *
     * @param reg the value to push onto the stack
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw $" + reg + " ($sp)");
    }

    /**
     * pops a value off the stack.
     *
     * @param reg the value to pop the value into
     */
    public void emitPop(String reg)
    {
        emit("lw $" + reg + " ($sp)");
        emit("addu $sp $sp 4");
    }
}


