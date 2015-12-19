package workserver;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by алексей on 19.12.2015.
 */
public class TestListOfTables {

    @Test
    public void testUpdate() throws Exception {
        NoSqlDB db1 = new NoSqlDB("test1");
        NoSqlDB db2 = new NoSqlDB("test2");
        NoSqlDB db3 = new NoSqlDB("test3");

        ListOfTables test = new ListOfTables();
        ArrayList<String> tables = test.getTables();
        Assert.assertEquals("[test1, test2, test3]", tables.toString());
        NoSqlDB db4 = new NoSqlDB("test4");
        Assert.assertNotEquals("[test1, test2, test3, test4]", tables.toString());
        test.update();
        tables = test.getTables();
        Assert.assertEquals("[test1, test2, test3, test4]",tables.toString());

        db1.deleteFile();
        db2.deleteFile();
        db3.deleteFile();
        db4.deleteFile();

    }



    @Test
    public void testSizes() throws Exception {
        NoSqlDB db1 = new NoSqlDB("test1");
        NoSqlDB db2 = new NoSqlDB("test2");
        NoSqlDB db3 = new NoSqlDB("test3");

        db1.append("test1","test1");
        db2.append("test2","test2");
        db3.append("test3","test3");
        db3.append("test4","test4");
        long size = 0;

        String dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            Assert.fail();
        }
        dir += File.separator + "Databases";
        final File folder = new File(dir);

        try {
            if(folder.listFiles() == null) return;

            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    String s = fileEntry.getName();
                    if (s.contains(".nosql")) {
                        size += fileEntry.length();
                    }

                }
            }
        }catch (NullPointerException e)
        {
            Assert.fail();
        }
        ListOfTables test = new ListOfTables();

        Assert.assertEquals(size, test.getSize());

        db1.deleteFile();
        db2.deleteFile();
        db3.deleteFile();

    }

}