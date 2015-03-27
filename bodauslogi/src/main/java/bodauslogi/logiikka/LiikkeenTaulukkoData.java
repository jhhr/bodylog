package bodauslogi.logiikka;

import java.util.Date;

public class LiikkeenTaulukkoData {

    private final Liike liike;
    private final Date paivamaara;

    public LiikkeenTaulukkoData(Liike liike, Date pvm) {
        this.liike = liike;
        this.paivamaara = pvm;
    }

    public Liike getLiike() {
        return liike;
    }

    public Date getPaivamaara() {
        return paivamaara;
    }

}
