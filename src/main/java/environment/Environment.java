package environment;

import ast.ProcedureDeclaration;
import ast.Statement;

import java.util.HashMap;

public class Environment implements Cloneable
{
    public HashMap<String, Object> allVars;
    public HashMap<String, ProcedureDeclaration> allProcedures;
    public boolean isGlobal;

    public Environment()
    {
        allVars = new HashMap<>();
    }

    public boolean isGlobal()
    {
        return isGlobal;
    }

    public void setGlobal(boolean global)
    {
        isGlobal = global;
    }

    public Environment(boolean isGlobal)
    {
        this(new HashMap<>(), new HashMap<>(), isGlobal);
    }

    public Environment(HashMap<String, Object> a)
    {
        allVars = a;
        setGlobal(true);
    }
    public Environment(HashMap<String, Object> a, HashMap<String, ProcedureDeclaration> b)
    {
        this(a);
        this.setAllProcedures(b);
    }
    public Environment(HashMap<String, Object> a, HashMap<String, ProcedureDeclaration> b, boolean global)
    {
        this(a);
        this.setAllProcedures(b);
        this.setGlobal(global);
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

    public void setAllVars(HashMap<String, Object> allVars)
    {
        this.allVars = allVars;
    }

    public HashMap<String, ProcedureDeclaration> getAllProcedures()
    {
        return allProcedures;
    }

    public void setAllProcedures(HashMap<String, ProcedureDeclaration> allProcedures)
    {
        this.allProcedures = allProcedures;
    }

    public void addProcedure(String name, ProcedureDeclaration procedureDeclaration)
    {
        allProcedures.put(name, procedureDeclaration);
    }

    public HashMap<String, Object> getAllVars()
    {
        return allVars;
    }

    @Override
    public String toString()
    {
        return "Environment (" + getAllVars().toString() + ")";
    }

    public boolean hasProcedure(String procedureCalled)
    {
        return getAllProcedures().containsKey(procedureCalled);
    }

    @Override
    public Environment clone()
    {
        return new Environment(new HashMap<>(getAllVars()), new HashMap<>(getAllProcedures()));
    }

    public ProcedureDeclaration getProcedure(String procedureCalled)
    {
        return getAllProcedures().get(procedureCalled);
    }
}
