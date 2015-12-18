package workserver;
/**
 * Created by Anderson on 18.12.2015.
 */
import org.junit.Test;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static workserver.ThreadWs.tableNum;

public class TestTableNumInThreadWs {

    @Test
    public void testTableNum() throws IOException {
        List<NoSqlDB> listDb = new LinkedList<>();
        NoSqlDB ndb = new NoSqlDB("2");
        NoSqlDB ndb2 = new NoSqlDB("3");
        listDb.add(ndb);
        listDb.add(ndb2);
        int resault1 = tableNum("2", listDb);
        int test1 = 0;
        int resault2 = tableNum("3", listDb);
        int test2 = 1;
        assertEquals(test1,resault1);
        assertEquals(test2,resault2);
    }
}
