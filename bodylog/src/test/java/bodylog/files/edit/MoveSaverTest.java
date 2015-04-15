package bodylog.files.edit;

import bodylog.files.Constant;
import bodylog.files.Util;
import bodylog.files.edit.MoveSaver;
import bodylog.logic.Move;
import java.io.File;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MoveSaverTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Util.deleteFiles();
    }

    @After
    public void tearDown() {
        Util.deleteFiles();
    }

    Move bench;
    Move deadlift;
    Move newMove;
    String benchName = "bench";
    String dlName = "deadlift";
    String varWeight = "weight";
    String varReps = "reps";
    String varPumped = "pumped";
    File benchMoveFile;
    File dlMoveFile;
    File newSessionFolder;
    File benchSessionFolder;
    File dlSessionFolder;
    MoveSaver saver;

    @Before
    public void setUp() {

        Constant.MOVES_DIR.mkdir();
        bench = new Move(benchName);
        deadlift = new Move(dlName);
        newMove = new Move();
        benchMoveFile = new File(Constant.MOVES_DIR, benchName + Constant.MOVE_END);
        dlMoveFile = new File(Constant.MOVES_DIR, dlName + Constant.MOVE_END);
        benchSessionFolder = new File(Constant.DATA_DIR, benchName);
        dlSessionFolder = new File(Constant.DATA_DIR, dlName);
        saver = new MoveSaver(bench);
    }

    @Test
    public void MoveFileExistsAfterSaving_FileExistsReturnsTrue() throws Exception {
        saver.saveToFile();
        assertTrue(benchMoveFile.exists() && saver.fileExists());
    }

    @Test
    public void MoveFileContentsAsExpected() throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        bench.addVariable(varPumped);
        saver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNewMove() throws Exception {
        saver = new MoveSaver(new Move());
        saver.saveToFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNameChangedToBlank() throws Exception {
        bench.setName("");
        saver.saveToFile();
    }

    @Test
    public void CreatesSessionFolderForNewMove() throws Exception {
        saver = new MoveSaver(newMove);
        newMove.setName(benchName);
        saver.saveToFile();
        assertTrue(benchSessionFolder.exists());
    }

    @Test
    public void FilenamesChangedWhenRenamingMoveWithExistingFiles() throws Exception {
        benchMoveFile.createNewFile();
        benchSessionFolder.mkdirs();
        bench.setName(dlName);
        saver.saveToFile();
        assertTrue(!benchMoveFile.exists() && dlMoveFile.exists());        
        assertTrue(!benchSessionFolder.exists() && dlSessionFolder.exists());
    }
    
    @Test
    public void MoveFileContentsAsExpectedAfterRenamingAndOverwriting() throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        benchSessionFolder.mkdirs();
        saver.saveToFile();
        bench.addVariable(varPumped);
        bench.setName(dlName);
        saver.saveToFile();

        Scanner lukija = new Scanner(dlMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }
    
    @Test
    public void MoveFileContentsAsExpectedAfterAddingVariableAndOverwriting() throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        saver.saveToFile();
        bench.addVariable(varPumped);
        saver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }

}
