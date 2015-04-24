package bodylog.logic.datahandling;

import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import java.util.regex.Pattern;

/**
 * Contains all definitions of how Variables are converted to strings and back.
 */
public class Variables {

    //static class, does not have instances
    private Variables() {
    }

    public static final String NAME = "name" + Delimiters.TITLE;
    public static final String TYPE = "type" + Delimiters.TITLE;
    public static final String CHOICES = "choices" + Delimiters.TITLE;

    /**
     * Parses the given line from a move or session file and returns a Variable
     * with the values from the line.
     *
     * @param line the line to be parsed
     * @return a Variable with name, type and choices set
     * @throws ParsingException when failing to parse the type from the line
     * @throws VariableStateException when the parsed Variable is not proper
     *
     * @see bodylog.logic.datahandling.Names#nameIsAllowed
     * @see bodylog.logic.Variable#checkState
     */
    public static Variable parseLine(String line)
            throws ParsingException, VariableStateException {
        //split line into name section, type section and choices section
        String[] sections = line.split(Pattern.quote(Delimiters.SECTION));
        //get name from second value of split first section
        String name = sections[0].split(Delimiters.TITLE)[1];
        //parse type from second value of split second section      
        Type type = parseType(sections[1].split(Delimiters.TITLE)[1]);
        //get choices from splitting third section
        String[] choiceList = sections[2].split(Delimiters.TITLE);
        //if length is 1, no choices were in the line
        String[] choices = choiceList.length < 2
                ? new String[0]
                : choiceList[1].split(Delimiters.VALUE);

        Variable var = new Variable(name, type, choices);
        var.checkState();
        return var;
    }

    /**
     * Compares input string to string formats of the enumerator Type in
     * Variable and returns the matching Type.
     *
     * @param typeStr the string to be parsed
     * @return a Type whose toString return value matches the string
     * @throws ParsingException when no match is found
     */
    public static Type parseType(String typeStr) throws ParsingException {
        Type type = null;
        if (typeStr.equals(Type.NUMERICAL.toString())) {
            type = Type.NUMERICAL;
        } else if (typeStr.equals(Type.CHECKBOX.toString())) {
            type = Type.CHECKBOX;
        } else if (typeStr.equals(Type.OPTIONAL_CHOICE.toString())) {
            type = Type.OPTIONAL_CHOICE;
        } else if (typeStr.equals(Type.MANDATORY_CHOICE.toString())) {
            type = Type.MANDATORY_CHOICE;
        } else {
            throw new ParsingException("Error while parsing Variable from string:"
                    + "type string did not match string format of any type.");
        }
        return type;
    }

    /**
     * Formats a Variable into a single line string.
     *
     * @param var the Variable to be formatted
     * @return a string describing the contents of the Variable
     */
    public static String format(Variable var) {
        String strVar = NAME + var.getName() + Delimiters.SECTION;
        strVar += TYPE + var.getType() + Delimiters.SECTION;
        strVar += CHOICES;
        if (var.choiceCount() != 0) {
            for (String choice : var.getChoices()) {
                strVar += choice + Delimiters.VALUE;
            }
            strVar = strVar.substring(0, strVar.length() - 1);
        }
        return strVar;

    }
}
