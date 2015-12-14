package workserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ������� on 14.12.2015.
 */
public class ListOfTables {
    private ArrayList<String> tables;
    private  static String dir;


    ListOfTables() {
        dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir += File.separator + "Databases";
        tables = new ArrayList<>();
        update();

    }

    public void update(){
        tables.clear();
        final File folder = new File(dir);
        listFilesForFolder(folder);

    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if ( !fileEntry.isDirectory()) {
                String s = fileEntry.getName();
                if(s.contains(".nosql")) {
                    s = s.replace(".nosql","");
                    tables.add(s);
                }

            }
        }
    }


    public ArrayList<String> getTables() {
        return tables;
    }


    public static void main(String[] args) {

        ListOfTables test = new ListOfTables();

        ArrayList<String> tables = test.getTables();

        for(String s: tables){
            System.out.println(s);
        }


    }


}