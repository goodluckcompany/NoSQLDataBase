package mainserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Anna on 14.12.2015.
 */
public class AvailableTablesTest {

    @org.junit.Test
    public void testAddTable() throws Exception {
        AvailableTables table = new AvailableTables();
        assertEquals("Table wasn't added", 0, table.addTable("tableName", "0.0.0.0", "1.1.1.1"));
        assertEquals("Error", -1, table.addTable("tableName", "1.1.1.1", "2.2.2.2"));
        assertEquals("Table wasn't added", 0, table.addTable("tableName1", "3.3.3.3", "4.4.4.4"));

    }

    @org.junit.Test
    public void testRemoveTable() throws Exception {
        AvailableTables table = new AvailableTables();
        table.addTable("tableName3", "0.0.0.0", "1.1.1.1");
        assertEquals("table wasn't removed", 0, table.removeTable("tableName3"));
        assertEquals("Error", 1, table.removeTable("Nonexistent table"));
    }

    @org.junit.Test
    public void testSaveTable() throws Exception {
        AvailableTables table = new AvailableTables();
        table.addTable("tableName4", "0.0.0.0", "1.1.1.1");
        assertEquals("Table wasn't saved", 0, table.saveTable());
        table.removeTable("tableName4");
        assertEquals("Table wasn't saved", 0, table.saveTable());
    }

    @org.junit.Test
    public void testGetMainServerIP() throws Exception {
        AvailableTables table = new AvailableTables();
        table.addTable("tableName5", "0.0.0.0", "1.1.1.1");
        assertEquals("Error", "0.0.0.0", table.getMainServerIP("tableName5"));
        assertNotEquals("Error","",table.getMainServerIP("tableName5"));
    }

    @org.junit.Test
    public void testGetReserveServerIP() throws Exception {
        AvailableTables table = new AvailableTables();
        table.addTable("tableName6", "0.0.0.0", "1.1.1.1");
        assertEquals("Error", "1.1.1.1", table.getReserveServerIP("tableName6"));
        assertNotEquals("Error","",table.getReserveServerIP("tableName6"));
    }
}