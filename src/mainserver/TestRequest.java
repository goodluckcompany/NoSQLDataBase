package mainserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anderson on 18.12.2015.
 */
public class TestRequest {
    @Test
    public void testGetSize(){
        Request r = new Request("10.32.2.12","creat 2");
        assertEquals(r.getSize(), -1);
        long i = 23;
        r.setSize(i);
        assertEquals(i,r.getSize());
    }
    @Test
    public void testSetSize(){
        Request r = new Request("10.32.2.12","creat 2");
        long i = 257;
        r.setSize(i);
        assertEquals(r.getSize(), i);
        r.setSize(i + 1);
        assertEquals(r.getSize(),i+1);
    }
    @Test
    public void testGetNameTable(){
        Request r = new Request("10.32.2.12","creat 2");
        String ntable = "Luck";
        r.setNameTable(ntable);
        assertEquals(r.getNameTable(), ntable);
    }
    @Test
    public void testSetIsOriginal(){
        Request r = new Request("10.32.2.12","creat 2");
        r.setIsOriginal(true);
        assertEquals(r.getIsOriginal(), true);
        r.setIsOriginal(false);
        assertEquals(r.getIsOriginal(), false);
    }
    @Test
    public void testSetNosqlR(){
        String nosql = "create 2";
        Request r = new Request("10.32.2.12",nosql);
        assertEquals(r.getNosqlR(), nosql);
        r.setNosqlR("create 3");
        assertEquals(r.getNosqlR(), "create 3");
    }
}
