/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.write;

import bodylog.files.Saver;
import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
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
     * Creates a new SessionSaver for the specified Move and adds a new Session
     * to it.
     *
     * @param updater MoveListContainerUpdater that is called to update session
     * views if any are open
     * @param move Move whose session will be saved
     */
    public SessionSaver(MoveListContainerUpdater updater, Move move) {
        super(updater, move);
        this.move.addSession(new Session());
    }

    @Override
    public Move getMove() {
        return move;
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
     * @see bodylog.ui.dataediting.SessionEditor
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
     * Writes the data contained in the Sessions of the t Move into session
     * files. Uses a DateTimeFormatter to format the date of Sessions into a
     * string used for the filename of the session files. Used in the UI class
     * SessionEditor.
     *
     * Writes each Session into one file, writing one line per each Set
     * contained in the Session. Nothing is written if no Sets are found. No
     * files are created if no Session are found.
     *
     * @throws IOException if the file exists but is a directory rather than a
     * regular file, does not exist but cannot be created, or cannot be opened
     * for any other reason
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     * @see bodylog.ui.dataediting.SessionEditor
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     * @see bodylog.files.Constant#DATA_DIR
     * @see bodylog.files.Constant#SESSION_END
     */
    @Override
    public void saveToFile() throws IOException {
        writeToFile();
        updater.updateDisplayer(move);
        move.clearSessions();
    }

    private void writeToFile() throws IOException {
        for (Session session : move.getSessions()) {
            String dateStr = session.getFileDateString();
            File sessionFile = new File(Constant.DATA_DIR,
                    move + "/" + dateStr + Constant.SESSION_END);
            FileWriter writer = new FileWriter(sessionFile);
            for (Set set : session.getSets()) {
                writer.write(set.toString() + "\n");
            }
            writer.close();
        }
    }

}
