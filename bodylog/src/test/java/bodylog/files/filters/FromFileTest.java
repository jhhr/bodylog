package bodylog.files.filters;

import bodylog.files.Constant;
import bodylog.files.FromFile;
import bodylog.files.Util;
import bodylog.logic.Move;
import bodylog.logic.Session;
import java.io.File;
import java.io.FileWriter;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FromFileTest {

    private final String dateStr1 = "2014-04-07";
    private final String dateStr2 = "2014-04-08";

    private File readFolder;
    private File readSessionFile;
    private File readMoveFile;
    private String readSessionfileContents;
    private String readMoveFileContents;
    private final String readingName = "readingMensFitness";
    private final String[] readingSessionData = {"{60.6,5,true}", "{40,null,false}"};
    private final String[] readingVarData = {"pages", "looks in the mirror", "body dysmorphia"};

    private File skipFolder;
    private File skipSessionONEFile;
    private File skipSessionTWOFile;
    private File skipMoveFile;
    private String skipSessionONEFileContents;
    private String skipSessionTWOFileContents;
    private String skipMoveFileContents;
    private final String skipName = "skipping legs";
    private final String[] skipSessionONEData = {"{4,12.1}", "{5,12}"};
    private final String[] skipSessionTWOData = {"{99,11.9,true}", "{0,null,false}"};
    private final String[] skipVarData = {"days skipped", "leg size", "can walk"};

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Util.deleteFiles();
    }

    @Before
    public void setUp() throws Exception {

        Constant.DATA_DIR.mkdir();
        Constant.MOVES_DIR.mkdir();
    }

    private void useReadingMove() throws Exception {
        readFolder = new File(Constant.DATA_DIR, readingName);
        readFolder.mkdir();

        readSessionFile = new File(readFolder, dateStr1 + Constant.SESSION_END);
        FileWriter sessionWriter = new FileWriter(readSessionFile);
        readSessionfileContents = "";
        for (String str : readingSessionData) {
            readSessionfileContents += str + "\n";
        }
        sessionWriter.write(readSessionfileContents);
        sessionWriter.close();

        readMoveFile = new File(Constant.MOVES_DIR, readingName + Constant.MOVE_END);
        FileWriter variableWriter = new FileWriter(readMoveFile);
        readMoveFileContents = "";
        for (String str : readingVarData) {
            readMoveFileContents += str + "\n";
        }
        variableWriter.write(readMoveFileContents);
        variableWriter.close();
    }

    private void useSkipLegsMove() throws Exception {
        skipFolder = new File(Constant.DATA_DIR, skipName);
        skipFolder.mkdir();

        skipSessionONEFile = new File(skipFolder, dateStr1 + Constant.SESSION_END);
        FileWriter sessionWriter = new FileWriter(skipSessionONEFile);
        skipSessionONEFileContents = "";
        for (String str : skipSessionONEData) {
            skipSessionONEFileContents += str + "\n";
        }
        sessionWriter.write(skipSessionONEFileContents);
        sessionWriter.close();

        skipSessionTWOFile = new File(skipFolder, dateStr2 + Constant.SESSION_END);
        sessionWriter = new FileWriter(skipSessionTWOFile);
        skipSessionTWOFileContents = "";
        for (String str : skipSessionTWOData) {
            skipSessionTWOFileContents += str + "\n";
        }
        sessionWriter.write(skipSessionTWOFileContents);
        sessionWriter.close();

        skipMoveFile = new File(Constant.MOVES_DIR, skipName + Constant.MOVE_END);
        FileWriter variableWriter = new FileWriter(skipMoveFile);
        skipMoveFileContents = "";
        for (String str : skipVarData) {
            skipMoveFileContents += str + "\n";
        }
        variableWriter.write(skipMoveFileContents);
        variableWriter.close();
    }

    @After
    public void tearDown() {
        Util.deleteFiles();
    }

    private void compareSetDataInSession(String[] setData, Session session) {
        String listContents = "[";
        for (String str : setData) {
            listContents += str + ", ";
        }
        listContents = listContents.substring(0, listContents.length() - 2) + "]";

        assertEquals(listContents, session.getSets().toString());
    }

    private void compareDateInSession(String dateStr, Session session) {
        assertEquals(dateStr,
                session.getFileDateString());
    }

    private void compareMoveName(String name, Move move) {
        assertEquals(name, move.toString());
    }

    private void compareVariableData(String[] varData, Move move) {
        assertArrayEquals(varData, move.getVariables());
    }

    @Test
    public void Session_SetDataSameAsInFile() throws Exception {
        useReadingMove();
        compareSetDataInSession(readingSessionData, FromFile.session(readSessionFile));
    }

    @Test
    public void Session_SessionDateSameAsOnFile() throws Exception {
        useReadingMove();
        compareDateInSession(dateStr1, FromFile.session(readSessionFile));
    }

    @Test
    public void MoveWithoutSessions_MoveNameSameAsOnFile() throws Exception {
        useReadingMove();
        compareMoveName(readingName, FromFile.move(readMoveFile));
    }

    @Test
    public void MoveWithoutSessions_MoveVariablesSameAsInFile() throws Exception {
        useReadingMove();
        compareVariableData(readingVarData, FromFile.move(readMoveFile));
    }

    @Test
    public void MoveWithSessions_MoveNameAndVariablesSameAsFile() throws Exception {
        useSkipLegsMove();
        Move skipLegs = FromFile.moveWithSessions(skipMoveFile);
        compareMoveName(skipName, skipLegs);
        compareVariableData(skipVarData, skipLegs);
    }

    @Test
    public void MoveWithSessions_SessionsDatesSameAsOnFiles() throws Exception {
        useSkipLegsMove();
        Move skipLegs = FromFile.moveWithSessions(skipMoveFile);

        compareDateInSession(dateStr1, skipLegs.getSession(0));
        compareDateInSession(dateStr2, skipLegs.getSession(1));
    }

    @Test
    public void MoveWithSessions_SetDataSameAsOnFiles() throws Exception {
        useSkipLegsMove();
        Move skipLegs = FromFile.moveWithSessions(skipMoveFile);

        compareSetDataInSession(skipSessionONEData, skipLegs.getSession(0));
        compareSetDataInSession(skipSessionTWOData, skipLegs.getSession(1));
    }

    @Test
    public void AllMovesWithSessions_MoveNamesAndVariablesSameAsOnFiles() throws Exception {
        useReadingMove();
        useSkipLegsMove();

        Move[] moves = FromFile.allMovesWithSessions();

        compareMoveName(readingName, moves[0]);
        compareMoveName(skipName, moves[1]);
        compareVariableData(readingVarData, moves[0]);
        compareVariableData(skipVarData, moves[1]);
    }

    @Test
    public void AllMovesWithSessions_SessionsDatesSameAsOnFiles() throws Exception {
        useReadingMove();
        useSkipLegsMove();

        Move[] moves = FromFile.allMovesWithSessions();

        compareDateInSession(dateStr1, moves[0].getSession(0));
        compareDateInSession(dateStr1, moves[1].getSession(0));
        compareDateInSession(dateStr2, moves[1].getSession(1));
    }

    @Test
    public void AllMovesWithSessions_SetDataSameAsOnFiles() throws Exception {
        useReadingMove();
        useSkipLegsMove();

        Move[] moves = FromFile.allMovesWithSessions();

        compareSetDataInSession(readingSessionData, moves[0].getSession(0));
        compareSetDataInSession(skipSessionONEData, moves[1].getSession(0));
        compareSetDataInSession(skipSessionTWOData, moves[1].getSession(1));
    }

    @Test
    public void AllMovesWithoutSessions_MoveNamesAndVariablesSameAsOnFiles() throws Exception {
        useReadingMove();
        useSkipLegsMove();

        Move[] moves = FromFile.allMovesWithoutSessions();

        compareMoveName(readingName, moves[0]);
        compareMoveName(skipName, moves[1]);
        compareVariableData(readingVarData, moves[0]);
        compareVariableData(skipVarData, moves[1]);
    }

    @Test
    public void moveFileList_FileListAsExpected() throws Exception {
        useReadingMove();
        useSkipLegsMove();

        File[] files = FromFile.moveFileList();

        assertArrayEquals(new File[]{readMoveFile, skipMoveFile}, files);

    }

}
