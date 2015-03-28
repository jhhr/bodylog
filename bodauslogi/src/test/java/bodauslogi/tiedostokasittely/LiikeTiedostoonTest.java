package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.util.Scanner;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LiikeTiedostoonTest {

    Liike penkki;
    File liikkeetKansio;
    File penkkiTiedosto;

    @Before
    public void setUp() throws Exception {
        penkki = new Liike("penkki");
        liikkeetKansio = new File(Vakiot.LIIKKEET);
        penkkiTiedosto = new File(Vakiot.LIIKKEET + "/penkki.txt");
    }

    @After
    public void tearDown() {
        if (liikkeetKansio.exists()) {
            for (String liikeFilu : liikkeetKansio.list()) {
                new File(Vakiot.LIIKKEET + "/" + liikeFilu).delete();
            }
            liikkeetKansio.delete();
        }
    }

    @Test
    public void LiikkeetKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        if (!liikkeetKansio.exists()) {
            LiikeTiedostoon.luoLiikkeetKansio();
        }
        assertTrue(liikkeetKansio.exists());
    }

    @Test
    public void LiikeTiedostonLuontiOnnistuu() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        LiikeTiedostoon.kirjoita(penkki);
        assertTrue(penkkiTiedosto.exists());
    }

    @Test
    public void LiikeTiedostonSisaltoOikein() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        LiikeTiedostoon.kirjoita(penkki);
        Scanner lukija = new Scanner(penkkiTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = "";
        tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals("paino\ntoistot", tiedostonSisalto);
    }

}
