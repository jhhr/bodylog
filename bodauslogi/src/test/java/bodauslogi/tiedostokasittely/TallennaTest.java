package bodauslogi.tiedostokasittely;

import bodauslogi.util.Vakiot;
import bodauslogi.logiikka.*;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TallennaTest {

    Sessio maveSessio;
    Sessio penkkiSessio;
    Liike penkki;
    Liike mave;
    Sarja sarja1;
    Sarja sarja2;
    Sarja sarja3;
    File dataKansio;
    File penkkiKansio;
    File maveKansio;
    File maveSessioTiedosto;
    File penkkiSessioTiedosto;
    File liikkeetKansio;
    File penkkiLiikeTiedosto;

    @Before
    public void setUp() {
        maveSessio = new Sessio(new Date(0));
        penkkiSessio = new Sessio(new Date(0));
        penkki = new Liike("penkki");
        mave = new Liike("mave");
        dataKansio = new File(Vakiot.SESSIOT);
        maveKansio = new File(Vakiot.SESSIOT + "/mave");
        penkkiKansio = new File(Vakiot.SESSIOT + "/penkki");
        maveSessioTiedosto = new File(Vakiot.SESSIOT + "/mave/1970.01.01" + Vakiot.SESSIOPAATE);
        penkkiSessioTiedosto = new File(Vakiot.SESSIOT + "/penkki/1970.01.01" + Vakiot.SESSIOPAATE);
        liikkeetKansio = new File(Vakiot.LIIKKEET);
        penkkiLiikeTiedosto = new File(Vakiot.LIIKKEET + "/penkki" + Vakiot.LIIKEPAATE);
    }

    private void lisaaMaveSarjat() {
        sarja3 = new Sarja();
        sarja3.lisaaArvo(125.0);
        sarja3.lisaaArvo(5);
        mave.lisaaMuuttuja("paino");
        mave.lisaaMuuttuja("toistot");
        maveSessio.lisaaSarja(sarja3);
        mave.lisaaSessio(maveSessio);
    }

    private void lisaaPenkkiSarjat() {
        sarja1 = new Sarja();
        sarja1.lisaaArvo(65.0);
        sarja1.lisaaArvo(10);
        sarja2 = new Sarja();
        sarja2.lisaaArvo(55.0);
        sarja2.lisaaArvo(8);
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        penkkiSessio.lisaaSarja(sarja1);
        penkkiSessio.lisaaSarja(sarja2);
        penkki.lisaaSessio(penkkiSessio);
    }

    @After
    public void tearDown() {
        if (maveKansio.exists()) {
            for (String maveFilu : maveKansio.list()) {
                new File(Vakiot.SESSIOT + "/mave/" + maveFilu).delete();
            }
            maveKansio.delete();
        }
        if (penkkiKansio.exists()) {
            for (String penkkiFilu : penkkiKansio.list()) {
                new File(Vakiot.SESSIOT + "/penkki/" + penkkiFilu).delete();
            }
            penkkiKansio.delete();
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
    public void KansioidenLuontiKahdelleLiikkeelle_KunEiOlemassa() throws Exception {
        Tallenna.luoDataKansiot(mave);
        Tallenna.luoDataKansiot(penkki);
        assertTrue(maveKansio.exists() && penkkiKansio.exists());
    }

    @Test
    public void Sessiot_KahdenLiikkeenSessioTiedostojenLuontiOnnistuu() throws Exception {
        lisaaMaveSarjat();
        lisaaPenkkiSarjat();
        dataKansio.mkdir();
        maveKansio.mkdir();
        penkkiKansio.mkdir();
        Tallenna.sessiot(mave);
        Tallenna.sessiot(penkki);
        assertTrue(maveSessioTiedosto.exists() && penkkiSessioTiedosto.exists());
    }

    @Test
    public void Sessio_YksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        lisaaMaveSarjat();
        dataKansio.mkdir();
        maveKansio.mkdir();
        Tallenna.sessiot(mave);
        Scanner lukija = new Scanner(maveSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja3.toString(), tiedostonSisalto);
    }

    @Test
    public void Sessio_KaksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        lisaaPenkkiSarjat();
        dataKansio.mkdir();
        penkkiKansio.mkdir();
        Tallenna.sessiot(penkki);
        Scanner lukija = new Scanner(penkkiSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja1 + "\n" + sarja2, tiedostonSisalto);
    }

    @Test
    public void Liike_KansionLuontiOnnistuu_KunEiAiemminOlemassa() throws Exception {
        if (!liikkeetKansio.exists()) {
            Tallenna.luoLiikkeetKansio();
        }
        assertTrue(liikkeetKansio.exists());
    }

    @Test
    public void Liike_LiikeTiedostonLuontiOnnistuu() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        Tallenna.liike(penkki);
        assertTrue(penkkiLiikeTiedosto.exists());
    }

    @Test
    public void Liike_LiikeTiedostonSisaltoOikein() throws Exception {
        liikkeetKansio.mkdir();
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        Tallenna.liike(penkki);
        Scanner lukija = new Scanner(penkkiLiikeTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = "";
        tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals("paino\ntoistot", tiedostonSisalto);
    }

}
