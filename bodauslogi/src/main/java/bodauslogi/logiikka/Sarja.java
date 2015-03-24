package bodauslogi.logiikka;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class Sarja {

    private LinkedHashMap<String, Double> arvot;

    public Sarja() {
        this.arvot = new LinkedHashMap<>();
    }

    public Set<String> getAvainJoukko() {
        return arvot.keySet();
    }

    public Collection<Double> getArvoKokoelma() {
        return arvot.values();
    }

    public void lisaaArvo(String muuttuja, double arvo) {
        arvot.put(muuttuja, arvo);
    }
    
    public void lisaaArvo(String muuttuja) {
        arvot.put(muuttuja, null);
    }

    public double getArvo(String muuttuja) {
        return arvot.get(muuttuja);
    }

    @Override
    public String toString() {
        String str = "[";
        for (Double arvo : arvot.values()) {
            str += arvo + ",";
        }
        str = str.substring(0, str.length() - 1) + "]";
        return str;
    }
}
