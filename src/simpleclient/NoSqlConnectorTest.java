package simpleclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Anna on 15.12.2015.
 */
public class NoSqlConnectorTest {

    @org.junit.Test
    public void testEstablishConnection() throws Exception {
        NoSqlConnector nsc = new NoSqlConnector("172.18.27.29");
        assertEquals("Connection wasn't established",0, nsc.establishConnection());
    }

    @org.junit.Test
    public void testSendCommand() throws Exception {
        NoSqlConnector nsc = new NoSqlConnector("172.18.27.29");
        if(nsc.establishConnection() == 0)
            assertNotEquals("Error", nsc.sendCommand(""), nsc.sendCommand("create 1"));

    }

    @org.junit.Test
    public void testBreakConnection() throws Exception {
        NoSqlConnector nsc = new NoSqlConnector("172.18.27.29");
        if(nsc.establishConnection() == 0)
            assertEquals("Error",0, nsc.breakConnection());
    }
}