/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package bodylog.ui.view;

import bodylog.logic.Move;
import bodylog.files.FromFile;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Window for viewing session data for movements. Contains the UI components
 * responsible for showing the data, MoveTables'. Contains a JSplitPane with the
 * list of Moves and the MoveTables.
 *
 * Code from Oracle's
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java">SplitPaneDemo</a>
 *
 * @see bodylog.ui.view.MoveTables
 * @see javax.swing.JSplitPane
 */
public class StatWindow extends JPanel implements ListSelectionListener {

    private final JPanel tablePanel;
    private final JList list;
    private final JSplitPane splitPane;
    private final String[] moveNameList;
    private final HashMap<String, JPanel> namesToTablesMap;

    /**
     * Constructs a new statistics displaying window. All moves are read from
     * the move folder. Session data is read from the data folder for each move
     * and a MoveTables is created from that. Each move name is linked to its
     * respective MoveTables in a HashMap.
     *
     * @throws FileNotFoundException if any file cannot be found
     * @throws SecurityException if any file cannot be accessed
     */
    public StatWindow() throws FileNotFoundException, SecurityException{
        setLayout(new GridLayout(1, 1));

        Move[] moveArray = FromFile.allMovesWithSessions();

        moveNameList = new String[moveArray.length];
        namesToTablesMap = new HashMap<>();

        for (int i = 0; i < moveArray.length; i++) {
            Move move = moveArray[i];
            String moveName = move.toString();
            moveNameList[i] = moveName;
            namesToTablesMap.put(moveName, new MoveTables(move));
        }

        list = new JList(moveNameList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);
        tablePanel = new JPanel();

        JScrollPane tablesScrollPane = new JScrollPane(tablePanel);

        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, tablesScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(50, 50);
        listScrollPane.setMinimumSize(minimumSize);
        tablesScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        add(splitPane);

        if (moveArray.length == 0) {
            tablePanel.add(new JLabel("Could not find any movements from "
                    + "which to display statistics"));
        } else {
            updateTables(moveNameList[list.getSelectedIndex()]);
        }
    }

    //Listens to the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        updateTables(moveNameList[list.getSelectedIndex()]);
    }

    /**
     * When the list is clicked the statistics window's content is changed to
     * the chosen Move's MoveTables.
     *
     * @param moveName the name of the move used to get the MoveTables from the
     * HashMap
     */
    private void updateTables(String moveName) {
        for (Component oldTables : tablePanel.getComponents()) {
            tablePanel.remove(oldTables);
        }
        tablePanel.add(namesToTablesMap.get(moveName));
        tablePanel.validate();
        tablePanel.repaint();
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}
