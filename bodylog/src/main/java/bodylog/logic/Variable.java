package bodylog.logic;

import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import bodylog.logic.exceptions.VariableStateException;
import java.util.Arrays;

/**
 * Class that defines the different types of variables the user can give to a
 * Move. A variable has a name. A variable is one of a set of different types,
 * see {@link Type Type}.
 *
 * @see bodylog.logic.Move
 */
public class Variable {

    private String name;
    private Type type;
    private String[] choices;

    /**
     * Enumerator of possible types for a Variable.
     * <ul>
     * <li> <code>NUMERICAL</code> allows only double values</li>
     * <li><code>CHECKBOX</code> allows only boolean values</li>
     * <li><code>OPTIONAL_CHOICE</code> or <code>MANDATORY CHOICE</code> allows
     * only string values from a list of choices</li>
     * <ul>
     * <li> <code>OPTIONAL_CHOICE</code> allows no choice to be made</li>
     * <li> <code>MANDATORY CHOICE</code> requires a choice to be made</li>
     * </ul>
     * </ul>
     */
    public enum Type {

        NUMERICAL, CHECKBOX, OPTIONAL_CHOICE, MANDATORY_CHOICE;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * Creates a new Variable with all attributes set. Used when reading
     * variable data from file, so arguments are assumed to be correct and are
     * not checked.
     *
     * @param name the name of the variable, not null
     * @param type the type of the variable, not null
     * @param choices list of string values allowed for this variable, not null,
     * can be empty when the variable is of type numerical or checkbox
     */
    public Variable(String name, Type type, String[] choices) {
        this.name = name;
        this.type = type;
        this.choices = choices;
    }

    /**
     * Creates a new blank variable. Name is set to an empty string, type is
     * <code>NUMERICAL</code>, choices is a zero-length array.
     */
    public Variable() {
        this("", Type.NUMERICAL, new String[0]);
    }

    /**
     * Attempts to set a new name for the Variable; if the name fails the check,
     * throws an exception.
     *
     * @param name the new name
     * @throws NameNotAllowedException when the name is not allowed
     *
     * @see bodylog.logic.DataHandling#nameIsAllowed
     */
    public void setName(String name) throws NameNotAllowedException {
        this.name = Names.isAllowed(name, Names.Illegal.VARIABLE);
    }

    public String getName() {
        return name;
    }

    public void setType(Type newType) {
        this.type = newType;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns the class allowed for this variable. Used in defining column
     * class in tables.
     *
     * @return double if type is <code>NUMERICAL</code>, boolean if
     * <code>CHECKBOX</code>, string otherwise
     */
    public Class getAllowedClass() {
        switch (type) {
            case NUMERICAL:
                return Double.class;
            case CHECKBOX:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getToolTip() {
        switch (type) {
            case NUMERICAL:
                return "Input numbers in the form X or X.X";
            case CHECKBOX:
                return "Check or uncheck the box";
            case OPTIONAL_CHOICE:
                return "You may choose one or none";
            case MANDATORY_CHOICE:
                return "You must choose one";
            default:
                return "";
        }
    }

    public int choiceCount() {
        return choices.length;
    }

    /**
     * Checks if the name of the variable is blank or the type is in
     * contradiction with the number of choices.
     *
     * @throws VariableStateException when any of the following applies:
     * <ul>
     * <li>name is blank</li>
     * <li>type is <code>NUMERICAL</code> or <code>CHECKBOX</code> and there are
     * 1 or more choices</li>
     * <li>type is <code>OPTIONAL_CHOICE</code> and there are no choices</li>
     * <li>type is <code>MANDATORY_CHOICE</code> and there are less than two
     * choices</li>
     * </ul>
     */
    public void checkState() throws VariableStateException {
        String message = "";
        if (name.isEmpty()) {
            message += "Variable name cannot be blank.\n";
        }
        switch (type) {
            case NUMERICAL:
            case CHECKBOX:
                if (choices.length != 0) {
                    message += "No choices are allowed when type is "
                            + type + ".";
                }
                break;
            case OPTIONAL_CHOICE:
                if (choices.length == 0) {
                    message += "There must be at least 1 choice when type is "
                            + type + ".";
                }
                break;
            case MANDATORY_CHOICE:
                if (choices.length < 2) {
                    message += "There must be at least 2 choices when type is "
                            + type + ".";
                }
                break;
        }
        if (!message.isEmpty()) {
            throw new VariableStateException(message);
        }
    }
}
