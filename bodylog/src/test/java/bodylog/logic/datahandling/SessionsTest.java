package bodylog.logic.datahandling;

import bodylog.logic.Session;
import bodylog.logic.Set;
import java.time.LocalDate;
import org.junit.Before;

public class SessionsTest {

    private final Session ses1 = new Session();
    private final LocalDate date = LocalDate.of(2, 2, 1999);
    private final Set set1 = new Set();
    private final Set set2 = new Set();
    private final Object[] values1 = new Object[]{};

    @Before
    public void setUp() {
    }

}
