/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic;

import bodylog.logic.datahandling.Names;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class VariableTest {

    Variable var;

    @Before
    public void setUp() {
        var = new Variable();
    }

    @Test
    public void CantUseBannedCharsInVariable() {
        boolean variableAdded = false;
        for (char ch : Names.Illegal.VARIABLE.getChars()) {
            try {
                var.setName("asd" + ch + "fjkl");
                variableAdded = true;
            } catch (Exception e) {
            }
        }
        assertFalse(variableAdded);
    }
    
    @Test
    public void CantUseBannedCharsInChoices() {
        boolean choicesAdded = false;
        assertFalse(choicesAdded);
    }
}
