package bodylog.files;

import bodylog.files.FromFile;
import bodylog.util.Constant;
import bodylog.logic.Move;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FromFileTest {

    private File dataFolder;
    private File moveFolder;
    private File movesFolder;
    private File sessionFile;
    private File moveFile;

    @Before
    public void setUp() throws Exception {
        dataFolder = new File(Constant.SESSION_DIR);
        dataFolder.mkdir();
        moveFolder = new File(Constant.SESSION_DIR + "/readingMensFitness");
        moveFolder.mkdir();
        sessionFile = new File(Constant.SESSION_DIR + "/readingMensFitness/2014-04-07" + Constant.SESSION_END);
        FileWriter sessioKirjoittaja = new FileWriter(sessionFile);
        sessioKirjoittaja.write("{60,5}\n{40,null}");
        sessioKirjoittaja.close();
        movesFolder = new File(Constant.MOVE_DIR);
        movesFolder.mkdir();
        moveFile = new File(Constant.MOVE_DIR + "/readingMensFitness" + Constant.MOVE_END);
        FileWriter muuttujaKirjoittaja = new FileWriter(moveFile);
        muuttujaKirjoittaja.write("pages\nlooks in the mirror");
        muuttujaKirjoittaja.close();
    }

    /**
     * Deletes all files in folders that are in the data folder, deletes those
     * folders and the data folder. Deletes all files in the move folder and
     * deletes the move folder.
     */
    @After
    public void tearDown() {
        if (dataFolder.exists()) {
            for (File foler : dataFolder.listFiles()) {
                if (foler.isDirectory()) {
                    for (File file : foler.listFiles()) {
                        file.delete();
                    }
                }
                foler.delete();
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
    public void Session_SetsContainTheValuesContainedInTheFile() throws Exception {
        assertEquals("[{60,5}, {40,null}]",
                FromFile.session(sessionFile).getSets().toString());
    }

    @Test
    public void Session_DateOfSessionIsSameAsDateParsedFromFile() throws Exception {
        assertEquals(LocalDate.from(Constant.FILE_DATE_FORMATTER.parse("2014-04-07")),
                FromFile.session(sessionFile).getDate());
    }

    @Test
    public void Move_NameOfMoveSameAsOnFile() throws Exception {
        Move liike = FromFile.moveWithoutSessions(moveFile);
        assertEquals("readingMensFitness", liike.toString());
    }

    @Test
    public void Move_MoveContainsSameVariablesAsInFile() throws Exception {
        Move liike = FromFile.moveWithoutSessions(moveFile);
        assertArrayEquals(new String[]{"pages", "looks in the mirror"}, liike.variablesToArray());
    }

}
