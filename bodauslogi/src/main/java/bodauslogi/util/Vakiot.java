package bodauslogi.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Vakiot {

    public static final String SESSIOT = "Tilastot";
    public static final String LIIKKEET = "Liikkeet";
    
    public static final String SESSIOPAATE = ".ses";        
    public static final String LIIKEPAATE = ".lii";
    
    public static final DateTimeFormatter TIEDOSTOPVM = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter UIPVM = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
}
