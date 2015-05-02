/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.write;

import bodylog.files.abstracts.Saver;
import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.datahandling.Sessions;
import bodylog.ui.MoveListContainerUpdater;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for encapsulating the saving of a <code>Session</code> to file and
 * editing files relating to a <code>Session</code>.
 */
public class SessionSaver extends Saver {

    /**
     * Creates a new SessionSaver for the specified Move.
     *
     * @param updater MoveListContainerUpdater that is called to update session
     * displays if any are open
     * @param move Move whose session will be saved
     */
    public SessionSaver(MoveListContainerUpdater updater, Move move) {
        super(updater, move);
    }

    /**
     * Checks if the specified Move has a session file of specified date. The
     * filename of the session file is the same as the given date string with an
     * added session file ending. The file is contained in a folder named after
     * the Move in the statistics folder. Used in the UI class SessionEditor.
     *
     * existence will be checked
     *
     * @return true if the file exists, false otherwise
     * @see bodylog.ui.edit.session.SessionEditor
     * @see bodylog.files.Constant#DATA_DIR
     * @see bodylog.files.Constant#SESSION_END
     */
    @Override
    public boolean fileExists() {
        String dateStr = move.getSession(0).getFileDateString();
        return new File(Constant.DATA_DIR,
                move + "/" + dateStr + Constant.SESSION_END).exists();
    }

    /**
     * Writes the data contained in the Sessions of the Move into session files.
     * Used in the UI class SessionEditor. Writes each Session into one file.
     *
     * @throws IOException if the file exists but is a directory rather than a
     * regular file, does not exist but cannot be created, or cannot be opened
     * for any other reason
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     * @see bodylog.ui.edit.session.SessionEditor
     * @see bodylog.files.Constant#DATA_DIR
     * @see bodylog.files.Constant#SESSION_END
     */
    @Override
    public void saveToFile() throws IOException {
        writeToFile();
        updater.updateDisplayer(move);
    }

    private void writeToFile() throws IOException {
        for (Session session : move.getSessions()) {

            String dateStr = session.getFileDateString();
            File sessionFile = new File(Constant.DATA_DIR,
                    move.getName() + "/" + dateStr + Constant.SESSION_END);
            FileWriter writer = new FileWriter(sessionFile);

            writer.write(Sessions.format(session));
            writer.close();
        }
    }

}
