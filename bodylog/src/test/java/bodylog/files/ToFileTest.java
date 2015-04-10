package bodylog.files;

import bodylog.logic.Set;
import bodylog.logic.Move;
import bodylog.logic.Session;
import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ToFileTest {

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
    Set set1;
    Set set2;
    Set set3;
    File benchFolder;
    File DLFolder;
    File DLSessionFile;
    File benchSessionFile;
    File benchMoveFile;

    @BeforeClass
    public static void oneTimeSetUp(){        
        // Delete files first in case some files are leftover from manual testing.
        Util.deleteFiles();
    }
    @Before
    public void setUp() {
        
        DLsession = new Session(LocalDate.of(1970, 1, 1));
        benchSession = new Session(LocalDate.of(1970, 1, 1));
        bench = new Move(benchName);
        deadlift = new Move(DLName);
        DLFolder = new File(Constant.DATA_DIR_NAME + "/" + DLName);
        benchFolder = new File(Constant.DATA_DIR_NAME + "/" + benchName);
        DLSessionFile = new File(Constant.DATA_DIR_NAME + "/" + DLName + "/" + dateStr + Constant.SESSION_END);
        benchSessionFile = new File(Constant.DATA_DIR_NAME + "/" + benchName + "/" + dateStr + Constant.SESSION_END);
        benchMoveFile = new File(Constant.MOVES_DIR_NAME + "/" + benchName + Constant.MOVE_END);
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
    
    @After
    public void tearDown(){
        Util.deleteFiles();
    }

    @Test
    public void CreatingFoldersWhenNotAlreadyExisting() throws Exception {
        ToFile.createDataFolder(deadlift);
        ToFile.createDataFolder(bench);
        assertTrue(DLFolder.exists() && benchFolder.exists());
    }

    @Test
    public void Sessions_CreatingMoveFiles_SessionFileCheckerReturnsTrue() throws Exception {
        addDLSets();
        addBenchSets();
        Constant.DATA_DIR.mkdir();
        DLFolder.mkdir();
        benchFolder.mkdir();
        ToFile.sessions(deadlift);
        ToFile.sessions(bench);
        assertTrue(DLSessionFile.exists() && ToFile.sessionFileExists(deadlift, dateStr));
        assertTrue(benchSessionFile.exists() && ToFile.sessionFileExists(bench, dateStr));
    }

    @Test
    public void SessionFileCheckerReturnsFalseForNonexistentFile() {
        assertFalse(ToFile.sessionFileExists(bench, dateStr));
    }

    @Test
    public void MoveFileCheckerReturnsFalseForNonexistentFile() {
        assertFalse(ToFile.moveFileExists(bench));
    }

    @Test
    public void Sessions_SessionFileContentsAsExpectedUsingMoveWithOneSetOfData() throws Exception {
        addDLSets();
        Constant.DATA_DIR.mkdir();
        DLFolder.mkdir();
        ToFile.sessions(deadlift);
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
        ToFile.sessions(bench);
        Scanner lukija = new Scanner(benchSessionFile);
        lukija.useDelimiter("\\Z");
        String fileContents = lukija.next();
        lukija.close();
        assertEquals(set1 + "\n" + set2, fileContents);
    }

    @Test
    public void Move_MoveFolderCreationWhenNotAlreadyExisting() throws Exception {
        if (!Constant.MOVES_DIR.exists()) {
            ToFile.createMovesFolder();
        }
        assertTrue(Constant.MOVES_DIR.exists());
    }

    @Test
    public void Move_MoveFileCreation_MoveFileCheckerReturnsTrue() throws Exception {
        Constant.MOVES_DIR.mkdir();
        bench.addVariable(varWeight);
        ToFile.move(bench);
        assertTrue(benchMoveFile.exists() && ToFile.moveFileExists(bench));
    }

    @Test
    public void Move_MoveFileContentsAsExpected() throws Exception {
        Constant.MOVES_DIR.mkdir();
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        bench.addVariable(varBool);
        ToFile.move(bench);
        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varBool, fileContents);
    }

}
