/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic.datahandling;

public class Delimiters {

    //static class, does not have instances
    private Delimiters() {
    }

    public static final String START = ">";
    public static final String SECTION = "|";
    public static final String TITLE = "=";
    public static final String VALUE = ";";

    public static final char[] CHARS = new char[]{START.charAt(0),
        SECTION.charAt(0), TITLE.charAt(0), VALUE.charAt(0)};
}
