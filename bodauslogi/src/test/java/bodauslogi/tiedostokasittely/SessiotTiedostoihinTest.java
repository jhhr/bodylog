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

public class SessiotTiedostoihinTest {

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

    @Before
    public void setUp() {
        maveSessio = new Sessio(new Date(0));
        penkkiSessio = new Sessio(new Date(0));
        penkki = new Liike("penkki");
        mave = new Liike("mave");
        dataKansio = new File(Vakiot.DATA);
        maveKansio = new File(Vakiot.DATA + "/mave");
        penkkiKansio = new File(Vakiot.DATA + "/penkki");
        maveSessioTiedosto = new File(Vakiot.DATA + "/mave/1970.01.01.txt");
        penkkiSessioTiedosto = new File(Vakiot.DATA + "/penkki/1970.01.01.txt");
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
                new File(Vakiot.DATA + "/mave/" + maveFilu).delete();
            }
            maveKansio.delete();
        }
        if (penkkiKansio.exists()) {
            for (String penkkiFilu : penkkiKansio.list()) {
                new File(Vakiot.DATA + "/penkki/" + penkkiFilu).delete();
            }
            penkkiKansio.delete();
        }
        dataKansio.delete();
    }

    @Test
    public void KansioidenLuontiKahdelleLiikkeelle_KunEiOlemassa() throws Exception {
        SessiotTiedostoihin.luoKansiot(mave);
        SessiotTiedostoihin.luoKansiot(penkki);
        assertTrue(maveKansio.exists() && penkkiKansio.exists());
    }

    @Test
    public void KahdenLiikkeenSessioTiedostojenLuontiOnnistuu() throws Exception {
        lisaaMaveSarjat();
        lisaaPenkkiSarjat();
        dataKansio.mkdir();
        maveKansio.mkdir();
        penkkiKansio.mkdir();
        SessiotTiedostoihin.kirjoita(mave);
        SessiotTiedostoihin.kirjoita(penkki);
        assertTrue(maveSessioTiedosto.exists() && penkkiSessioTiedosto.exists());
    }

    @Test
    public void YksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        lisaaMaveSarjat();
        dataKansio.mkdir();
        maveKansio.mkdir();
        SessiotTiedostoihin.kirjoita(mave);
        Scanner lukija = new Scanner(maveSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja3.toString(), tiedostonSisalto);
    }

    @Test
    public void KaksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception {
        lisaaPenkkiSarjat();
        dataKansio.mkdir();
        penkkiKansio.mkdir();
        SessiotTiedostoihin.kirjoita(penkki);
        Scanner lukija = new Scanner(penkkiSessioTiedosto);
        lukija.useDelimiter("\\Z");
        String tiedostonSisalto = lukija.next();
        lukija.close();
        assertEquals(sarja1 + "\n" + sarja2, tiedostonSisalto);
    }
}
