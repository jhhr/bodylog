/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.view;

import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.files.read.MoveReader;
import bodylog.files.read.SessionReader;
import bodylog.files.read.Util;
import bodylog.logic.Move;
import bodylog.logic.Variable;
import bodylog.logic.datahandling.Moves;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class StatisticsDisplayerTest {

    private static final Util util = new Util();

    private static final StatisticsDisplayer displayer = new StatisticsDisplayer();
    private static final MoveReader moveReader = new MoveReader();
    private static final SessionReader sessionReader = new SessionReader();
    private static final String[] dates = new String[]{util.dateStrONE, util.dateStrTWO};
    private static Move skipLegs;
    private static String[] displayDates;
    private static String[] displayVarNames;
    private static Class[] displayColumnClasses;
    private static Object[] displaySessions;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        util.useSkipLegsFiles();
        skipLegs = moveReader.fetchMove(util.skipMoveFile);
        sessionReader.fetchSessionsForMove(skipLegs);

        Move newMove = Moves.parse(new Scanner(util.skipMoveFile), util.skipName);

        JPanel display = displayer.getStatisticsDisplay(newMove);
        int componentCount = display.getComponentCount();
        displaySessions = new Object[componentCount];
        displayDates = new String[componentCount];
        for (int sesNo = 0; sesNo < componentCount; sesNo++) {
            JScrollPane pane = (JScrollPane) display.getComponent(sesNo);
            CompoundBorder cBorder = (CompoundBorder) pane.getBorder();
            TitledBorder tBorder = (TitledBorder) cBorder.getOutsideBorder();

            displayDates[sesNo] = Constant.uiDateToFileDate(tBorder.getTitle());

            JTable table = (JTable) pane.getViewport().getComponent(0);
            TableModel model = table.getModel();
            int columnCount = model.getColumnCount();
            displayVarNames = new String[columnCount];
            displayColumnClasses = new Class[columnCount];
            for (int varNo = 0; varNo < columnCount; varNo++) {
                displayVarNames[varNo] = table.getColumnName(varNo);
                displayColumnClasses[varNo] = table.getColumnClass(varNo);
            }
            int rowCount = model.getRowCount();
            Object[] sets = new Object[rowCount];
            for (int setNo = 0; setNo < rowCount; setNo++) {
                Object[] values = new Object[columnCount];
                for (int varNo = 0; varNo < columnCount; varNo++) {
                    values[varNo] = table.getValueAt(setNo, varNo);
                }
                sets[setNo] = values;
            }
            displaySessions[sesNo] = sets;
        }
    }

    @AfterClass
    public static void tearDown() {
        Delete.filesAndFolders();
    }

    @Test
    public void DisplayDatesAsExpected() {
        assertArrayEquals(dates, displayDates);
    }
    
    @Test
    public void DisplayVariableNamesAsExpected() {
        assertArrayEquals(skipLegs.getVariableNames(), displayVarNames);
    }
    
     @Test
    public void DisplayColumnClassesAsExpected() {
        int varCount = skipLegs.getVariableCount();
        Class[] varClasses = new Class[varCount];
         for (int varNo = 0; varNo < varCount; varNo++) {
             varClasses[varNo] = skipLegs.getVariable(varNo).getAllowedClass();
         }
        assertArrayEquals(varClasses, displayColumnClasses);
    }

    @Test
    public void DisplaySetDataAsExpected() {
        int sesCount = displaySessions.length;
        for (int sesNo = 0; sesNo < sesCount; sesNo++) {
            Object[] sets = (Object[]) displaySessions[sesNo];
            int setCount = sets.length;
            for (int setNo = 0; setNo < setCount; setNo++) {
                Object[] values = (Object[]) sets[setNo];
                assertArrayEquals(
                        skipLegs.getSession(sesNo).getSet(setNo).toArray(),
                        values);
            }
        }
    }
}
