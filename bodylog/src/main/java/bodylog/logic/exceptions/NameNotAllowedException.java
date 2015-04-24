package bodylog.logic.exceptions;

/**
 * Custom exception for situations when a string is not allowed. Used for move
 * names, variable names and variable choices.
 * 
 * @see bodylog.logic.datahandling.Names#isAllowed
 */
public class NameNotAllowedException extends Exception {

    public NameNotAllowedException(String message) {
        super(message);
    }
}
