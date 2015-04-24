package bodylog.files.edit;

import bodylog.files.write.MoveSaver;
import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.logic.Move;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.logic.datahandling.Delimiters;
import bodylog.logic.datahandling.Moves;
import bodylog.logic.datahandling.Variables;
import bodylog.logic.exceptions.FileRenameException;
import bodylog.logic.exceptions.NameNotAllowedException;
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
    
    private final Util util = new Util();

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Delete.filesAndFolders();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }
    
    final String moveFileContents = Moves.VARS_START
            + Variables.format(util.varWeight) + "\n"
            + Variables.format(util.varReps) + "\n"
            + Variables.format(util.varPumped) + "\n"
            + Variables.format(util.varJacked) + "\n"
            + Variables.format(util.varProg);
   
    final File benchMoveFile = 
            new File(Constant.MOVES_DIR, util.benchName + Constant.MOVE_END);
    final File dlMoveFile = 
            new File(Constant.MOVES_DIR, util.dlName + Constant.MOVE_END);
    File benchFolder = new File(Constant.DATA_DIR, util.benchName);
    File dlFolder = new File(Constant.DATA_DIR, util.dlName);
    MoveSaver benchSaver;
    MoveListContainerUpdater updater;

    @Before
    public void setUp() {

        Constant.MOVES_DIR.mkdir();
        util.bench = new Move(util.benchName);
        util.deadlift = new Move(util.dlName);
        util.newMove = new Move();
        updater = new MoveListContainerUpdater();
        benchSaver = new MoveSaver(updater, util.bench);
    }

    @Test
    public void GetsCorrectMove() {
        assertEquals(util.bench, benchSaver.getMove());
    }

    @Test
    public void MoveFileExistsAfterSaving_FileExistsReturnsTrue() throws Exception {
        benchSaver.saveToFile();
        assertTrue(benchMoveFile.exists() && benchSaver.fileExists());
    }

    @Test
    public void MoveFileContentsAsExpected() throws Exception {
        for (Variable var : util.vars) {
            util.bench.addVariable(var);
        }
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = lukija.next();
        lukija.close();
        assertEquals(moveFileContents, fileContents);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNewMove() throws Exception {
        benchSaver = new MoveSaver(updater, new Move());
        benchSaver.saveToFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNewMoveNameChangedToAnExistingNameOnMoveFile()
            throws Exception {
        benchSaver = new MoveSaver(updater, util.newMove);
        util.newMove.setName(util.benchName);
        benchMoveFile.createNewFile();
        benchSaver.saveToFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void CannotSaveWithNameChangedToBlank() throws Exception {
        util.bench.setName("");
        benchSaver.saveToFile();
    }

    @Test
    public void CreatesSessionFolderForNewMove() throws Exception {
        benchSaver = new MoveSaver(updater, util.newMove);
        util.newMove.setName(util.benchName);
        benchSaver.saveToFile();
        assertTrue(benchFolder.exists());
    }

    @Test
    public void FilenamesChangedWhenRenamingMoveWithExistingFiles()
            throws Exception {
        benchMoveFile.createNewFile();
        benchFolder.mkdirs();
        util.bench.setName(util.dlName);
        benchSaver.saveToFile();
        assertTrue(!benchMoveFile.exists() && dlMoveFile.exists());
        assertTrue(!benchFolder.exists() && dlFolder.exists());
    }

    @Test
    public void MoveFileContentsAsExpectedAfterRenamingAndOverwriting()
            throws Exception {
        util.bench.addVariable(util.varWeight);
        util.bench.addVariable(util.varReps);
        util.bench.addVariable(util.varPumped);
        benchFolder.mkdirs();
        benchSaver.saveToFile();
        util.bench.addVariable(util.varJacked);
        util.bench.addVariable(util.varProg);
        util.bench.setName(util.dlName);
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(dlMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(moveFileContents, fileContents);
    }

    @Test
    public void MoveFileContentsAsExpectedAfterAddingMoreVariablesAndOverwriting()
            throws Exception {
        util.bench.addVariable(util.varWeight);
        util.bench.addVariable(util.varReps);
        util.bench.addVariable(util.varPumped);
        benchFolder.mkdirs();
        benchSaver.saveToFile();
        util.bench.addVariable(util.varJacked);
        util.bench.addVariable(util.varProg);
        benchSaver.saveToFile();

        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String fileContents = "";
        fileContents = lukija.next();
        lukija.close();
        assertEquals(moveFileContents, fileContents);
    }

    @Test(expected = FileRenameException.class)
    public void RenamingThrowsExceptionWhenMoveFileIsDeleted() throws Exception {
        util.bench.setName(util.dlName);
        benchMoveFile.delete();
        benchSaver.saveToFile();
    }

    @Test(expected = FileRenameException.class)
    public void RenamingThrowsExceptionWhenTargetFileExists() throws Exception {
        util.bench.setName(util.dlName);
        dlMoveFile.createNewFile();
        benchSaver.saveToFile();
    }

}
