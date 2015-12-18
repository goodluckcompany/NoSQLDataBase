package workserver;

/**
 * Created by Anderson on 18.12.2015.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestItem {
    @Test
    public void testGetKeyField(){
        Item i1 = new Item("1","a");
        Item i2 = new Item("2","b");
        assertEquals("1",i1.getKeyField());
        assertEquals("2",i2.getKeyField());
    }
    @Test
    public void testGetValueField() {
        Item i1 = new Item("1","a");
        Item i2 = new Item("2","b");
        assertEquals("a",i1.getValueField());
        assertEquals("b",i2.getValueField());
    }
    @Test
    public void testSetKeyField() {
        String a = "Oleg";
        String b = "Anna";
        Item i1 = new Item("Slava","1");
        Item i2 = new Item("Fedor","2");
        i1.setKeyField(a);
        i2.setKeyField(b);
        assertEquals(i1.getKeyField(), a);
        assertEquals(i2.getKeyField(),b);
    }
    @Test
    public void testSetValueField() {
        String a = "1";
        String b = "2";
        Item i1 = new Item("Slava","a");
        Item i2 = new Item("Fedor","b");
        i1.setValueField(a);
        i2.setValueField(b);
        assertEquals(i1.getValueField(),a);
        assertEquals(i2.getValueField(),b);
    }
    @Test
    public void testToString() {
        Item i1 = new Item("Slava","a");
        Item i2 = new Item("Fedor","b");
        String test1 = "Slava : a\n";
        String test2 = "Fedor : b\n";
        assertEquals(i1.toString(),test1);
        assertEquals(i2.toString(),test2);
    }
}
