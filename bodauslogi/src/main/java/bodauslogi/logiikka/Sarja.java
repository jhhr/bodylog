package bodauslogi.logiikka;

import java.util.ArrayList;

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

    /**
     * Muokkaa sarjan arvon String-muotoon. Sarjan toString-metodi käyttää tätä.
     * Palauttaa "null" jos arvo on null. Jos arvo on Double niin metodi poistaa
     * ylimääräiset nollat ja pisteen. Eli x.0-->x mutta x.y-->x.y
     *
     * @param arvo Sarjan yksi arvo
     *
     * @return arvon String-muodossa
     */
    private String arvoToString(Object arvo) {
        if (arvo == null) {
            return "null";
        }
        double arvoD = (Double) arvo;
        if (arvoD == (long) arvoD) {
            return String.format("%d", (long) arvoD);
        } else {
            return String.format("%s", arvoD);
        }

    }

    /**
     * Sarjan toString-metodi.
     *
     * Käsittelee sarjan arvot Object-olioina koska arvo saattaa olla null. 
     * Kasittely Double-olioina johtaa NullPointerExceptioniin.
     *
     * @return Sarjan muodossa {arvo1, ... ,arvoN}
     */

    @Override
    public String toString() {
        String str = "{";
        for (Object arvo : arvot) {
            str += arvoToString(arvo) + ",";
        }
        str = str.substring(0, str.length() - 1) + "}";
        return str;
    }
}
