package bodylog.logic;

import bodylog.files.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
    private String[] variables;
    private final ArrayList<Session> sessionList;

    /**
     * Constructs a new Move with a blank name, no Sessions and no variables.
     */
    public Move() {
        this.name = "";
        this.variables = new String[]{};
        this.sessionList = new ArrayList<>();
    }

    /**
     * Constructs a new Move with given name, checks whether the name is
     * allowed. Throws an exception it isn't.
     *
     * @param name Name given to move
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.logic.DataHandling#nameIsAllowed
     */
    public Move(String name) throws IllegalArgumentException{
        this();
        setName(name);
    }

    /**
     * Sets new name for the move. Checks whether the new name is allowed.
     * Throws an exception when it isn't.
     *
     * @param newName new name for move
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.logic.DataHandling#nameIsAllowed
     */
    public void setName(String newName) throws IllegalArgumentException {
        name = DataHandling.nameIsAllowed(newName, DataHandling.Illegal.MOVE_NAME);
    }

    /**
     * Converts the variable list to an array.
     *
     * @return a string array containing this Move's variables
     */
    public String[] getVariables() {
        return variables;
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
     *
     * @return the number of variables in this move
     */
    public int variableCount() {
        return variables.length;
    }

    /**
     * Gets a variable in the given index from the list. Doesn't check if it's
     * out of bounds or not.
     *
     * @param index index used to get the variable
     * @return a variable
     */
    public String getVariable(int index) {
        return variables[index];
    }

    /**
     * Adds a variable to the move. Checks if the name of the variable is
     * allowed. Throws an IllegalArgumentException if it isn't.
     *
     * @param var name of the variable to be added
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.logic.DataHandling#variableIsAllowed
     */
    public void addVariable(String var) throws IllegalArgumentException{
        String newVar = DataHandling.nameIsAllowed(var, DataHandling.Illegal.VARIABLE);
        variables = Arrays.copyOf(variables, variables.length + 1);
        variables[variables.length - 1] = newVar;
    }
    
    /**
     * Adds a variable to the specified index. Checks if the name of the variable is
     * allowed. Throws an IllegalArgumentException if it isn't.
     *
     * @param var name of the variable to be added
     * @param index index to be added to
     * @throws IllegalArgumentException when name is not allowed
     * @see bodylog.logic.DataHandling#variableIsAllowed
     */
    public void addVariable(String var, int index) throws IllegalArgumentException{
        String newVar = DataHandling.nameIsAllowed(var, DataHandling.Illegal.VARIABLE);        
        try {
            variables[index] = newVar;
        } catch (IndexOutOfBoundsException e) {
            variables = Arrays.copyOf(variables, index + 1);
            variables[index] = newVar;
        }
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
    
    public void clearSessions(){
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
