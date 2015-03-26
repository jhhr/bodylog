package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Liike {

    private String nimi;
    private LinkedHashSet<String> muuttujat;
    private ArrayList<Sarja> sarjat;

    public Liike(String nimi) {
        tarkistaOnkoMerkitSallittu(nimi);
        this.nimi = nimi;
        this.muuttujat = new LinkedHashSet<>();
        this.sarjat = new ArrayList<>();
    }

    private void tarkistaOnkoMerkitSallittu(String tarkistettava) {
        for (char ch : Merkit.kielletyt) {
            if (tarkistettava.contains("" + ch)) {
                throw new IllegalArgumentException("merkit {}:, eivät ole sallittu");
            }
        }
    }

    public void setNimi(String uusiNimi) {
        tarkistaOnkoMerkitSallittu(uusiNimi);
        nimi = uusiNimi;
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
        tarkistaOnkoMerkitSallittu(nimike);
        muuttujat.add(nimike);
    }

    public void lisaaSarja(Sarja sarja) {
        sarjat.add(sarja);
    }
}
