package bodylog.files.edit;

import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.files.write.SessionSaver;
import bodylog.logic.Set;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Variable;
import bodylog.logic.datahandling.Sessions;
import bodylog.logic.datahandling.Sets;
import bodylog.logic.datahandling.Variables;
import bodylog.ui.MoveListContainerUpdater;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SessionSaverTest {

    private Util util = new Util();

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Delete.filesAndFolders();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }

    Session dlSession;
    Session benchSession;
    final String dateStr = "1970-01-01";
    final File benchSessionFile
            = new File(util.benchFolder, dateStr + Constant.SESSION_END);
    final File dlSessionFile
            = new File(util.dlFolder, dateStr + Constant.SESSION_END);
    TemporalAccessor date = LocalDate.of(1970, 1, 1);
    Set setONE;
    Set setTWO;
    Set setTHREE;
    String sessionFileContents;
    Object[] setONEValues = {50.0, 11, true,
        util.jackedChoices[0], util.progChoices[1]};
    Object[] setTWOValues = {80, 13.0, false,
        null, util.progChoices[0]};
    Object[] setTHREEValues = {0.0, null, null,
        util.jackedChoices[1], util.progChoices[0]};
    SessionSaver benchSaver;
    SessionSaver dlSaver;
    MoveListContainerUpdater updater;

    @Before
    public void setUp() {
        dlSession = new Session(date);
        benchSession = new Session(date);
        util.bench = new Move(util.benchName);
        util.deadlift = new Move(util.dlName);
        updater = new MoveListContainerUpdater();

        util.deadlift.addSession(dlSession);
        util.bench.addSession(benchSession);

        benchSaver = new SessionSaver(updater, util.bench);
        dlSaver = new SessionSaver(updater, util.deadlift);
    }

    private void addDLSets() {
        setTHREE = new Set();
        for (Object obj : setTHREEValues) {
            setTHREE.addValue(obj);
        }
        for (Variable var : util.vars) {
            util.deadlift.addVariable(var);
        }
        dlSession.addSet(setTHREE);
        sessionFileContents = Sessions.VARS_START
                + Variables.format(util.varWeight) + "\n"
                + Variables.format(util.varReps) + "\n"
                + Variables.format(util.varPumped) + "\n"
                + Variables.format(util.varJacked) + "\n"
                + Variables.format(util.varProg) + "\n"
                + Sessions.SETS_START
                + Sets.format(setTHREE);
    }

    private void addBenchSets() {
        setONE = new Set();
        for (Object obj : setONEValues) {
            setONE.addValue(obj);
        }
        setTWO = new Set();
        for (Object obj : setTWOValues) {

        }
        for (Variable var : util.vars) {
            util.bench.addVariable(var);
        }
        benchSession.addSet(setONE);
        benchSession.addSet(setTWO);
        sessionFileContents = Sessions.VARS_START
                + Variables.format(util.varWeight) + "\n"
                + Variables.format(util.varReps) + "\n"
                + Variables.format(util.varPumped) + "\n"
                + Variables.format(util.varJacked) + "\n"
                + Variables.format(util.varProg) + "\n"
                + Sessions.SETS_START
                + Sets.format(setONE) + "\n"
                + Sets.format(setTWO);
    }

    @Test
    public void GetsCorrectMove() {
        assertEquals(util.deadlift, dlSaver.getMove());
    }

    @Test
    public void Sessions_CreatesSessionFile() throws Exception {
        Constant.DATA_DIR.mkdir();
        util.benchFolder.mkdir();
        benchSaver.saveToFile();
        assertTrue(benchSessionFile.exists());
    }

    @Test
    public void Sessions_FindsSessionFileWithSameDateAsOnSession()
            throws Exception {
        Constant.DATA_DIR.mkdir();
        util.dlFolder.mkdir();
        dlSessionFile.createNewFile();
//        System.out.println("sessionFile: " + dlSessionFile.getName());
//        System.out.println("session date from movesaver: "
//                + dlSaver.getMove().getSession(0).getFileDateString());
        assertTrue(dlSaver.fileExists());
    }

    @Test
    public void SessionFileCheckerReturnsFalseForNonexistentFile() {
        assertFalse(benchSaver.fileExists());
    }

    @Test
    public void Sessions_SessionFileContentsAsExpectedUsingMoveWithOneSetOfData()
            throws Exception {
        addDLSets();
        Constant.DATA_DIR.mkdir();
        util.dlFolder.mkdir();
        dlSaver.saveToFile();
        compareFileContents(sessionFileContents,dlSessionFile);
    }

    @Test
    public void Sessions_SessionFileContentsAsExpectedUsingMoveWithTwoSetsOfData()
            throws Exception {
        addBenchSets();
        Constant.DATA_DIR.mkdir();
        util.benchFolder.mkdir();
        benchSaver.saveToFile();
        compareFileContents(sessionFileContents,benchSessionFile);
    }

    public void compareFileContents(String expected, File actual)
            throws Exception {
        Scanner scanActual = new Scanner(actual);
        Scanner scanExpected = new Scanner(expected);
        String expectedLine = "";
        String actualLine = "";
        try {
            while (scanExpected.hasNextLine()) {
                expectedLine = scanExpected.nextLine();
                actualLine = scanActual.nextLine();
                assertEquals(expectedLine, actualLine);
            }
        } catch (Throwable t) {
            System.out.println("expected: " + expectedLine);
            System.out.println("actual: " + actualLine);
            throw t;
        } finally {
            scanActual.close();
            scanExpected.close();
        }
    }

}
