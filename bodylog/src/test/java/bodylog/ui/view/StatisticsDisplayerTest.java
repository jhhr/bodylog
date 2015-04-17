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
import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StatisticsDisplayerTest {

    private Util util;
    private StatisticsDisplayer displayer;
    private Move skipLegs;
    private MoveReader moveReader;
    private SessionReader sessionReader;

    @BeforeClass
    public static void oneTimeSetUp() {

    }

    @Before
    public void setUp() {
        this.util = new Util();
        this.displayer = new StatisticsDisplayer();
        this.moveReader = new MoveReader();
        this.sessionReader = new SessionReader();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }

    public void useSkipLegsMove() {
        skipLegs = new Move(util.skipName);
        for (String var : util.skipVarData) {
            skipLegs.addVariable(var);
        }
    }

    public void addSessionsAndVars() {
        Session sesONE = new Session(
                Constant.FILE_DATE_FORMAT.parse(util.dateStrONE));
        for (String line : util.skipSessionONEData) {
            Set set = new Set();
            for (String valueStr : DataHandling.lineToStringArray(line)) {
                set.addValue(valueStr);
            }
            sesONE.addSet(set);
        }
        skipLegs.addSession(sesONE);
        Session sesTWO = new Session(
                Constant.FILE_DATE_FORMAT.parse(util.dateStrTWO));
        for (String line : util.skipSessionTWOData) {
            Set set = new Set();
            for (String valueStr : DataHandling.lineToStringArray(line)) {
                set.addValue(valueStr);
            }
            sesONE.addSet(set);
        }
        skipLegs.addSession(sesTWO);
    }

    @Test
    public void TablesContentEqualsVariableAndSetContent() throws Exception {
        util.useSkipLegsFiles();        
        skipLegs = sessionReader.fetchSessionsForMove(moveReader.fetchMove(util.skipMoveFile));
        
        Move newMove = new Move(util.skipName);
        for (String var : util.skipVarData) {
            newMove.addVariable(var);
        }
        
        JPanel display = displayer.getStatisticsDisplay(newMove);
        String[] dates = new String[]{util.dateStrONE,util.dateStrTWO};
        for (int k = 0; k < display.getComponentCount(); k++) {
            JScrollPane pane = (JScrollPane) display.getComponent(k);
            CompoundBorder cBorder = (CompoundBorder) pane.getBorder();
            TitledBorder tBorder = (TitledBorder) cBorder.getOutsideBorder();
            assertEquals(dates[k], Constant.uiDateToFileDate(tBorder.getTitle()));
            JTable table = (JTable) pane.getViewport().getComponent(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                assertEquals(skipLegs.getVariable(i),table.getColumnName(i));
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    assertEquals(skipLegs.getSession(k).getSet(i).getValue(j),
                            table.getValueAt(i, j));
                }
            }
        }
    }
}
