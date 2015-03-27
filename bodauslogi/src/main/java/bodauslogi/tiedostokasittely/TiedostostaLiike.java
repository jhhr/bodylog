package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.util.Scanner;

public class TiedostostaLiike {

    private TiedostostaLiike() {
    }

    public static Liike luo(File liikeTiedosto) throws Exception {
        Scanner lukija = new Scanner(liikeTiedosto);
        String nimi = liikeTiedosto.getName();
        nimi = nimi.substring(0, nimi.length() - 4);
        Liike liike = new Liike(nimi);
        while (lukija.hasNextLine()) {
            liike.lisaaMuuttuja(lukija.nextLine());
        }
        lukija.close();
        return liike;
    }

}
