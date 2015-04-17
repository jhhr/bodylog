package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.logic.Move;
import java.io.File;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MoveReaderTest {

    private Util util;
    private MoveReader reader;

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Delete.filesAndFolders();
    }

    @Before
    public void setUp() throws Exception {
        util = new Util();
        reader = new MoveReader();
    }


    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }


    @Test
    public void MoveWithoutSessions_MoveNameSameAsOnFile() throws Exception {
        util.useReadingFiles();
        util.compareMoveName(util.readingName, reader.fetchMove(util.readMoveFile));
    }

    @Test
    public void MoveWithoutSessions_MoveVariablesSameAsInFile() throws Exception {
        util.useReadingFiles();
        util.compareVariableData(util.readingVarData, reader.fetchMove(util.readMoveFile));
    }

    @Test
    public void AllMovesWithoutSessions_MoveNamesAndVariablesSameAsOnFiles() 
            throws Exception {
        util.useReadingFiles();
        util.useSkipLegsFiles();

        Move[] moves = reader.fetchAllMoves();

        util.compareMoveName(util.readingName, moves[0]);
        util.compareMoveName(util.skipName, moves[1]);
        util.compareVariableData(util.readingVarData, moves[0]);
        util.compareVariableData(util.skipVarData, moves[1]);
    }

    @Test
    public void moveFileList_FileListAsExpected() throws Exception {
        util.useReadingFiles();
        util.useSkipLegsFiles();

        File[] files = reader.fetchMoveFileList();

        assertArrayEquals(new File[]{util.readMoveFile, util.skipMoveFile}, files);

    }

}
