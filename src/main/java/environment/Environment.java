package environment;

import java.util.HashMap;

public class Environment
{
    public HashMap<String, Object> allVars;

    public Environment()
    {
        allVars = new HashMap<>();
    }

    public Environment(HashMap<String, Object> a)
    {
        allVars = a;
    }

    //associates the given variable name with the given value
    public void setVariable(String variable, Object value)
    {
        allVars.put(variable, value);
    }

    //returns the value associated with the given variable
//name
    public Object getVariable(String variable)
    {
        return allVars.get(variable);
    }

    public boolean hasVariable(String a)
    {
        return getAllVars().containsKey(a);
    }

    public HashMap<String, Object> getAllVars()
    {
        return allVars;
    }
}
