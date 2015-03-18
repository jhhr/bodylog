package bodauslogi.logiikka;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LiikeTest {

    Liike penkki;

    @Before
    public void SetUp() {
        penkki = new Liike("penkki");
    }

    @Test
    public void LiikkeenMuuttujaJoukkoAlussaTyhja() {
        assertTrue(penkki.getMuuttujat().isEmpty());
    }

    @Test
    public void LiikkeenSarjaListaAlussaTyhja() {
        assertTrue(penkki.getSarjat().isEmpty());
    }
    
    @Test
    public void LiikkeeseenLisattyYksiMuuttujaNiinMuuttujaJoukonKokoYksi(){
        penkki.lisaaMuuttuja("paino");
        assertEquals(1, penkki.getMuuttujat().size());
    }   
    
    @Test
    public void LiikkeeseenEiVoiLisataSarjaaJosMuuttujiaEiOle(){
        assertFalse(penkki.lisaaSarja(new ArrayList<Double>()));
    }
    
    @Test
    public void LiikkeeseenEiVoiLisataSarjaaJonkaKokoKaksiKunMuuttujiaYksi(){
        penkki.lisaaMuuttuja("paino");
        ArrayList sarja = new ArrayList<>();
        sarja.add(50.0);
        sarja.add(60.0);
        assertFalse(penkki.lisaaSarja(sarja));
    }
    
    @Test
    public void OikeanKokoisenLisattySarjaOnnistuuKunMuuttujiaYksi(){
        penkki.lisaaMuuttuja("paino");        
        ArrayList sarja = new ArrayList<>();
        sarja.add(50.0);
        assertTrue(penkki.lisaaSarja(sarja));
    }
    
    @Test
    public void OikeanKokoisenLisattySarjaLoytyyOikeastaPaikastaKunMuuttujiaYksi(){
        penkki.lisaaMuuttuja("paino");        
        ArrayList sarja = new ArrayList<>();
        sarja.add(50.0);
        penkki.lisaaSarja(sarja);
        assertEquals(sarja, penkki.getSarjat().get(0));
    }
    
}
