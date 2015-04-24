package bodylog.logic.exceptions;

/**
 * Custom exception used in parsing strings to Sets and Variables.
 *
 * @see bodylog.logic.datahandling.Sets
 * @see bodylog.logic.datahandling.Variables
 */
public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }
}
