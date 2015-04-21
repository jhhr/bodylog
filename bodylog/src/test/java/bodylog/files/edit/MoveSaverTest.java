package bodylog.files.edit;

import bodylog.files.write.MoveSaver;
import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.files.Saver.FileRenameException;
import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;
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
        Delete.filesAndFolders();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
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
    MoveSaver benchSaver;
    MoveListContainerUpdater updater;

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
        updater = new MoveListContainerUpdater();
        benchSaver = new MoveSaver(updater, bench);
    }
    
    @Test
    public void GetsCorrectMove(){
        assertEquals(bench, benchSaver.getMove());
    }

    @Test
    public void MoveFileExistsAfterSaving_FileExistsReturnsTrue() throws Exception {
        benchSaver.saveToFile();
        assertTrue(benchMoveFile.exists() && benchSaver.fileExists());
    }

    @Test
    public void MoveFileContentsAsExpected() throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        bench.addVariable(varPumped);
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNewMove() throws Exception {
        benchSaver = new MoveSaver(updater, new Move());
        benchSaver.saveToFile();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNewMoveNameChangedToAnExistingNameOnMoveFile()
            throws Exception {
        benchSaver = new MoveSaver(updater, newMove);
        newMove.setName(benchName);
        benchMoveFile.createNewFile();
        benchSaver.saveToFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNameChangedToBlank() throws Exception {
        bench.setName("");
        benchSaver.saveToFile();
    }

    @Test
    public void CreatesSessionFolderForNewMove() throws Exception {
        benchSaver = new MoveSaver(updater, newMove);
        newMove.setName(benchName);
        benchSaver.saveToFile();
        assertTrue(benchSessionFolder.exists());
    }

    @Test
    public void FilenamesChangedWhenRenamingMoveWithExistingFiles()
            throws Exception {
        benchMoveFile.createNewFile();
        benchSessionFolder.mkdirs();
        bench.setName(dlName);
        benchSaver.saveToFile();
        assertTrue(!benchMoveFile.exists() && dlMoveFile.exists());
        assertTrue(!benchSessionFolder.exists() && dlSessionFolder.exists());
    }

    @Test
    public void MoveFileContentsAsExpectedAfterRenamingAndOverwriting()
            throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        benchSessionFolder.mkdirs();
        benchSaver.saveToFile();
        bench.addVariable(varPumped);
        bench.setName(dlName);
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(dlMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }

    @Test
    public void MoveFileContentsAsExpectedAfterAddingVariableAndOverwriting()
            throws Exception {
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        benchSaver.saveToFile();
        bench.addVariable(varPumped);
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps + "\n" + varPumped, fileContents);
    }

    @Test(expected = FileRenameException.class)
    public void RenamingThrowsExceptionWhenMoveFileIsDeleted() throws Exception {
        bench.setName(dlName);
        benchMoveFile.delete();
        benchSaver.saveToFile();
    }
    
    @Test(expected = FileRenameException.class)
    public void RenamingThrowsExceptionWhenTargetFileExists() throws Exception {
        bench.setName(dlName);
        dlMoveFile.createNewFile();
        benchSaver.saveToFile();
    }

}
