package bodylog.files.edit;

import bodylog.files.Constant;
import bodylog.files.Util;
import bodylog.files.edit.SessionSaver;
import bodylog.logic.Set;
import bodylog.logic.Move;
import bodylog.logic.Session;
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

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Util.deleteFiles();
    }

    @After
    public void tearDown() {
        Util.deleteFiles();
    }

    Session DLsession;
    Session benchSession;
    Move bench;
    Move deadlift;
    String benchName = "bench";
    String DLName = "deadlift";
    String varWeight = "weight";
    String varReps = "reps";
    String varBool = "pumped";
    String dateStr = "1970-01-01";
    TemporalAccessor date = LocalDate.of(1970, 1, 1);
    Set set1;
    Set set2;
    Set set3;
    File benchFolder;
    File DLFolder;
    File DLSessionFile;
    File benchSessionFile;
    File benchMoveFile;
    SessionSaver benchSaver;
    SessionSaver dlSaver;

    @Before
    public void setUp() {

        DLsession = new Session(date);
        benchSession = new Session(date);
        bench = new Move(benchName);
        deadlift = new Move(DLName);
        DLFolder = new File(Constant.DATA_DIR, DLName);
        benchFolder = new File(Constant.DATA_DIR, benchName);
        DLSessionFile = new File(DLFolder, dateStr + Constant.SESSION_END);
        benchSessionFile = new File(benchFolder, dateStr + Constant.SESSION_END);
        benchMoveFile = new File(Constant.MOVES_DIR, benchName + Constant.MOVE_END);
        benchSaver = new SessionSaver(bench);
        dlSaver = new SessionSaver(deadlift);
    }

    private void addDLSets() {
        set3 = new Set();
        set3.addValue(125.0);
        set3.addValue(5);
        set3.addValue(false);
        deadlift.addVariable(varWeight);
        deadlift.addVariable(varReps);
        deadlift.addVariable(varBool);
        DLsession.addSet(set3);
        deadlift.addSession(DLsession);
    }

    private void addBenchSets() {
        set1 = new Set();
        set1.addValue(65.5);
        set1.addValue(10);
        set1.addValue(true);
        set2 = new Set();
        set2.addValue(55.0);
        set2.addValue(0.00);
        set2.addValue(null);
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        bench.addVariable(varBool);
        benchSession.addSet(set1);
        benchSession.addSet(set2);
        bench.addSession(benchSession);
    }

    @Test
    public void Sessions_CreatingMoveFiles_SessionFileCheckerReturnsTrue() throws Exception {
        addDLSets();
        addBenchSets();
        Constant.DATA_DIR.mkdir();
        DLFolder.mkdir();
        benchFolder.mkdir();
        dlSaver.saveToFile();
        benchSaver.saveToFile();
        assertTrue(DLSessionFile.exists() && dlSaver.fileExists());
        assertTrue(benchSessionFile.exists() && benchSaver.fileExists());
    }

    @Test
    public void SessionFileCheckerReturnsFalseForNonexistentFile() {
        assertFalse(benchSaver.fileExists());
    }

    @Test
    public void Sessions_SessionFileContentsAsExpectedUsingMoveWithOneSetOfData() throws Exception {
        addDLSets();
        Constant.DATA_DIR.mkdir();
        DLFolder.mkdir();
        dlSaver.saveToFile();
        Scanner lukija = new Scanner(DLSessionFile);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(set3.toString(), tiedostonSisalto);
    }

    @Test
    public void Sessions_SessionFileContentsAsExpectedUsingMoveWithTwoSetsOfData() throws Exception {
        addBenchSets();
        Constant.DATA_DIR.mkdir();
        benchFolder.mkdir();
        benchSaver.saveToFile();
        Scanner lukija = new Scanner(benchSessionFile);
        lukija.useDelimiter("\\Z");
        String fileContents = lukija.next();
        lukija.close();
        assertEquals(set1 + "\n" + set2, fileContents);
    }

}
