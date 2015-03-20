package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Liike {

    private String nimi;
    private LinkedHashSet<String> muuttujat;
    private ArrayList<Sarja> sarjat;

    public Liike(String nimi) {
        this.nimi = nimi;
        this.muuttujat = new LinkedHashSet<>();
        this.sarjat = new ArrayList<>();
    }

    public String getNimi() {
        return nimi;
    }

    public LinkedHashSet<String> getMuuttujaJoukko() {
        return muuttujat;
    }

    public ArrayList<Sarja> getSarjaLista() {
        return sarjat;
    }

    public void lisaaMuuttuja(String nimike) {
        muuttujat.add(nimike);
    }

    public void lisaaSarja(Sarja sarja) {
        sarjat.add(sarja);
    }
}
