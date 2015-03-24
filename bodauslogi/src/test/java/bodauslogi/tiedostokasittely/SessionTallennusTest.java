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
import org.junit.BeforeClass;
import org.junit.Test;

public class SessionTallennusTest {

    Sessio sessio;
    Liike penkki;
    Liike mave;
    Sarja sarja1;
    Sarja sarja2;
    Sarja sarja3;
    SessionTallennus tallennus;
    File sessiotKansio;
    File penkkiKansio;
    File maveKansio;
    File maveSessioTiedosto;
    File penkkiSessioTiedosto;

    @Before
    public void setUp() {
        sessio = new Sessio(new Date(0));        
        tallennus = new SessionTallennus(sessio);
        penkki = new Liike("penkki");
        mave = new Liike("mave");  
        sessiotKansio = new File("Sessiot");
        maveKansio = new File("Sessiot/mave");
        penkkiKansio = new File("Sessiot/penkki");
        maveSessioTiedosto = new File("Sessiot/mave/01.01.1970.txt");
        penkkiSessioTiedosto = new File("Sessiot/penkki/01.01.1970.txt");
    }
    
    private void lisaaMaveSarjat(){
        sarja3 = new Sarja();
        sarja3.lisaaArvo("paino", 125.0);
        sarja3.lisaaArvo("toistot", 5);
        mave.lisaaMuuttuja("paino");
        mave.lisaaMuuttuja("toistot");
        mave.lisaaSarja(sarja3);
    }
    
    private void lisaaPenkkiSarjat(){
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
                new File("Sessiot/mave/"+maveFilu).delete();
            }
            maveKansio.delete();
        }
        if (penkkiKansio.exists()) {
            for (String penkkiFilu : penkkiKansio.list()) {
                new File("Sessiot/penkki/"+penkkiFilu).delete();
            }
            penkkiKansio.delete();
        }
        sessiotKansio.delete();
    }

    @Test
    public void SessiotKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        if (!sessiotKansio.exists()) {
            tallennus.LuoSessiotKansio();
        }
        assertTrue(sessiotKansio.exists());
    }

    @Test
    public void YhdenLiikeKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {        
        sessio.lisaaLiike(mave);
        sessiotKansio.mkdir();
        if (!maveKansio.exists()) {
            tallennus.LuoKansiotLiikkeille();
        }
        assertTrue(maveKansio.exists());
    }

    @Test
    public void YhdenLiikeKansionLuontiJosAiemminOlemassaToimii() {
        sessio.lisaaLiike(mave);
        sessiotKansio.mkdir();
        maveKansio.mkdir();
        try {
            tallennus.LuoKansiotLiikkeille();
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    public void KahdenLiikeKansionLuontiJosEiAiemminOlemassaOnnistuu() throws Exception {
        sessio.lisaaLiike(mave);
        sessiotKansio.mkdir();
        sessio.lisaaLiike(penkki);
        if (!maveKansio.exists() || !penkkiKansio.exists()) {
            tallennus.LuoKansiotLiikkeille();
        }
        assertTrue(maveKansio.exists() && penkkiKansio.exists());
    }
    
    @Test
    public void YhdenLiikkeenSessioTiedostojenLuontiJosEiAiemminOlemassaOnnistuu() throws Exception{
        sessio.lisaaLiike(mave);
        sessiotKansio.mkdir();
        maveKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        assertTrue(maveSessioTiedosto.exists());        
    }
    
    @Test
    public void KahdenLiikkeenSessioTiedostojenLuontiJosEiAiemminOlemassaOnnistuu() throws Exception{
        sessio.lisaaLiike(penkki);
        sessio.lisaaLiike(mave);
        sessiotKansio.mkdir();
        maveKansio.mkdir();
        penkkiKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        assertTrue(maveSessioTiedosto.exists() && penkkiSessioTiedosto.exists());        
    }
    
    @Test
    public void YksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception{
        sessio.lisaaLiike(mave);
        lisaaMaveSarjat();
        sessiotKansio.mkdir();
        maveKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        Scanner lukija = new Scanner(maveSessioTiedosto);
        assertEquals("[125.0,5.0]",lukija.nextLine());        
    }
    
    @Test
    public void KaksisarjaisenLiikkeenSessioTiedostonSisaltoOikein() throws Exception{
        sessio.lisaaLiike(penkki);
        lisaaPenkkiSarjat();
        sessiotKansio.mkdir();
        penkkiKansio.mkdir();
        tallennus.kirjoitaSessioTiedostoihin();
        Scanner lukija = new Scanner(penkkiSessioTiedosto);
        String tiedostonSisalto = "";
        tiedostonSisalto += lukija.nextLine() + "\n";        
        tiedostonSisalto += lukija.nextLine();
        assertEquals("[65.0,10.0]\n[55.0,8.0]", tiedostonSisalto);
    }

    @Test
    public void PaivamaaraFormaattiOikein() {
    }
}
