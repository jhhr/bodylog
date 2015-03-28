package bodauslogi.tiedostokasittely;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TiedostostaSessioTest {

    private File dataKansio;
    private File liikeKansio;
    private File liikkeetKansio;
    private File sessioTiedosto;
    private File liikeTiedosto;

    @Before
    public void setUp() throws Exception {
        dataKansio = new File(Vakiot.DATA);
        dataKansio.mkdir();
        liikeKansio = new File(Vakiot.DATA + "/lehdenluku");
        liikeKansio.mkdir();
        sessioTiedosto = new File(Vakiot.DATA + "/lehdenluku/2014.04.07.txt");
        FileWriter sessioKirjoittaja = new FileWriter(sessioTiedosto);
        sessioKirjoittaja.write("{60.0,5.0}\n{40.0,null}");
        sessioKirjoittaja.close();
        liikkeetKansio = new File(Vakiot.LIIKKEET);
        liikkeetKansio.mkdir();
        liikeTiedosto = new File(Vakiot.LIIKKEET + "/lehdenluku.txt");
        FileWriter muuttujaKirjoittaja = new FileWriter(liikeTiedosto);
        muuttujaKirjoittaja.write("pisteitä\nankka");
        muuttujaKirjoittaja.close();
    }

    @After
    public void tearDown() {
        if (liikeKansio.exists()) {
            for (String liikeFilu : liikeKansio.list()) {
                new File(Vakiot.DATA + "/lehdenluku/" + liikeFilu).delete();
            }
            liikeKansio.delete();
        }
        dataKansio.delete();
        if (liikkeetKansio.exists()) {
            for (String liikeFilu : liikkeetKansio.list()) {
                new File(Vakiot.LIIKKEET + "/" + liikeFilu).delete();
            }
            liikkeetKansio.delete();
        }
    }

    @Test
    public void luodunLiikkeenSarjatOikein() throws Exception {
        assertEquals("[{60.0,5.0}, {40.0,null}]",
                TiedostostaSessio.luo(sessioTiedosto).getSarjat().toString());
    }

    @Test
    public void luodunLiikkeenSessionPaivamaarOikein() throws Exception {
        assertEquals(new SimpleDateFormat(Vakiot.PAIVAFORMAATTI).parse("2014.04.07"),
                TiedostostaSessio.luo(sessioTiedosto).getPaivamaara());
    }
}
