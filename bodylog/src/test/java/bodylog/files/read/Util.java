/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.logic.datahandling.Delimiters;
import bodylog.logic.datahandling.Moves;
import bodylog.logic.datahandling.Sessions;
import bodylog.logic.datahandling.Sets;
import bodylog.logic.datahandling.Variables;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
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
    public final String[] readingSessionData = {
        Sets.LINE_PREFIX
        + "60.6" + Delimiters.VALUE
        + "true" + Delimiters.VALUE
        + "severe",
        Sets.LINE_PREFIX
        + "null" + Delimiters.VALUE
        + "false" + Delimiters.VALUE
        + "mild"};
    public final String[] readingVarNames = {
        "pages", "looked in the mirror", "body dysmorphia"};
    public final String[] readingVarData = {
        Variables.NAME + readingVarNames[0] + Delimiters.SECTION
        + Variables.TYPE + Type.NUMERICAL + Delimiters.SECTION
        + Variables.CHOICES + "",
        Variables.NAME + readingVarNames[1] + Delimiters.SECTION
        + Variables.TYPE + Type.CHECKBOX + Delimiters.SECTION
        + Variables.CHOICES + "",
        Variables.NAME + readingVarNames[2] + Delimiters.SECTION
        + Variables.TYPE + Type.MANDATORY_CHOICE + Delimiters.SECTION
        + Variables.CHOICES
        + "severe" + Delimiters.VALUE
        + "mild" + Delimiters.VALUE
        + "none",};

    public File skipFolder;
    public File skipSessionONEFile;
    public File skipSessionTWOFile;
    public File skipMoveFile;
    public String skipSessionONEFileContents;
    public String skipSessionTWOFileContents;
    public String skipMoveFileContents;
    public final String skipName = "skipping legs";
    public final String[] skipSessionONEData = {
        Sets.LINE_PREFIX
        + "0" + Delimiters.VALUE
        + "false" + Delimiters.VALUE
        + "tree",
        Sets.LINE_PREFIX
        + "5" + Delimiters.VALUE
        + "true" + Delimiters.VALUE
        + "normal"};
    public final String[] skipSessionTWOData = {
        Sets.LINE_PREFIX
        + "25" + Delimiters.VALUE
        + "true" + Delimiters.VALUE
        + "chicken",
        Sets.LINE_PREFIX
        + "999.99" + Delimiters.VALUE
        + "false" + Delimiters.VALUE
        + Variable.OPT_NO_CHOICE};
    public final String[] skipVarNames = {
        "times skipped", "can walk", "leg size"};
    public final String[] skipVarData = {
        Variables.NAME + skipVarNames[0] + Delimiters.SECTION
        + Variables.TYPE + Type.NUMERICAL + Delimiters.SECTION
        + Variables.CHOICES + "",
        Variables.NAME + skipVarNames[1] + Delimiters.SECTION
        + Variables.TYPE + Type.CHECKBOX + Delimiters.SECTION
        + Variables.CHOICES + "",
        Variables.NAME + skipVarNames[2] + Delimiters.SECTION
        + Variables.TYPE + Type.OPTIONAL_CHOICE + Delimiters.SECTION
        + Variables.CHOICES
        + "tree" + Delimiters.VALUE
        + "chicken" + Delimiters.VALUE
        + "normal",};

    public void compareMoveName(String name, Move move) {
        assertEquals(name, move.toString());
    }

    public void compareVariableNames(String[] varNames, Move move) {
        assertArrayEquals(varNames, move.getVariableNames());
    }

    public void compareDateInSession(String dateStr, Session session) {
        assertEquals(dateStr, session.getFileDateString());
    }

    public void compareSetDataInSession(String[] setData, Session session) {
        int setCount = session.getSetCount();
        for (int i = 0; i < setCount; i++) {
            assertEquals(setData[i],Sets.format(session.getSet(i)));
        }
    }

    public void useSkipLegsFiles() throws Exception {
        Constant.DATA_DIR.mkdir();
        Constant.MOVES_DIR.mkdir();

        skipFolder = new File(Constant.DATA_DIR, skipName);
        skipFolder.mkdir();

        skipSessionONEFile = new File(skipFolder, dateStrONE + Constant.SESSION_END);
        FileWriter sessionWriter = new FileWriter(skipSessionONEFile);
        skipSessionONEFileContents = Sessions.VARS_START;
        for (String str : skipVarData) {
            skipSessionONEFileContents += str + "\n";
        }
        skipSessionONEFileContents += Sessions.SETS_START;
        for (String str : skipSessionONEData) {
            skipSessionONEFileContents += str + "\n";
        }
        skipSessionONEFileContents = skipSessionONEFileContents.substring(
                0, skipSessionONEFileContents.length() - 1);
        sessionWriter.write(skipSessionONEFileContents);
        sessionWriter.close();

        skipSessionTWOFile = new File(skipFolder, dateStrTWO + Constant.SESSION_END);
        sessionWriter = new FileWriter(skipSessionTWOFile);
        skipSessionTWOFileContents = Sessions.VARS_START;
        for (String str : skipVarData) {
            skipSessionTWOFileContents += str + "\n";
        }
        skipSessionTWOFileContents += Sessions.SETS_START;
        for (String str : skipSessionTWOData) {
            skipSessionTWOFileContents += str + "\n";
        }
        skipSessionTWOFileContents = skipSessionTWOFileContents.substring(
                0, skipSessionTWOFileContents.length() - 1);
        sessionWriter.write(skipSessionTWOFileContents);
        sessionWriter.close();

        skipMoveFile = new File(Constant.MOVES_DIR, skipName + Constant.MOVE_END);
        FileWriter variableWriter = new FileWriter(skipMoveFile);
        skipMoveFileContents = Moves.VARS_START;
        for (String str : skipVarData) {
            skipMoveFileContents += str + "\n";
        }
        skipMoveFileContents = skipMoveFileContents.substring(
                0, skipMoveFileContents.length() - 1);
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
        readSessionfileContents = Sessions.VARS_START;
        for (String str : readingVarData) {
            readSessionfileContents += str + "\n";
        }
        readSessionfileContents += Sessions.SETS_START;
        for (String str : readingSessionData) {
            readSessionfileContents += str + "\n";
        }
        readSessionfileContents = readSessionfileContents.substring(
                0, readSessionfileContents.length() - 1);
        sessionWriter.write(readSessionfileContents);
        sessionWriter.close();

        readMoveFile = new File(Constant.MOVES_DIR, readingName + Constant.MOVE_END);
        FileWriter variableWriter = new FileWriter(readMoveFile);
        readMoveFileContents = Moves.VARS_START;
        for (String str : readingVarData) {
            readMoveFileContents += str + "\n";
        }
        readMoveFileContents = readMoveFileContents.substring(
                0, readMoveFileContents.length() - 1);
        variableWriter.write(readMoveFileContents);
        variableWriter.close();
    }

}
