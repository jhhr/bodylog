package bodylog.logic.datahandling;

import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import java.time.LocalDate;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SessionsTest {

    private final Session ses1 = new Session();
    private final LocalDate date = LocalDate.of(1999, 2, 2);
    private final Set set1 = new Set();
    private final Set set2 = new Set();
    private final Object[] values1 = new Object[]{10.0, true, "N/A"};
    private final Object[] values2 = new Object[]{null, false, "cat"};
    private final Variable varNum 
            = new Variable("varNum", Type.NUMERICAL, new String[0]);
    private final Variable varBool 
            = new Variable("varBool", Type.CHECKBOX, new String[0]);
    private final Variable varOpt 
            = new Variable("varOpt", Type.OPTIONAL_CHOICE, new String[]{"cat"});
    private final Variable[] vars = {varNum,varBool,varOpt};

    @Before
    public void setUp() {
        ses1.setDate(date);
        for (Object value : values1) {
            set1.addValue(value);
        }
        for (Object value : values2) {
            set2.addValue(value);
        }
        ses1.addSet(set1);
        ses1.addSet(set2);
        for (Variable var : vars) {
            ses1.addVariable(var);
        }
    }

    @Test
    public void FormatAndParseBackOfSessionWithTwoSetsResultsInIdenticalSets()
            throws Exception {
        Session newSes = Sessions.parse(new Scanner(Sessions.format(ses1)), date);
        assertArrayEquals(newSes.getSet(0).toArray(), values1);
        assertArrayEquals(newSes.getSet(1).toArray(), values2);
    }

}
