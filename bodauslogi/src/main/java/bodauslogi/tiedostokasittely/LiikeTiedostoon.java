package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.io.FileWriter;

public class LiikeTiedostoon {

    private final Liike liike;
    private final File liikeTiedosto;

    public LiikeTiedostoon(Liike liike) {
        this.liike = liike;
        this.liikeTiedosto = new File(Kansiot.LIIKKEET + "/" + liike.getNimi() + ".txt");
    }

    public void luoLiikkeetKansio() throws Exception {
        new File(Kansiot.LIIKKEET).mkdir();
    }

    public void kirjoitaMuuttujatTiedostoon() throws Exception {
        FileWriter kirjoittaja = new FileWriter(this.liikeTiedosto);
        for (String muuttuja : liike.getMuuttujaJoukko()) {
            kirjoittaja.write(muuttuja + "\n");
        }
        kirjoittaja.close();
    }

}
