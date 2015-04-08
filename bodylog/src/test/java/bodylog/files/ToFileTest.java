package bodylog.files;

import bodylog.logic.Set;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.util.Constant;
import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ToFileTest {

    Session DLsession;
    Session benchSession;
    Move bench;
    Move deadlift;
    String benchName = "bench";
    String DLName = "deadlift";
    String varWeight = "weight";
    String varReps = "repe";
    String dateStr = "1970-01-01";
    Set set1;
    Set set2;
    Set set3;
    File dataFolder;
    File benchFolder;
    File DLFolder;
    File DLSessionFile;
    File benchSessionFile;
    File movesFolder;
    File benchMoveFile;

    @Before
    public void setUp() {
        DLsession = new Session(LocalDate.of(1970, 1, 1));
        benchSession = new Session(LocalDate.of(1970, 1, 1));
        bench = new Move(benchName);
        deadlift = new Move(DLName);
        dataFolder = new File(Constant.SESSION_DIR);
        DLFolder = new File(Constant.SESSION_DIR + "/" + DLName);
        benchFolder = new File(Constant.SESSION_DIR + "/" + benchName);
        DLSessionFile = new File(Constant.SESSION_DIR + "/" + DLName + "/" + dateStr + Constant.SESSION_END);
        benchSessionFile = new File(Constant.SESSION_DIR + "/" + benchName + "/" + dateStr + Constant.SESSION_END);
        movesFolder = new File(Constant.MOVE_DIR);
        benchMoveFile = new File(Constant.MOVE_DIR + "/" + benchName + Constant.MOVE_END);
    }

    private void addDLSets() {
        set3 = new Set();
        set3.addValue(125.0);
        set3.addValue(5);
        deadlift.addVariable(varWeight);
        deadlift.addVariable(varReps);
        DLsession.addSet(set3);
        deadlift.addSession(DLsession);
    }

    private void addBenchSets() {
        set1 = new Set();
        set1.addValue(65.0);
        set1.addValue(10);
        set2 = new Set();
        set2.addValue(55.0);
        set2.addValue(8);
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        benchSession.addSet(set1);
        benchSession.addSet(set2);
        bench.addSession(benchSession);
    }

    /**
     * Deletes all files in folders that are in the data folder, deletes those
     * folders and the data folder. Deletes all files in the move folder and
     * deletes the move folder.
     */
    @After
    public void tearDown() {
        if (dataFolder.exists()) {
            for (File folder : dataFolder.listFiles()) {
                if (folder.isDirectory()) {
                    for (File file : folder.listFiles()) {
                        file.delete();
                    }
                }
                folder.delete();
            }
            dataFolder.delete();
        }

        if (movesFolder.exists()) {
            for (File file : movesFolder.listFiles()) {
                file.delete();
            }
            movesFolder.delete();
        }
    }

    @Test
    public void KansioidenLuontiKahdelleLiikkeelle_KunEiOlemassa() throws Exception {
        ToFile.createDataFolder(deadlift);
        ToFile.createDataFolder(bench);
        assertTrue(DLFolder.exists() && benchFolder.exists());
    }

    @Test
    public void Sessiot_KahdenLiikkeenSessioTiedostojenLuontiOnnistuu() throws Exception {
        addDLSets();
        addBenchSets();
        dataFolder.mkdir();
        DLFolder.mkdir();
        benchFolder.mkdir();
        ToFile.sessions(deadlift);
        ToFile.sessions(bench);
        assertTrue(DLSessionFile.exists() && benchSessionFile.exists());
    }

    @Test
    public void Sessio_YksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        addDLSets();
        dataFolder.mkdir();
        DLFolder.mkdir();
        ToFile.sessions(deadlift);
        Scanner lukija = new Scanner(DLSessionFile);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(set3.toString(), tiedostonSisalto);
    }

    @Test
    public void Sessio_KaksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        addBenchSets();
        dataFolder.mkdir();
        benchFolder.mkdir();
        ToFile.sessions(bench);
        Scanner lukija = new Scanner(benchSessionFile);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(set1 + "\n" + set2, tiedostonSisalto);
    }

    @Test
    public void Liike_KansionLuontiOnnistuu_KunEiAiemminOlemassa() throws Exception {
        if (!movesFolder.exists()) {
            ToFile.createMovesFolder();
        }
        assertTrue(movesFolder.exists());
    }

    @Test
    public void Liike_LiikeTiedostonLuontiOnnistuu() throws Exception {
        movesFolder.mkdir();
        bench.addVariable(varWeight);
        ToFile.move(bench);
        assertTrue(benchMoveFile.exists());
    }

    @Test
    public void Liike_LiikeTiedostonSisaltoOikein() throws Exception {
        movesFolder.mkdir();
        bench.addVariable(varWeight);
        bench.addVariable(varReps);
        ToFile.move(bench);
        Scanner lukija = new Scanner(benchMoveFile);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = "";
        tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(varWeight + "\n" + varReps, tiedostonSisalto);
    }

}
