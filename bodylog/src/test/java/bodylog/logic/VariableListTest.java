package bodylog.logic;

import bodylog.logic.abstracts.VariableList;
import bodylog.logic.exceptions.DuplicateVariableNameException;
import bodylog.logic.exceptions.VariableStateException;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class VariableListTest {

    private class VariableListImpl extends VariableList {
    }

    VariableList varList;
    Variable varWeight
            = new Variable("weight", Variable.Type.NUMERICAL, new String[]{});
    Variable varDupeWeight
            = new Variable("weight", Variable.Type.NUMERICAL, new String[]{});
    Variable varImproperNumerical
            = new Variable("improper", Variable.Type.NUMERICAL, 
                    new String[]{"a"});
    Variable varImproperOptChoice
            = new Variable("improper", Variable.Type.OPTIONAL_CHOICE, 
                    new String[]{});    
    Variable varImproperManChoice
            = new Variable("improper", Variable.Type.MANDATORY_CHOICE, 
                    new String[]{"a"});
    Variable varTearing = new Variable(
            "tore something", Variable.Type.CHECKBOX, new String[]{});

    @Before
    public void setUp() {
        varList = new VariableListImpl();
    }

    @Test
    public void VariableListSizeOneWhenOneVariableAdded() {
        varList.addVariable(varWeight);
        assertEquals(1, varList.getVariableCount());
    }

    @Test
    public void VariableAddedNormallyFoundAtExpectedIndex() {
        varList.addVariable(varWeight);
        assertEquals(varWeight.getName(), varList.getVariableName(0));
        varList.addVariable(varTearing);
        assertEquals(varTearing.getName(), varList.getVariableName(1));
    }

    @Test
    public void VariableAddedBeyondCurrectLengthFoundAtExpectedIndex() {
        varList.setVariable(varWeight, 5);
        assertEquals(varWeight, varList.getVariable(5));
    }

    @Test
    public void VariableAddedToSpecificIndexReplacesPreviousValue() {
        varList.addVariable(varWeight);
        varList.setVariable(varTearing, 0);
        assertEquals(varTearing, varList.getVariable(0));
    }

    @Test(expected = DuplicateVariableNameException.class)
    public void DiscoversDuplicateVariableNames() throws Exception {
        varList.addVariable(varWeight);
        varList.addVariable(varDupeWeight);
        varList.checkVariables();
    }

    @Test(expected = VariableStateException.class)
    public void DiscoversImproperNumericalVariableWithOneChoice()
            throws Exception {
        varList.addVariable(varImproperNumerical);
        varList.checkVariables();
    }

    @Test(expected = VariableStateException.class)
    public void DiscoversImproperOptionalChoiceVariableWithNoChoices()
            throws Exception {
        varList.addVariable(varImproperOptChoice);
        varList.checkVariables();
    }
    
    @Test(expected = VariableStateException.class)
    public void DiscoversImproperMandatoryChoiceVariableWithOneChoice()
            throws Exception {
        varList.addVariable(varImproperManChoice);
        varList.checkVariables();
    }

}
