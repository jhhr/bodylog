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
    LiikeTiedostoon tallennus;

    @Before
    public void setUp() throws Exception {
        penkki = new Liike("penkki");
        liikkeetKansio = new File(Kansiot.LIIKKEET);
        penkkiTiedosto = new File(Kansiot.LIIKKEET + "/penkki.txt");
        tallennus = new LiikeTiedostoon(penkki);
    }

    @After
    public void tearDown() {
        if (liikkeetKansio.exists()) {
            for (String liikeFilu : liikkeetKansio.list()) {
                new File(Kansiot.LIIKKEET + "/" + liikeFilu).delete();
            }
            liikkeetKansio.delete();
        }
    }

    @Test
    public void LiikkeetKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        if (!liikkeetKansio.exists()) {
            tallennus.luoLiikkeetKansio();
        }
        assertTrue(liikkeetKansio.exists());
    }

    @Test
    public void LiikeTiedostonLuontiOnnistuu() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        tallennus.kirjoitaMuuttujatTiedostoon();
        assertTrue(penkkiTiedosto.exists());
    }

    @Test
    public void LiikeTiedostonSisaltoOikein() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        tallennus.kirjoitaMuuttujatTiedostoon();
        Scanner lukija = new Scanner(penkkiTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = "";
        tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals("paino\ntoistot", tiedostonSisalto);
    }

}
