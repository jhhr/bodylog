package bodylog.logic.datahandling;

import bodylog.logic.Move;
import bodylog.logic.Variable;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import java.util.Scanner;

/**
 * Contains all definitions of how Moves are converted to strings and back.
 */
public class Moves {

    //static class, does not have instances
    private Moves() {
    }

    public static final String VARS_START = "move variables"
            + Delimiters.START + "\n";

    /**
     * Parses the entire contents of a move file and returns a Move with the
     * Variables from the contents.
     *
     * @param scanner the Scanner containing the move file to be parsed
     * @param name the name parsed from the filename of the move file
     *
     * @return a Move populated with Variables
     *
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public static Move parse(Scanner scanner, String name)
            throws ParsingException, VariableStateException {
        Move move = new Move(name);
        scanner.useDelimiter(VARS_START);
        for (String line : scanner.next().split("\n")) {
            move.addVariable(Variables.parseLine(line));
        }
        return move;
    }

    /**
     * Formats a Move to a string of one or more lines.
     *
     * @param move the Move to be formatted
     *
     * @return a string describing this Move
     */
    public static String format(Move move) {
        String moveStr = VARS_START;
        for (Variable var : move.getVariables()) {
            moveStr += Variables.format(var) + "\n";
        }
        //remove last linefeed
        return moveStr.substring(0, moveStr.length() - 1);
    }

}
