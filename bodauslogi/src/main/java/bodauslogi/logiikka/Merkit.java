package bodauslogi.logiikka;

public class Merkit {

    public static final char[] kielletyt = new char[]{'{', '}', ','};

    public static void tarkistaOnkoSallittu(String tarkistettava) {
        for (char ch : Merkit.kielletyt) {
            if (tarkistettava.contains("" + ch)) {
                throw new IllegalArgumentException("merkit {}:, eivät ole sallittu");
            }
        }
    }

}
