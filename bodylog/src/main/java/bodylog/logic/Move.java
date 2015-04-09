package bodylog.logic;

import bodylog.files.Constant;
import java.util.ArrayList;

/**
 * The first component in the logical hierarchy. Container for session data and
 * movement variables. Contains a name, a list of string variables and a list of
 * Sessions. The name is used as the date directory name and the move defining
 * file name, from which it is also read.
 *
 * @see bodylog.logic.Session
 * @see bodylog.logic.Set
 */
public class Move {

    private String name;
    private final ArrayList<String> variables;
    private final ArrayList<Session> sessionList;

    /**
     * Constructs a new move with given name, checks whether the name is
     * allowed. Throws an exception it isn't.
     *
     * @param name Name given to move
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.util.Constant#nameIsAllowed
     */
    public Move(String name) {
        DataHandling.nameIsAllowed(name);
        this.name = name;
        this.variables = new ArrayList<>();
        this.sessionList = new ArrayList<>();
    }

    /**
     * Sets new name for the move. Checks whether the new name is allowed.
     * Throws an exception when it isn't.
     *
     * @param newName new name for move
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.util.Constant#nameIsAllowed
     */
    public void setName(String newName) {
        DataHandling.nameIsAllowed(newName);
        name = newName;
    }

    /**
     * Converts variables to an array. Used in tables in the UI.
     *
     * @return a string array containing this move's variables
     */
    public String[] variablesToArray() {
        return variables.toArray(new String[0]);
    }

    /**
     *
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
     *
     * @return the number of variables in this move
     */
    public int variableCount() {
        return variables.size();
    }

    /**
     * Gets a variable in the given index from the list. Doesn't check if it's
     * out of bounds or not.
     *
     * @param index index used to get the variable
     * @return a variable
     */
    public String getVariable(int index) {
        return variables.get(index);
    }

    /**
     * Adds a variable to the move. Checks if the name of the variable is
     * allowed. Throws an IllegalArgumentException if it isn't.
     *
     * @param name name of the variable to be added
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.util.Constant#nameIsAllowed
     */
    public void addVariable(String name) {
        DataHandling.nameIsAllowed(name);
        variables.add(name);
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

    /**
     *
     * @return the name of this move
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param obj Object to be compared to
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
}
