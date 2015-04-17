package bodylog.logic;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {

    Move bench;
    String benchName = "bench";
    Session session;
    String varWeight = "weight";
    String varReps = "reps";

    @Before
    public void SetUp() {
        bench = new Move(benchName);
        session = new Session(LocalDate.now());
    }

    @Test
    public void Constructor_BlankNameNoVarsNoSessionsInNewMove() {
        Move blank = new Move();

        assertEquals("", blank.toString());
        assertEquals(0, blank.variableCount());
        assertTrue(blank.getSessions().isEmpty());
    }

    @Test
    public void Constructor_WithName() {
        assertEquals(benchName, bench.toString());
    }

    @Test
    public void Constructor_WithNameWhiteSpaceRemoved() {
        Move withSpaces = new Move(" spaces  \n");
        assertEquals("spaces", withSpaces.toString());
    }

    @Test
    public void VariableListSizeOneWhenOneVariableAdded() {
        bench.addVariable(varWeight);
        assertEquals(1, bench.variableCount());
    }

    @Test
    public void CantUseBannedCharsWhenSettingName() {
        boolean nameSet = false;
        for (char ch : DataHandling.Illegal.MOVE_NAME.getChars()) {
            try {
                String name = "asd" + ch + "fjkl";
                bench.setName(name);
                nameSet = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(nameSet);
    }

    @Test
    public void CantUseBannedCharsInVariable() {
        boolean variableAdded = false;
        for (char ch : DataHandling.Illegal.VARIABLE.getChars()) {
            try {
                bench.addVariable("asd" + ch + "fjkl");
                variableAdded = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(variableAdded);
    }

    @Test
    public void VariableAddedNormallyFoundAtExpectedIndex() {
        bench.addVariable(varWeight);
        assertEquals(varWeight, bench.getVariable(0));
        bench.addVariable(varReps);
        assertEquals(varReps, bench.getVariable(1));
    }

    @Test
    public void VariableAddedBeyondCurrectLengthFoundAtExpectedIndex() {
        bench.addVariable(varWeight, 5);
        assertEquals(varWeight, bench.getVariable(5));
    }

    @Test
    public void VariableAddedToSpecificIndexReplacesPreviousValue() {
        bench.addVariable(varWeight);
        bench.addVariable(varReps, 0);
        assertEquals(varReps, bench.getVariable(0));
    }

    @Test
    public void AddedSessionIsFoundAtExpectedIndex() {
        bench.addSession(session);
        assertEquals(session, bench.getSession(0));
        Session ses2 = new Session(LocalDate.now());
        bench.addSession(ses2);
        assertEquals(ses2, bench.getSession(1));
    }

    @Test(expected = NullPointerException.class)
    public void CantAddNullIntoSessionList() {
        bench.addSession(null);
    }

    @Test
    public void NumberOfSetsOneWhenOneSetAdded() {
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
    public void MoveHashCodeNotSameAsJustNameHashCode(){
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
