package bodauslogi.tiedostokasittely;

import bodauslogi.tiedostokasittely.Kansiot;
import bodauslogi.tiedostokasittely.TiedostostaLiikkeenSessio;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiedostostaLiikkeenSessioTest {

    private File dataKansio;
    private File liikeKansio;
    private File liikkeetKansio;
    private File sessioTiedosto;
    private File liikeTiedosto;

    @Before
    public void setUp() throws Exception {
        dataKansio = new File(Kansiot.DATA);
        dataKansio.mkdir();
        liikeKansio = new File(Kansiot.DATA + "/lehdenluku");
        liikeKansio.mkdir();
        sessioTiedosto = new File(Kansiot.DATA + "/lehdenluku/04.07.2014.txt");
        FileWriter sessioKirjoittaja = new FileWriter(sessioTiedosto);
        sessioKirjoittaja.write("{pisteitä:60.0,ankka:5.0}\n{pisteitä:40.0,ankka:null}");
        sessioKirjoittaja.close();
        liikkeetKansio = new File(Kansiot.LIIKKEET);
        liikkeetKansio.mkdir();
        liikeTiedosto = new File(Kansiot.LIIKKEET + "/lehdenluku.txt");
        FileWriter muuttujaKirjoittaja = new FileWriter(liikeTiedosto);
        muuttujaKirjoittaja.write("pisteitä\nankka");
        muuttujaKirjoittaja.close();
    }

    @After
    public void tearDown() {
        if (liikeKansio.exists()) {
            for (String liikeFilu : liikeKansio.list()) {
                new File(Kansiot.DATA + "/lehdenluku/" + liikeFilu).delete();
            }
            liikeKansio.delete();
        }
        dataKansio.delete();
        if (liikkeetKansio.exists()) {
            for (String liikeFilu : liikkeetKansio.list()) {
                new File(Kansiot.LIIKKEET + "/" + liikeFilu).delete();
            }
            liikkeetKansio.delete();
        }
    }

    @Test
    public void luodunLiikkeenNimiOikein() throws Exception {
        assertEquals("lehdenluku", TiedostostaLiikkeenSessio.luo(sessioTiedosto).getLiike().getNimi());
    }

    @Test
    public void luodunLiikkeenSarjatOikein() throws Exception {
        assertEquals("[{pisteitä:60.0,ankka:5.0}, {pisteitä:40.0,ankka:null}]",
                TiedostostaLiikkeenSessio.luo(sessioTiedosto).getLiike().getSarjaLista().toString());
    }

    @Test
    public void luodunLiikkeenMuuttujatOikein() throws Exception {
        assertArrayEquals(new String[]{"pisteitä", "ankka"},
                TiedostostaLiikkeenSessio.luo(sessioTiedosto).getLiike().getMuuttujaJoukko().toArray(new String[0]));
    }

    @Test
    public void luodunLiikkeenSessionPaivamaarOikein() throws Exception {
        assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("04.07.2014"),
                TiedostostaLiikkeenSessio.luo(sessioTiedosto).getPaivamaara());
    }
}
