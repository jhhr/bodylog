package bodylog.logic;

import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * The first component in the logical hierarchy. Container for session data and
 * movement variables. Contains a name, a list of Variables and a list of
 * Sessions. The name is used as the date directory name and the move defining
 * file name, from which it is also read.
 *
 * @see bodylog.logic.Variable
 * @see bodylog.logic.Session
 * @see bodylog.logic.Set
 */
public class Move implements Comparable<Move> {

    private String name;
    private Variable[] variables;
    private final ArrayList<Session> sessionList;

    /**
     * Constructs a new Move with the given name and no Sessions or Variables.
     *
     * @param name Name given to move
     * @see bodylog.logic.DataHandling#nameIsAllowed
     */
    public Move(String name) {
        this.name = name;
        this.variables = new Variable[]{};
        this.sessionList = new ArrayList<>();
    }

    /**
     * Constructs a new Move with a blank name.
     */
    public Move() {
        this("");
    }

    /**
     * Attempts to set a new name for the move; if the name fails the check,
     * throws an exception.
     *
     * @param newName the new name
     * @throws NameNotAllowedException when the name is not allowed
     * @see bodylog.logic.datahandling.Names#nameIsAllowed
     */
    public void setName(String newName) throws NameNotAllowedException {
        name = Names.nameIsAllowed(newName, Names.Illegal.MOVE_NAME);
    }

    /**
     * Converts the variable list to an array.
     *
     * @return a string array containing this Move's variables
     */
    public String[] getVariableNames() {
        int varCount = variableCount();
        String[] variableNames = new String[variableCount()];
        for (int i = 0; i < varCount; i++) {
            variableNames[i] = variables[i].getName();
        }
        return variableNames;
    }

    /**
     *
     * @return the number of variables in this move
     */
    public int variableCount() {
        return variables.length;
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

    /**
     * Adds a new variable to the end of the list.
     *
     * @param var name of the variable to be added
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.logic.Variable
     */
    public void addVariable(Variable var) {
        variables = Arrays.copyOf(variables, variables.length + 1);
        variables[variables.length - 1] = var;
    }

    /**
     * Adds a variable to the specified index in the list. Replaces the previous
     * value if present or extends list to index if out of bounds.
     *
     * @param var name of the variable to be added
     * @param index index to be added to
     * @see bodylog.logic.Variable
     */
    public void addVariable(Variable var, int index) {
        try {
            variables[index] = var;
        } catch (IndexOutOfBoundsException e) {
            variables = Arrays.copyOf(variables, index + 1);
            variables[index] = var;
        }
    }

    /**
     * @return the ArrayList of Sessions contained in this move
     */
    public ArrayList<Session> getSessions() {
        return sessionList;
    }

    /**
     * Gets a session from the given index in the move list. Doesn't check if
     * the index is out of bounds or not.
     *
     * @param index index used to get the session
     * @return the session at the given index
     */
    public Session getSession(int index) {
        return sessionList.get(index);
    }

    /**
     * Adds a session to the move. Checks if the parameter is null and throws a
     * NullPointerException if it is.
     *
     * @param session Session to be added
     * @throws NullPointerException when the parameter is null
     */
    public void addSession(Session session) {
        if (session == null) {
            throw new NullPointerException("yritetty lisata null liikkeen sessiolistaan");
        }
        sessionList.add(session);
    }

    public void clearSessions() {
        sessionList.clear();
    }

    public String getName() {
        return name;
    }

    /**
     * String form of a Move is simply its name.
     *
     * @return the name of this move
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Equals method.
     *
     * @return true if the object is a Move with the same name, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            return obj.toString().equals(this.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * CompareTo method. Orders Moves by to their name, ignoring case.
     *
     * @param other compare target
     * @return positive if this name follows other name, negative if this name
     * precedes other name, 0 if they are equal
     */
    @Override
    public int compareTo(Move other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}
