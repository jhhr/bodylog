package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Liike {

    private String nimi;
    private LinkedHashSet<String> muuttujat;
    private ArrayList<ArrayList<Double>> sarjat;

    public Liike(String nimi) {
        this.nimi = nimi;
        this.muuttujat = new LinkedHashSet<>();
        this.sarjat = new ArrayList<>();
    }

    public String getNimi() {
        return nimi;
    }

    public LinkedHashSet<String> getMuuttujat() {
        return muuttujat;
    }

    public ArrayList<ArrayList<Double>> getSarjat() {
        return sarjat;
    }

    public void lisaaMuuttuja(String nimike) {
        muuttujat.add(nimike);
    }

    public boolean lisaaSarja(ArrayList<Double> sarja) {
        if (muuttujat.isEmpty() || sarja.size() != muuttujat.size()) {
            return false;
        } else {
            sarjat.add(sarja);
            return true;
        }
    }

//    @Override
//    public String toString() {
//        String str = nimi + "\n" + muuttujat.toString() + "\n";
//        for (ArrayList<Double> sarja : sarjat) {
//            str += sarja + "\n";
//        }
//        return str;
//    }
}
