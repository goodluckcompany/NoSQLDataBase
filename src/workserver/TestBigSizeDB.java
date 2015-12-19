package workserver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anderson on 19.12.2015.
 */
public class TestBigSizeDB {
    @Test
    public void testBigSizeDB(){
        String nameTable = "2";
        String test1 = "key12000";
        String test3 = "100000";
        NoSqlDB db = new NoSqlDB(nameTable);
        ResponseItem is = db.getValue(test1);
        assertEquals(is.ResponseItemList.get(0).getKeyField(), test1);
        String test2 = "key120120";
        is = db.getValue(test2);
        assertEquals(is.ResponseItemList.get(0).getKeyField(), test2);
        is = db.getKeys(test3);
        assertEquals(is.ResponseItemList.get(0).getKeyField(), "key" + test3);
        String test4 = "6";
        db.append("Petr","6");
        is = db.getValue("Petr");
        assertEquals(is.ResponseItemList.get(0).getValueField(), test4);
    }
}
