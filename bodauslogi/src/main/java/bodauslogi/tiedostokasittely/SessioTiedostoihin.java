package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class SessioTiedostoihin {

    private SessioTiedostoihin() {
    }

    public static void luoDATAKansio() throws Exception {
        new File(Kansiot.DATA).mkdir();
    }

    public static void luoKansiotLiikkeille(Sessio sessio) throws Exception {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            new File(Kansiot.DATA + "/" + liikkeenNimi + "/").mkdir();
        }
    }

    public static void kirjoita(Sessio sessio) throws Exception {

        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            String pvmteksti = new SimpleDateFormat("dd.MM.yyyy").format(sessio.getPaivamaara());
            File sessioTiedosto = new File(Kansiot.DATA + "/" + liikkeenNimi + "/" + pvmteksti + ".txt");
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Sarja sarja : sessio.getLiike(liikkeenNimi).getSarjaLista()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }
}
