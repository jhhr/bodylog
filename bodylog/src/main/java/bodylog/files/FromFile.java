package bodylog.files;

import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import java.io.File;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

public class FromFile {

    public static Move moveWithoutSessions(File moveFile) throws Exception {
        Scanner scanner = new Scanner(moveFile);
        String name = moveFile.getName();
        name = name.substring(0, name.length() - 4);
        Move move = new Move(name);
        while (scanner.hasNextLine()) {
            move.addVariable(scanner.nextLine());
        }
        scanner.close();
        return move;
    }

    private static Set setForSession(Scanner scanner) {
        Set set = new Set();
        String row = scanner.nextLine();
        row = row.substring(row.indexOf("{") + 1, row.indexOf("}"));
        String[] valuesArray = row.split(",");
        for (String arvo : valuesArray) {
            set.addValue(DataHandling.stringToSetValue(arvo));
        }
        return set;
    }

    private static TemporalAccessor dateForSession(File sessionFile) {
        String dateStr = sessionFile.getName();
        dateStr = dateStr.substring(0, dateStr.length() - 4);
        return Constant.FILE_DATE_FORMATTER.parse(dateStr);
    }

    public static Session session(File sessionFile) throws Exception {
        Scanner scanner = new Scanner(sessionFile);
        Session session = new Session(dateForSession(sessionFile));
        while (scanner.hasNextLine()) {
            session.addSet(setForSession(scanner));
        }
        scanner.close();
        return session;
    }

    public static Move moveWithSessions(File moveFile) throws Exception {
        Move move = moveWithoutSessions(moveFile);
        File moveDataFolder = new File(Constant.SESSION_DIR + "/" + move);
        moveDataFolder.mkdir();
        for (File sessionFile : moveDataFolder.listFiles(new SessionFileFilter())) {
            move.addSession(session(sessionFile));
        }
        return move;
    }

    public static Move[] allMovesWithSessions() throws Exception {
        File[] moveFileList = moveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = moveWithSessions(moveFileList[i]);
        }
        return moveList;
    }

    public static File[] moveFileList() throws Exception {
        File movesFolder = new File(Constant.MOVE_DIR);
        movesFolder.mkdir();
        return movesFolder.listFiles(new MoveFileFilter());
    }

    public static Move[] allMovesWithoutSessions() throws Exception {
        File[] moveFileList = moveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = moveWithoutSessions(moveFileList[i]);
        }
        return moveList;
    }

    public static String[] allMoveNames() throws Exception {
        File[] moveFileList = moveFileList();
        String[] moveNameList = new String[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            String moveFileName = moveFileList[i].getName();
            moveNameList[i] = moveFileName.substring(0, moveFileName.length() - 4);
        }
        return moveNameList;
    }
}
