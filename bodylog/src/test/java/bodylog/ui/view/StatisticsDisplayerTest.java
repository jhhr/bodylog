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
import bodylog.logic.datahandling.Moves;
import bodylog.logic.datahandling.Sessions;
import java.util.Scanner;
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

    private static final Util util = new Util();

    @BeforeClass
    public static void oneTimeSetUp() {
        
    }

    private StatisticsDisplayer displayer;
    private Move skipLegs;
    private MoveReader moveReader;
    private SessionReader sessionReader;

    @Before
    public void setUp() {
        displayer = new StatisticsDisplayer();
        moveReader = new MoveReader();
        sessionReader = new SessionReader();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }

    public void useSkipLegsMove() throws Exception {
        util.useSkipLegsFiles();
        skipLegs = Moves.parse(new Scanner(util.skipMoveFile), util.skipName);
        skipLegs.addSession(
                Sessions.parse(new Scanner(util.skipSessionONEFile),
                        Constant.FILE_DATE_FORMAT.parse(util.dateStrONE)));
        skipLegs.addSession(
                Sessions.parse(new Scanner(util.skipSessionTWOFile),
                        Constant.FILE_DATE_FORMAT.parse(util.dateStrTWO)));

    }

    @Test
    public void TablesContentEqualsVariableAndSetContent() throws Exception {
        util.useSkipLegsFiles();
        skipLegs = moveReader.fetchMove(util.skipMoveFile);
        sessionReader.fetchSessionsForMove(skipLegs);

        Move newMove = Moves.parse(new Scanner(util.skipMoveFile), util.skipName);

        JPanel display = displayer.getStatisticsDisplay(newMove);
        String[] dates = new String[]{util.dateStrONE, util.dateStrTWO};
        for (int k = 0; k < display.getComponentCount(); k++) {
            JScrollPane pane = (JScrollPane) display.getComponent(k);
            CompoundBorder cBorder = (CompoundBorder) pane.getBorder();
            TitledBorder tBorder = (TitledBorder) cBorder.getOutsideBorder();
            assertEquals(dates[k], Constant.uiDateToFileDate(tBorder.getTitle()));
            JTable table = (JTable) pane.getViewport().getComponent(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                assertEquals(skipLegs.getVariableName(i), table.getColumnName(i));
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
