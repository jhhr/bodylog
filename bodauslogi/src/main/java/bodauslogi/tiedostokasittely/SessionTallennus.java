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

    public void LuoSessiotKansio() {
        try {
            new File("Sessiot\\").mkdir();
        } catch (Exception e) {
            System.out.println("Sessiokansion luonti epäonnistui:" + e.getMessage());
        }
    }

    public void LuoKansiotLiikkeille() {
        try {
            for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
                new File("Sessiot\\" + liikkeenNimi + "\\").mkdir();
            }
        } catch (Exception e) {
            System.out.println("Liikekansion luonti epäonnistui:" + e.getMessage());
        }
    }

    public void kirjoitaSessioTiedostoihin() {
        for (String liikkeenNimi : sessio.getLiikkeidenNimienJoukko()) {
            File liikkeenSessio = new File("Sessiot\\" + liikkeenNimi + "\\" + pvmteksti + ".txt");
            try {
                FileWriter kirjoittaja = new FileWriter(liikkeenSessio);
                for (Sarja sarja : sessio.getLiike(liikkeenNimi).getSarjaLista()) {
                    kirjoittaja.write(sarja.toString() + "\n");
                }
                kirjoittaja.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
