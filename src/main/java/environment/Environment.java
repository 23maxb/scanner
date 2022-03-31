package environment;

import ast.ProcedureDeclaration;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment implements Cloneable
{
    public HashMap<String, Object> allVars;
    public HashMap<String, ProcedureDeclaration> allProcedures;
    public Environment parent;

    public Environment(HashMap<String, Object> newVars,
                       HashMap<String, ProcedureDeclaration> newProcedures)
    {
        this.allVars = newVars;
        this.setAllProcedures(newProcedures);
    }

    public Environment(
            HashMap<String, ProcedureDeclaration> newProcedures)
    {
        this.allVars = new HashMap<>();
        this.setAllProcedures(newProcedures);
    }

    public Environment()
    {
        this(new HashMap<>(), new HashMap<>());
    }

    public Environment(Environment parent)
    {
        this(new HashMap<>(), new HashMap<>());
        setParent(parent);
    }

    public Environment(HashMap<String, Object> a, HashMap<String, ProcedureDeclaration> b,
                       Environment env)
    {
        this(a, b);
        setParent(env);
    }

    public Environment(ArrayList<ProcedureDeclaration> procedures)
    {
        this();
        for (ProcedureDeclaration procedureDeclaration : procedures)
            addProcedure(procedureDeclaration.getName(), procedureDeclaration);
    }

    private void setParent(Environment parent)
    {
        this.parent = parent;
    }

    public Environment getParent()
    {
        if (parent != null)
            return parent;
        return this;
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

    public Environment global()
    {
        if (parent == null)
            return this;
        else
            return parent.global();
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
        return new Environment(new HashMap<>(getAllVars()), new HashMap<>(getAllProcedures()),
                this);
    }

    public ProcedureDeclaration getProcedure(String procedureCalled)
    {
        return getAllProcedures().get(procedureCalled);
    }
}
