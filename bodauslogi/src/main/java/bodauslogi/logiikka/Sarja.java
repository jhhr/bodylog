package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class Sarja {

    private ArrayList<Double> arvot;

    public Sarja() {
        this.arvot = new ArrayList<>();
    }

    public Double[] toArray(int koko) {
        return arvot.toArray(new Double[koko]);
    }

    public Double[] toArray() {
        return arvot.toArray(new Double[0]);
    }

    public int koko() {
        return arvot.size();
    }

    public void lisaaArvo(double arvo) {
        arvot.add(arvo);
    }

    public void lisaaArvo() {
        arvot.add(null);
    }

    public double getArvo(int indeksi) {
        return arvot.get(indeksi);
    }

    @Override
    public String toString() {
        String str = "{";
        for (Object arvo : arvot) {
            str += arvo + ",";
        }
        str = str.substring(0, str.length() - 1) + "}";
        return str;
    }
}
