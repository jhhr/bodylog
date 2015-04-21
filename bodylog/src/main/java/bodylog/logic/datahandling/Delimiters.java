/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bodylog.logic.datahandling;

public class Delimiters {
    
    public static final String SECTION = "-";
    public static final String TITLE = ":";
    public static final String VALUE = ",";
    
    public static char[] charArray(){
        return new char[]{SECTION.charAt(0),TITLE.charAt(0),VALUE.charAt(0)};
    }
}
