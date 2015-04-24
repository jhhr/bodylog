package bodylog.logic.exceptions;

/**
 * Custom exception for situations where a VariableList has two variables with
 * the same name.
 *
 * @see bodylog.logic.abstracts.VariableList
 * @see bodylog.logic.Move
 * @see bodylog.files.write.MoveSaver#saveToFile
 */
public class DuplicateVariableNameException extends Exception {

    public DuplicateVariableNameException(String message) {
        super(message);
    }

}
