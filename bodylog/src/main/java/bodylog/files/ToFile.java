package bodylog.files;

import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import java.io.File;
import java.io.FileWriter;

public class ToFile {

    public static void createMovesFolder() throws Exception {
        new File(Constant.MOVE_DIR).mkdir();
    }

    public static void createDataFolder(Move move) throws Exception {
        new File(Constant.SESSION_DIR).mkdir();
        new File(Constant.SESSION_DIR + "/" + move).mkdir();
    }
    
    public static boolean moveFileExists(Move move){
        return new File(Constant.MOVE_DIR +"/"+ move + Constant.MOVE_END).exists();
    }
    
    public static boolean sessionFileExists(Move move, String dateStr){
        return new File(Constant.SESSION_DIR +"/"+ move + "/"+ dateStr + Constant.SESSION_END).exists();
    }

    public static void move(Move move) throws Exception {
        File moveFile = new File(Constant.MOVE_DIR + "/" + move + Constant.MOVE_END);
        FileWriter writer = new FileWriter(moveFile);
        for (String variable : move.variablesToArray()) {
            writer.write(variable + "\n");
        }
        writer.close();
    }

    public static void sessions(Move move) throws Exception {

        for (Session sessio : move.getSessions()) {
            String pvmteksti = Constant.FILE_DATE_FORMATTER.format(sessio.getDate());
            File sessioTiedosto = new File(Constant.SESSION_DIR + "/" + move + "/" + pvmteksti + Constant.SESSION_END);
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Set sarja : sessio.getSets()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }

}
