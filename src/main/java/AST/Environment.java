package AST;

import java.util.HashMap;

public class Environment
{
    HashMap<String, Object> a = new HashMap<>();

    //associates the given variable name with the given value
    public void setVariable(String variable, Object value)
    {
        a.put(variable, value);
    }

    //returns the value associated with the given variable
//name
    public Object getVariable(String variable)
    {
        return a.get(variable);
    }
}
