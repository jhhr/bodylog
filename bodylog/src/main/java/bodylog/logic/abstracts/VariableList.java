/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic.abstracts;

import bodylog.logic.Variable;
import bodylog.logic.exceptions.DuplicateVariableNameException;
import bodylog.logic.exceptions.VariableStateException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Abstract class containing the functionality required by a class that uses a
 * list of Variables.
 */
public abstract class VariableList {

    protected Variable[] variables;

    public VariableList() {
        this.variables = new Variable[0];
    }

    /**
     * @return the number of Variables in this
     */
    public int getVariableCount() {
        return variables.length;
    }

    /**
     * Sets all variables for this.
     *
     * @param variables the array of Variables to be set
     */
    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }

    /**
     * Gets all variables in this.
     *
     * @return an array of Variables
     */
    public Variable[] getVariables() {
        return variables;
    }

    /**
     * Gets the variable from the specified index. Doesn't check if it's out of
     * bounds or not.
     *
     * @param index the index from which to get the Variable
     * @return a Variable
     */
    public Variable getVariable(int index) {
        return variables[index];
    }

    /**
     * Adds a new variable to the end of the list.
     *
     * @param var name of the variable to be added
     * @see bodylog.logic.Variable
     */
    public void addVariable(Variable var) {
        variables = Arrays.copyOf(variables, getVariableCount() + 1);
        variables[getVariableCount() - 1] = var;
    }

    /**
     * Adds a variable to the specified index in the list. Replaces the previous
     * value if present or extends list to index if out of bounds.
     *
     * @param var the Variable to be added
     * @param index the index to be added to
     * @see bodylog.logic.Variable
     */
    public void setVariable(Variable var, int index) {
        try {
            variables[index] = var;
        } catch (IndexOutOfBoundsException in) {
            variables = Arrays.copyOf(variables, index + 1);
            variables[index] = var;
        }
    }

    /**
     * Constructs a string array of the names of the variables in this.
     *
     * @return a string array containing the names of the variables
     */
    public String[] getVariableNames() {
        int varCount = getVariableCount();
        String[] variableNames = new String[getVariableCount()];
        for (int i = 0; i < varCount; i++) {
            variableNames[i] = getVariableName(i);
        }
        return variableNames;
    }

    /**
     * Gets the name of the variable at the specified index in the list. Doesn't
     * check if it's out of bounds or not.
     *
     * @param index index used to get the variable
     * @return a variable
     */
    public String getVariableName(int index) {
        return variables[index].getName();
    }

    public Object[] getDefaultValues() {
        Object[] defaultValues = new Object[getVariableCount()];
        for (int index = 0; index < defaultValues.length; index++) {
            defaultValues[index] = getVariable(index).getDefaultValue();
        }
        return defaultValues;
    }

    public void checkVariables() throws VariableStateException,
            DuplicateVariableNameException {
        for (Variable var : variables) {
            var.checkState();
        }
        HashSet<String> nameSet = new HashSet();
        for (String name : getVariableNames()) {
            if (!nameSet.add(name)) {
                throw new DuplicateVariableNameException(
                        "Duplicate variable names are not allowed.");
            }
        }
    }

}
