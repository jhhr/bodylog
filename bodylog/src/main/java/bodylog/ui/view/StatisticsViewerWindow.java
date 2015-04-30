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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

        //create the MoveListContainer for this window and add it to the updater
        MoveSelector selector = new MoveSelector(this, updater.newMoveList());
        this.moveListContainer = selector;
        updater.addContainer(moveListContainer);

        //the manager for the views displayed in this window
        this.displayer = new StatisticsDisplayer();
        updater.setDisplayer(displayer);

        //the panel which holds the displays   
        tablePanel = new JPanel();

        //the panel that will hold the reload button
        JPanel buttonPanel = createButtonPanel(selector);

        //the panel holding the buttonPanel and tablePanel
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;    
        viewPanel.add(buttonPanel,c);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_START;
        viewPanel.add(tablePanel,c);

        //the scroll pane holding the move list, left side of the split pane
        JScrollPane listScrollPane = new JScrollPane(selector.getJList());

        //the scroll Pane holding the viewPanel, right side of the split pane
        JScrollPane viewScrollPane = new JScrollPane(viewPanel);

        //provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(50, 150);
        listScrollPane.setMinimumSize(minimumSize);
        viewScrollPane.setMinimumSize(minimumSize);

        //create the split pane with the two scroll panes in it
        JSplitPane splitPane = createSplitPane(listScrollPane, viewScrollPane);
        setViewportView(splitPane);

        //add default content to the tablePanel
        tablePanel.add(new JLabel("Could not find any movements from "
                + "which to display statistics"));

        //get the display for the first move, if null, the above is displayed
        moveSelectedAction(selector.getSelectedMove());
    }
    
    private JPanel createButtonPanel(final MoveSelector selector){
        JPanel buttonPanel = new JPanel();
        //create and add the button to the panel
        JButton reloadButton = new JButton("Reload");
        reloadButton.addActionListener(new ActionListener() {
            //resets and reloads the display of the currectly selected Move
            @Override
            public void actionPerformed(ActionEvent e) {
                Move move = selector.getSelectedMove();
                displayer.resetDisplay(move);
                moveSelectedAction(move);
            }

        });
        buttonPanel.add(reloadButton);
        
        return buttonPanel;
    }

    private JSplitPane createSplitPane(JScrollPane left, JScrollPane right) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                left, right);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //provide a preferred size for the split pane
        splitPane.setPreferredSize(new Dimension(400, 400));

        return splitPane;
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
     * the chosen Move's display.
     *
     * @param move the move used to get the display
     *
     * @see bodylog.ui.view.StatisticsDisplayer#getStatisticsDisplay
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
        validate();
        repaint();
    }
}
