package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SessioTiedostoihinTest {

    Sessio sessio;
    Liike penkki;
    Liike mave;
    Sarja sarja1;
    Sarja sarja2;
    Sarja sarja3;
    SessioTiedostoihin tallennus;
    File dataKansio;
    File penkkiKansio;
    File maveKansio;
    File maveSessioTiedosto;
    File penkkiSessioTiedosto;

    @Before
    public void setUp() {
        sessio = new Sessio(new Date(0));
        tallennus = new SessioTiedostoihin(sessio);
        penkki = new Liike("penkki");
        mave = new Liike("mave");
        dataKansio = new File(Kansiot.DATA);
        maveKansio = new File(Kansiot.DATA + "/mave");
        penkkiKansio = new File(Kansiot.DATA + "/penkki");
        maveSessioTiedosto = new File(Kansiot.DATA + "/mave/01.01.1970.txt");
        penkkiSessioTiedosto = new File(Kansiot.DATA + "/penkki/01.01.1970.txt");
    }

    private void lisaaMaveSarjat() {
        sarja3 = new Sarja();
        sarja3.lisaaArvo("paino", 125.0);
        sarja3.lisaaArvo("toistot", 5);
        mave.lisaaMuuttuja("paino");
        mave.lisaaMuuttuja("toistot");
        mave.lisaaSarja(sarja3);
    }

    private void lisaaPenkkiSarjat() {
        sarja1 = new Sarja();
        sarja1.lisaaArvo("paino", 65.0);
        sarja1.lisaaArvo("toistot", 10);
        sarja2 = new Sarja();
        sarja2.lisaaArvo("paino", 55.0);
        sarja2.lisaaArvo("toistot", 8);
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        penkki.lisaaSarja(sarja1);
        penkki.lisaaSarja(sarja2);
    }

    @After
    public void tearDown() {
        if (maveKansio.exists()) {
            for (String maveFilu : maveKansio.list()) {
                new File(Kansiot.DATA + "/mave/" + maveFilu).delete();
            }
            maveKansio.delete();
        }
        if (penkkiKansio.exists()) {
            for (String penkkiFilu : penkkiKansio.list()) {
                new File(Kansiot.DATA + "/penkki/" + penkkiFilu).delete();
            }
            penkkiKansio.delete();
        }
        dataKansio.delete();
    }

    @Test
    public void TilastotKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        if (!dataKansio.exists()) {
            tallennus.luoTilastotKansio();
        }
        assertTrue(dataKansio.exists());
    }

    @Test
    public void YhdenLiikeKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        sessio.lisaaLiike(mave);
        dataKansio.mkdir();
        if (!maveKansio.exists()) {
            tallennus.luoKansiotLiikkeille();
        }
        assertTrue(maveKansio.exists());
    }

    @Test
    public void YhdenLiikeKansionLuontiJosAiemminOlemassaToimii() {
        sessio.lisaaLiike(mave);
        dataKansio.mkdir();
        maveKansio.mkdir();
        try {
            tallennus.luoKansiotLiikkeille();
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    public void KahdenLiikeKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        sessio.lisaaLiike(mave);
        dataKansio.mkdir();
        sessio.lisaaLiike(penkki);
        if (!maveKansio.exists() || !penkkiKansio.exists()) {
            tallennus.luoKansiotLiikkeille();
        }
        assertTrue(maveKansio.exists() && penkkiKansio.exists());
    }

    @Test
    public void YhdenLiikkeenSessioTiedostojenLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        sessio.lisaaLiike(mave);
        dataKansio.mkdir();
        maveKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        assertTrue(maveSessioTiedosto.exists());
    }

    @Test
    public void KahdenLiikkeenSessioTiedostojenLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        sessio.lisaaLiike(penkki);
        sessio.lisaaLiike(mave);
        dataKansio.mkdir();
        maveKansio.mkdir();
        penkkiKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        assertTrue(maveSessioTiedosto.exists() && penkkiSessioTiedosto.exists());
    }

    @Test
    public void YksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        sessio.lisaaLiike(mave);
        lisaaMaveSarjat();
        dataKansio.mkdir();
        maveKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        Scanner lukija = new Scanner(maveSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja3.toString(), tiedostonSisalto);
    }

    @Test
    public void KaksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        sessio.lisaaLiike(penkki);
        lisaaPenkkiSarjat();
        dataKansio.mkdir();
        penkkiKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        Scanner lukija = new Scanner(penkkiSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja1 + "\n" + sarja2, tiedostonSisalto);
    }
}