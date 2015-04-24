package bodylog.logic;

import bodylog.logic.abstracts.VariableList;
import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The first component in the logical hierarchy. Container for session data and
 * movement variables. Contains a name, a list of Variables and a list of
 * Sessions. The name is used as the name of the statistics directory and the
 * move defining file, from which it is also read.
 *
 * @see bodylog.logic.abstracts.VariableList
 * @see bodylog.logic.Variable
 * @see bodylog.logic.Session
 * @see bodylog.logic.Set
 */
public class Move extends VariableList implements Comparable<Move> {

    private String name;
    private final ArrayList<Session> sessions;

    /**
     * Constructs a new Move with the given name and no Sessions or Variables.
     *
     * @param name Name given to move
     * @see bodylog.logic.DataHandling#nameIsAllowed
     */
    public Move(String name) {
        super();
        this.name = name;
        this.sessions = new ArrayList<>();
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
     * @see bodylog.logic.datahandling.Names#isAllowed
     */
    public void setName(String newName) throws NameNotAllowedException {
        name = Names.isAllowed(newName, Names.Illegal.MOVE_NAME);
    }

    /**
     * @return the ArrayList of Sessions contained in this move
     */
    public ArrayList<Session> getSessions() {
        return sessions;
    }

    /**
     * Gets a session from the given index in the move list. Doesn't check if
     * the index is out of bounds or not.
     *
     * @param index index used to get the session
     * @return the session at the given index
     */
    public Session getSession(int index) {
        return sessions.get(index);
    }

    /**
     * Adds a session to the move. Checks if the parameter is null and throws a
     * NullPointerException if it is.
     *
     * @param session the Session to be added
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    public void clearSessions() {
        sessions.clear();
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
            Move move = (Move) obj;
            return name.equals(move.getName());
        } else {
            return false;
        }
    }

    /**
     * HashCode method. Uses the name of this move plus some salt which results
     * in a hash code that is distinct from the hash code of the name itself.
     *
     * @return a hash code
     */
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
