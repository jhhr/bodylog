package bodauslogi.tiedostokasittely;
 
import bodauslogi.logiikka.Liike;
import java.io.File;
import java.io.FileWriter;
 
public class LiikeTiedostoon {
 
    private LiikeTiedostoon() {
    }
 
    public static void luoLiikkeetKansio() throws Exception {
        new File(Kansiot.LIIKKEET).mkdir();
    }
 
    public static void kirjoita(Liike liike) throws Exception {
        File liikeTiedosto = new File(Kansiot.LIIKKEET + "/" + liike.getNimi() + ".txt");
        FileWriter kirjoittaja = new FileWriter(liikeTiedosto);
        for (String muuttuja : liike.getMuuttujaJoukko()) {
            kirjoittaja.write(muuttuja + "\n");
        }
        kirjoittaja.close();
    }
 
}