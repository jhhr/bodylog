/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.logic.Session;
import java.io.File;
import java.io.FileWriter;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Util {
    public final String dateStrONE = "2014-04-07";
    public final String dateStrTWO = "2014-04-08";

    public File readFolder;
    public File readSessionFile;
    public File readMoveFile;
    public String readSessionfileContents;
    public String readMoveFileContents;
    public final String readingName = "readingMensFitness";
    public final String[] readingSessionData = {"{60.6,5,true}", "{40,null,false}"};
    public final String[] readingVarData = {"pages", "looks in the mirror", "body dysmorphia"};

    public File skipFolder;
    public File skipSessionONEFile;
    public File skipSessionTWOFile;
    public File skipMoveFile;
    public String skipSessionONEFileContents;
    public String skipSessionTWOFileContents;
    public String skipMoveFileContents;
    public final String skipName = "skipping legs";
    public final String[] skipSessionONEData = {"{4,12.1}", "{5,12}"};
    public final String[] skipSessionTWOData = {"{99,11.9,true}", "{0,null,false}"};
    public final String[] skipVarData = {"days skipped", "leg size", "can walk"};

    public void compareMoveName(String name, Move move) {
        assertEquals(name, move.toString());
    }

    public void compareVariableData(String[] varData, Move move) {
        assertArrayEquals(varData, move.getVariableNames());
    }

    public void compareDateInSession(String dateStr, Session session) {
        assertEquals(dateStr, session.getFileDateString());
    }

    public void compareSetDataInSession(String[] setData, Session session) {
        String listContents = "[";
        for (String str : setData) {
            listContents += str + ", ";
        }
        listContents = listContents.substring(0, listContents.length() - 2) + "]";
        assertEquals(listContents, session.getSets().toString());
    }

    public void useSkipLegsFiles() throws Exception {        
        Constant.DATA_DIR.mkdir();
        Constant.MOVES_DIR.mkdir();
        
        skipFolder = new File(Constant.DATA_DIR, skipName);
        skipFolder.mkdir();
        skipSessionONEFile = new File(skipFolder, dateStrONE + Constant.SESSION_END);
        FileWriter sessionWriter = new FileWriter(skipSessionONEFile);
        skipSessionONEFileContents = "";
        for (String str : skipSessionONEData) {
            skipSessionONEFileContents += str + "\n";
        }
        sessionWriter.write(skipSessionONEFileContents);
        sessionWriter.close();
        skipSessionTWOFile = new File(skipFolder, dateStrTWO + Constant.SESSION_END);
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

    public void useReadingFiles() throws Exception {        
        Constant.DATA_DIR.mkdir();
        Constant.MOVES_DIR.mkdir();
        
        readFolder = new File(Constant.DATA_DIR, readingName);
        readFolder.mkdir();
        readSessionFile = new File(readFolder, dateStrONE + Constant.SESSION_END);
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

}
