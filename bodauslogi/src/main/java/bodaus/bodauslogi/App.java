package bodaus.bodauslogi;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Sessio sessio = new Sessio(new Date());
        Liike pena = new Liike("pena");
        sessio.lisaaLiike("pena", pena);
        pena.lisaaMuuttuja("paino");
        sessio.lisaaSarjaLiikkeelle("pena");
        System.out.println(sessio);
    }
}
