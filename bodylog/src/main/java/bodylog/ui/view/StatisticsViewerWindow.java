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
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import bodylog.ui.MoveListContainer;
import bodylog.ui.MoveListContainerUpdater;
import bodylog.ui.WindowWithMoveListContainer;
import java.awt.Component;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * Window for viewing session data for movements. Contains the UI components
 * responsible for showing the data, StatisticsDisplayer'. Contains a JSplitPane
 * with the list of Moves and the StatisticsDisplayer.
 *
 * Code from Oracle's
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java">SplitPaneDemo</a>
 *
 * @see bodylog.ui.view.StatisticsDisplayer
 * @see javax.swing.JSplitPane
 */
public class StatisticsViewerWindow extends WindowWithMoveListContainer {

    private final StatisticsDisplayer displayer;
    private final JPanel tablePanel;

    /**
     * Constructs a new statistics displaying window. All moves are read from
     * the move folder. Session data is read from the data folder for each move
     * and a StatisticsDisplayer is created from that. Each move name is linked
     * to its respective StatisticsDisplayer in a HashMap.
     *
     * @param updater MoveListContainerUpdater to which is given the
     * MoveListContainer of this window, the JList
     * @throws FileNotFoundException if any file cannot be found
     * @throws SecurityException if any file cannot be accessed
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public StatisticsViewerWindow(MoveListContainerUpdater updater)
            throws FileNotFoundException, SecurityException, ParsingException,
            VariableStateException {
        super(updater);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        MoveSelector selector = new MoveSelector(this, updater.newMoveList());
        this.moveListContainer = selector;
        updater.addContainer(moveListContainer);
        
        this.displayer = new StatisticsDisplayer();
        updater.setDisplayer(displayer);

        JScrollPane listScrollPane = new JScrollPane(selector.getJList());
        tablePanel = new JPanel();
        JScrollPane tablesScrollPane = new JScrollPane(tablePanel);

        //Create a split pane with the two scroll panes in it.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, tablesScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(50, 150);
        listScrollPane.setMinimumSize(minimumSize);
        tablesScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        setViewportView(splitPane);

        tablePanel.add(new JLabel("Could not find any movements from "
                + "which to display statistics"));

        moveSelectedAction(selector.getSelectedMove());
    }

    @Override
    public MoveListContainer getListContainer() {
        return moveListContainer;
    }

    @Override
    public MoveListContainerUpdater getUpdater() {
        return updater;
    }

    /**
     * When the list is clicked the statistics window's content is changed to
     * the chosen Move's StatisticsDisplayer.
     *
     * @param move the move used to get the StatisticsDisplayer from the HashMap
     */
    @Override
    public void moveSelectedAction(Move move) {
        if (move == null) {
            return;
        }
        for (Component previousTables : tablePanel.getComponents()) {
            tablePanel.remove(previousTables);
        }
        try {
            tablePanel.add(displayer.getStatisticsDisplay(move));
        } catch (FileNotFoundException | ParsingException |
                VariableStateException ex) {
            Logger.getLogger(StatisticsViewerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablePanel.validate();
        tablePanel.repaint();
    }
}
