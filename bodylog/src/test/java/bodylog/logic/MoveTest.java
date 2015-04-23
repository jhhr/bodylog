package bodylog.logic;

import bodylog.logic.Variable.Type;
import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeSet;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {

    Move bench;
    String benchName = "bench";
    Session session;
    Variable varWeight = new Variable("weight", Type.NUMERICAL, new String[]{});
    Variable varTearing = new Variable(
            "tore something", Type.CHECKBOX, new String[]{});

    @Before
    public void SetUp() {
        bench = new Move(benchName);
        session = new Session();
    }

    @Test
    public void Constructor_BlankNameNoVarsNoSessionsInNewMove() {
        Move blank = new Move();

        assertEquals("", blank.getName());
        assertEquals(0, blank.variableCount());
        assertTrue(blank.getSessions().isEmpty());
    }

    @Test
    public void Constructor_WithName() {
        assertEquals(benchName, bench.toString());
    }

    @Test
    public void SetName_RemovesTrailingWhiteSpace() throws Exception {
        bench.setName(" spaces  ");
        assertEquals("spaces", bench.getName());
    }

    @Test
    public void VariableListSizeOneWhenOneVariableAdded() {
        bench.addVariable(varWeight);
        assertEquals(1, bench.variableCount());
    }

    @Test
    public void CantUseBannedCharsWhenSettingName() {
        boolean nameSet = false;
        for (char ch : Names.Illegal.MOVE_NAME.getChars()) {
            try {
                String name = "asd" + ch + "fjkl";
                bench.setName(name);
                nameSet = true;
            } catch (NameNotAllowedException e) {
            }
        }
        assertFalse(nameSet);
    }

    @Test
    public void VariableAddedNormallyFoundAtExpectedIndex() {
        bench.addVariable(varWeight);
        assertEquals(varWeight.getName(), bench.getVariableName(0));
        bench.addVariable(varTearing);
        assertEquals(varTearing.getName(), bench.getVariableName(1));
    }

    @Test
    public void VariableAddedBeyondCurrectLengthFoundAtExpectedIndex() {
        bench.addVariable(varWeight, 5);
        assertEquals(varWeight.getName(), bench.getVariableName(5));
    }

    @Test
    public void VariableAddedToSpecificIndexReplacesPreviousValue() {
        bench.addVariable(varWeight);
        bench.addVariable(varTearing, 0);
        assertEquals(varTearing.getName(), bench.getVariableName(0));
    }

    @Test
    public void AddedSessionIsFoundAtExpectedIndex() {
        bench.addSession(session);
        assertEquals(session, bench.getSession(0));
        Session ses2 = new Session(LocalDate.now());
        bench.addSession(ses2);
        assertEquals(ses2, bench.getSession(1));
    }

    @Test
    public void NumberOfSessionsOneWhenOneSessionAdded() {
        bench.addSession(session);
        assertEquals(1, bench.getSessions().size());
    }

    @Test
    public void EqualsComparisonTrueIfMovesHaveSameNameFalseOtherwise() {
        Move bench2 = new Move(benchName);
        assertTrue(bench.equals(bench2));
        Move dl = new Move("deadlift");
        assertFalse(bench.equals(dl));
        assertFalse(bench.equals(null));
        assertFalse(bench.equals(benchName));
    }

    @Test
    public void MovesAreOrderedAlphabeticallyByName() {
        Move first = new Move("abc");
        Move second = new Move("BBC");
        Move third = new Move("sp ace");
        Move fourth = new Move("zzTop");
        TreeSet moveList = new TreeSet<Move>();
        moveList.add(first);
        moveList.add(fourth);
        moveList.add(third);
        moveList.add(second);
        assertArrayEquals(new Move[]{first, second, third, fourth},
                moveList.toArray());
    }

    @Test
    public void MoveHashCodeNotSameAsJustNameHashCode() {
        assertFalse(bench.hashCode() == benchName.hashCode());
    }

    @Test
    public void MovesWithSameNameAreReplacedAsHashMapValues() {
        HashMap moveMap = new HashMap<Move, String>();
        Move abc1 = new Move("abc");
        moveMap.put(abc1, "one");
        Move abc2 = new Move("abc");
        moveMap.put(abc2, "two");
        assertEquals("two", moveMap.get(new Move("abc")));

    }
}
