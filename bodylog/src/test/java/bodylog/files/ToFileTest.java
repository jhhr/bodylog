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
    File dataFolder;
    File benchFolder;
    File DLFolder;
    File DLSessionFile;
    File benchSessionFile;
    File movesFolder;
    File benchMoveFile;

    @Before
    public void setUp() {
        DLsession = new Session(LocalDate.of(1970, 1, 1));
        benchSession = new Session(LocalDate.of(1970, 1, 1));
        bench = new Move(benchName);
        deadlift = new Move(DLName);
        dataFolder = new File(Constant.SESSION_DIR);
        DLFolder = new File(Constant.SESSION_DIR + "/" + DLName);
        benchFolder = new File(Constant.SESSION_DIR + "/" + benchName);
        DLSessionFile = new File(Constant.SESSION_DIR + "/" + DLName + "/" + dateStr + Constant.SESSION_END);
        benchSessionFile = new File(Constant.SESSION_DIR + "/" + benchName + "/" + dateStr + Constant.SESSION_END);
        movesFolder = new File(Constant.MOVE_DIR);
        benchMoveFile = new File(Constant.MOVE_DIR + "/" + benchName + Constant.MOVE_END);
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

    /**
     * Deletes all files in folders that are in the data folder, deletes those
     * folders and the data folder. Deletes all files in the move folder and
     * deletes the move folder.
     */
    @After
    public void tearDown() {
        if (dataFolder.exists()) {
            for (File folder : dataFolder.listFiles()) {
                if (folder.isDirectory()) {
                    for (File file : folder.listFiles()) {
                        file.delete();
                    }
                }
                folder.delete();
            }
            dataFolder.delete();
        }

        if (movesFolder.exists()) {
            for (File file : movesFolder.listFiles()) {
                file.delete();
            }
            movesFolder.delete();
        }
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
        dataFolder.mkdir();
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
        dataFolder.mkdir();
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
        dataFolder.mkdir();
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
        if (!movesFolder.exists()) {
            ToFile.createMovesFolder();
        }
        assertTrue(movesFolder.exists());
    }

    @Test
    public void Move_MoveFileCreation_MoveFileCheckerReturnsTrue() throws Exception {
        movesFolder.mkdir();
        bench.addVariable(varWeight);
        ToFile.move(bench);
        assertTrue(benchMoveFile.exists() && ToFile.moveFileExists(bench));
    }

    @Test
    public void Move_MoveFileContentsAsExpected() throws Exception {
        movesFolder.mkdir();
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
