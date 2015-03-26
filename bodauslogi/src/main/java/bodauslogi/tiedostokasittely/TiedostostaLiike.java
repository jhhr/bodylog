package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.util.Scanner;

public class TiedostostaLiike {

    private final File liikeTiedosto;
    private final Scanner lukija;

    public TiedostostaLiike(File liikeTiedosto) throws Exception {
        this.liikeTiedosto = liikeTiedosto;
        lukija = new Scanner(liikeTiedosto);
    }

    public Liike luoLiike() {
        String nimi = liikeTiedosto.getName();
        nimi = nimi.substring(0,nimi.length() - 4);
        Liike liike = new Liike(nimi);
        while (lukija.hasNextLine()) {
            liike.lisaaMuuttuja(lukija.nextLine());
        }
        lukija.close();
        return liike;
    }

}