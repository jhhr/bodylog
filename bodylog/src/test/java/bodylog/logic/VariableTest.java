package bodylog.logic;

import bodylog.logic.Variable.Type;
import bodylog.logic.datahandling.Names;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
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
    public void ReturnsClassObjectForEachType() {
        for (Type type : Type.values()) {
            var.setType(type);
            assertTrue(var.getAllowedClass() instanceof Class);
        }
    }
    
    @Test
    public void ReturnsNonEmptyToolTipForEachType(){
        for (Type type : Type.values()) {
            var.setType(type);
            assertFalse(var.getToolTip().isEmpty());
        }
    }

}
