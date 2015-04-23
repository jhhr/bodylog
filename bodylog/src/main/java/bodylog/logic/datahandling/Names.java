package bodylog.logic.datahandling;

import bodylog.logic.exceptions.NameNotAllowedException;

/**
 * Class for encapsulating the parsing of data. Used for writing Set data to
 * session files and creating Set objects from reading session files and
 * checking names of moves and variables for characters used in parsing move
 * file contents.
 */
public class Names {

    private Names() {
    }

    /**
     * Lists of characters not allowed in certain places.
     *
     * Names of Moves are used as filenames for the session data folders and
     * move files, so some characters would cause errors.
     *
     * Certain characters are used in parsing data from files into Set values so
     * they are not allowed in variables of Moves.
     */
    public static enum Illegal {

        MOVE_NAME(new char[]{'/', '\\', ':', '*', '?', '"', '<', '>', '|'}),
        VARIABLE(Delimiters.CHARS);

        private final char[] charList;

        Illegal(char[] charList) {
            this.charList = charList;
        }

        public char[] getChars() {
            return charList;
        }
    }

    /**
     * Returns a string of the specified set of illegal characters with a space
     * added between each one for better readability.
     *
     * @param charSet Enumerator <code>Illegal</code> containing the character
     * list
     * @return String constructed from the char list
     */
    public static String IllegalCharsWithSpaces(Illegal charSet) {
        String chars = "";
        for (char c : charSet.getChars()) {
            chars += c + " ";
        }
        return chars.substring(0, chars.length() - 1);
    }

    /**
     * Checks if the given string contains any of the banned characters from the
     * specified character set. If so, throws an IllegalArgumentException. If
     * not, removes extraneous whitespace and returns the modified name. Used in
     * setting the name and variables for a Move.
     *
     * Use <code>Illegal.MOVE_NAME</code> for move name checking and
     * <code>Illegal.VARIABLE</code> for variable checking.
     *
     * @param name the string to be checked
     * @param charSet the set of characters that are not allowed in the name
     * @return the name, when allowed, with leading and trailing whitespace
     * removed
     * @throws NameNotAllowedException when the name is found to contain illegal
     * characters
     * @see bodylog.logic.Move
     * @see bodylog.logic.datahandling.Names
     */
    public static String isAllowed(String name, Illegal charSet)
            throws NameNotAllowedException {
        for (char ch : charSet.getChars()) {
            if (name.contains("" + ch)) {
                throw new NameNotAllowedException("The characters "
                        + IllegalCharsWithSpaces(charSet) + " are not allowed.");
            }
        }
        return name.trim();
    }

}
