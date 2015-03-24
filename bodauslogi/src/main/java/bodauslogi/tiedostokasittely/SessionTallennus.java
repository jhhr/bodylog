package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SessionTallennus {

    private Sessio sessio;
    private String pvmteksti;

    public SessionTallennus(Sessio sessio) {
        this.pvmteksti = new SimpleDateFormat("dd.MM.yyyy").format(sessio.getPaivamaara());
        this.sessio = sessio;
    }

    public void LuoSessiotKansio() throws Exception {
        new File("Sessiot\\").mkdir();
    }

    public void LuoKansiotLiikkeille() throws Exception {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            new File("Sessiot\\" + liikkeenNimi + "\\").mkdir();
        }
    }

    public void kirjoitaSessioTiedostoihin() throws Exception {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            File liikkeenSessio = new File("Sessiot\\" + liikkeenNimi + "\\" + pvmteksti + ".txt");
            FileWriter kirjoittaja = new FileWriter(liikkeenSessio);
            for (Sarja sarja : sessio.getLiike(liikkeenNimi).getSarjaLista()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }
}
