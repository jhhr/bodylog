package bodylog.logic.datahandling;

import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import static org.junit.Assert.*;
import org.junit.Test;

public class VariablesTest {

    private final String optName = "opt";
    private final Type optType = Type.OPTIONAL_CHOICE;
    private final String[] optChoices = {"one","two"};
    private final Variable optVar = new Variable(optName,optType,optChoices);


    @Test
    public void FormatAndParseBackOfProperVariableReturnsIdenticalVariable()
            throws Exception {
        Variable newVar = Variables.parseLine(Variables.format(optVar));
        assertEquals(optName,newVar.getName());
        assertEquals(optType,newVar.getType());
        assertArrayEquals(optChoices,newVar.getChoices());
    }
}
