package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class SessioTiedostoihin {

    private final Sessio sessio;
    private final String pvmteksti;

    public SessioTiedostoihin(Sessio sessio) {
        this.pvmteksti = new SimpleDateFormat("dd.MM.yyyy").format(sessio.getPaivamaara());
        this.sessio = sessio;
    }

    public void luoTilastotKansio() throws Exception {
        new File(Kansiot.DATA).mkdir();
    }

    public void luoKansiotLiikkeille() throws Exception {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            new File(Kansiot.DATA + "/" + liikkeenNimi + "/").mkdir();
        }
    }

    public void kirjoitaSessioTiedostoihin() throws Exception {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            File sessioTiedosto = new File(Kansiot.DATA+"/" + liikkeenNimi + "/" + pvmteksti + ".txt");
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Sarja sarja : sessio.getLiike(liikkeenNimi).getSarjaLista()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }
}
