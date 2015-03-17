package bodaus.bodauslogi;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Sarja {
    
    private LinkedHashMap<String,Double> arvot;
    
    public Sarja(Liike liike){
        this.arvot = new LinkedHashMap<>();
        for (String muuttuja : liike.getMuuttujat()) {
            this.arvot.put(muuttuja, annaArvo());
        }
    }
    
    private double annaArvo(){
        return Double.parseDouble(new Scanner(System.in).nextLine());
    }
    
    @Override
    public String toString(){
        String str = "";
        for (Double arvo : arvot.values()) {
            str += arvo + ", ";
        }
        return str;
    }
}
