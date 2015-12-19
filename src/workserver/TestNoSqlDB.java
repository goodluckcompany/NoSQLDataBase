package workserver;

/**
 * Created by алексей on 19.12.2015.
 */
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestNoSqlDB {

    @Test(expected=NullPointerException.class)
    public void testWrongName() throws Exception{
        String wrongName = "";
        NoSqlDB wrongTable = new NoSqlDB(wrongName);
    }


    @Test
    public void testGetDbName() throws Exception {
        String name = "testName";

        NoSqlDB testTable = new NoSqlDB(name);

        Assert.assertEquals(testTable.getDbName(),name);

        testTable.deleteFile();
    }

    @Test
    public void testGetFileSize() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");


        String baseDir = "";
        try {
            baseDir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseDir += File.separator + "Databases" + File.separator;
        File f = new File(baseDir + "testName.nosql");
        long fileSize = f.length();

        Assert.assertEquals(fileSize,testTable.getFileSize());

        testTable.append("test","value");

        fileSize = f.length();

        Assert.assertEquals(fileSize,testTable.getFileSize());


        testTable.deleteFile();


    }

    @Test
    public void testAppend() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");

        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value3");
        testTable.append("key4","value4");

        ResponseItem item =  testTable.getValue("key1");

        Assert.assertEquals(1,item.ResponseItemList.size());

        Assert.assertEquals("key1 : value1\n",item.ResponseItemList.get(0).toString());

        ResponseItem item2 =  testTable.getValue("key8");

        Assert.assertEquals(0,item2.ResponseItemList.size());





        testTable.deleteFile();


    }

    @Test
    public void testGetValue() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");

        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value3");
        testTable.append("key4","value4");

        ResponseItem item =  testTable.getValue("key1");

        Assert.assertEquals(1,item.ResponseItemList.size());

        Assert.assertEquals("key1 : value1\n",item.ResponseItemList.get(0).toString());


        testTable.deleteFile();
    }

    @Test
    public void testDelKey() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");

        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value3");
        testTable.append("key4","value4");

        testTable.delKey("key3");

        ResponseItem item = testTable.getAll();
        Assert.assertEquals(3,item.ResponseItemList.size());

        Assert.assertEquals("key4 : value4\n",item.ResponseItemList.get(0).toString());
        Assert.assertEquals("key2 : value2\n",item.ResponseItemList.get(1).toString());
        Assert.assertEquals("key1 : value1\n",item.ResponseItemList.get(2).toString());

        testTable.deleteFile();


    }

    @Test
    public void testDelValue() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");

        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value1");
        testTable.append("key4","value1");
        testTable.append("key5","value5");

        testTable.delValue("value1");

        ResponseItem item = testTable.getAll();

        Assert.assertEquals(2,item.ResponseItemList.size());

        Assert.assertEquals("key5 : value5\n", item.ResponseItemList.get(0).toString());
        Assert.assertEquals("key2 : value2\n",item.ResponseItemList.get(1).toString());

        testTable.deleteFile();
    }

    @Test
    public void testGetAll() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");


        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value1");
        testTable.append("key4","value1");
        testTable.append("key5","value5");

        ResponseItem item = testTable.getAll();

        Assert.assertEquals(5,item.ResponseItemList.size());

        Assert.assertEquals("key5 : value5\n",item.ResponseItemList.get(0).toString());
        Assert.assertEquals("key4 : value1\n",item.ResponseItemList.get(1).toString());
        Assert.assertEquals("key3 : value1\n",item.ResponseItemList.get(2).toString());
        Assert.assertEquals("key2 : value2\n",item.ResponseItemList.get(3).toString());
        Assert.assertEquals("key1 : value1\n",item.ResponseItemList.get(4).toString());


        testTable.deleteFile();
    }


    @Test
    public void testGetKeys() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");
        NoSqlDB testTable2 = new NoSqlDB("testName2");


        testTable.append("key1","value1");
        testTable.append("key2","value2");
        testTable.append("key3","value1");
        testTable.append("key4","value1");
        testTable.append("key5","value5");



        ResponseItem item = testTable.getKeys("value1");
        Assert.assertEquals(3,item.ResponseItemList.size());


        ResponseItem item2 = testTable2.getKeys("value1");
        Assert.assertEquals(0,item2.ResponseItemList.size());

        Assert.assertEquals("key4 : value1\n",item.ResponseItemList.get(0).toString());
        Assert.assertEquals("key3 : value1\n",item.ResponseItemList.get(1).toString());
        Assert.assertEquals("key1 : value1\n",item.ResponseItemList.get(2).toString());



        testTable.deleteFile();
        testTable2.deleteFile();


    }

    @Test
    public void testDeleteFile() throws Exception {
        NoSqlDB testTable = new NoSqlDB("testName");
        testTable.deleteFile();
        String dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir += File.separator + "Databases" + File.separator  + "testName.nosql";

        Assert.assertFalse(new File(dir).exists());
    }


}