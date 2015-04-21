package bodylog.logic.datahandling;

import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.logic.exceptions.NameNotAllowedException;
import bodylog.logic.exceptions.ParsingException;

/**
 * Contains all definitions of how Variables are converted to strings and back.
 */
public class Variables {

    private static final String NAME = "name" + Delimiters.TITLE;
    private static final String TYPE = "type" + Delimiters.TITLE;
    private static final String CHOICES = "choices" + Delimiters.TITLE;

    /**
     * Parses the given line from a move or session file and returns a Variable
     * with the values from the line.
     *
     * @param line the line to be parsed
     * @return a Variable with name, type and choices set
     * @throws NameNotAllowedException when the name parsed from the line was
     * not allowed
     * @throws ParsingException when failing to parse the type from the line
     */
    public static Variable parseLine(String line)
            throws NameNotAllowedException, ParsingException {
        Variable var = new Variable();
        //split line into name section, type section and choices section
        String[] sections = line.split(Delimiters.SECTION);
        //get name from second value of split first section
        var.setName(sections[0].split(Delimiters.TITLE)[1]);
        //parse type from second value of split second section      
        var.setType(parseType(sections[1].split(Delimiters.TITLE)[1]));
        //get choices from splitting third section
        var.setChoices(sections[2].split(
                Delimiters.TITLE)[1].split(Delimiters.VALUE));
        return var;
    }

    private static Type parseType(String typeStr) throws ParsingException {
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
