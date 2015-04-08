package bodylog.logic;

import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.util.Constant;
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {

    Move bench;
    String benchName = "bench";
    Session session;
    String varWeight = "weight";

    @Before
    public void SetUp() {
        bench = new Move(benchName);
        session = new Session(LocalDate.now());
    }

    @Test
    public void NoVariablesInNewMove() {
        assertEquals(0, bench.variablesToArray().length);
    }

    @Test
    public void NoSessionsInNewMove() {
        assertTrue(bench.getSessions().isEmpty());
    }

    @Test
    public void VariableListSizeOneWhenOneVariableAdded() {
        bench.addVariable(varWeight);
        assertEquals(1, bench.variablesToArray().length);
    }

    @Test
    public void CantUseBannedCharsWhenCreatingMove() {
        boolean moveCreated = false;
        for (char ch : Constant.BANNED_CHARS) {
            try {
                String name = "asd" + ch + "fjkl";
                new Move(name);
                moveCreated = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(moveCreated);
    }

    @Test
    public void CantUseBannedCharsWhenChangingName() {
        boolean nameSet = false;
        for (char ch : Constant.BANNED_CHARS) {
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
        for (char ch : Constant.BANNED_CHARS) {
            try {
                bench.addVariable("asd" + ch + "fjkl");
                variableAdded = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(variableAdded);
    }

    @Test
    public void AddedSessionIsFoundAtExpectedIndex() {
        bench.addSession(session);
        assertEquals(session, bench.getSession(0));
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
}
