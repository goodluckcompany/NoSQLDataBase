package workserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ������� on 14.12.2015.
 */
public class ListOfTables {
    /** ����� ������ ��� �������� ��� ������, ������� ���������� �� �������� ����������: <br>
     * {@link ListOfTables#tables}, {@link ListOfTables#size}, {@link ListOfTables#dir} .
     * @author Ivanov Aleksey
     */

    /** ������������ ��� �������� ��� ������ �� ���������� {@link ListOfTables#dir}    */
    private ArrayList<String> tables;
    /** ������ ������ � ���������� {@link ListOfTables#dir}    */
    private long size;
    /** ���������� �� ������� ��������� ����� ������  */
    private  static String dir;

    /**
     * �����������, ������ ���������� � �������� ������� {@link ListOfTables#update()} ��� ��������� ��� ������
     */
    ListOfTables() {
        dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir += File.separator + "Databases";
        tables = new ArrayList<>();
        size = 0 ;
        update();

    }

    /**
     * ������� {@link ListOfTables#tables} � �������� ������� {@link ListOfTables#listFilesForFolder(File)}
     * ��� ��������� ������ ������ �� ���������� {@link ListOfTables#dir}
     */
    public void update(){
        tables.clear();
        final File folder = new File(dir);
        listFilesForFolder(folder);

    }

    /**
     * ���������� ����� ������ � ���������� {@link ListOfTables#dir} � ��������� �� � ������ ���� �� ����������
     * .nosql
     * @param folder ���������� �� ������� �������� ������ ���
     */
    private void listFilesForFolder(final File folder) {
        try {
            size = 0;
            if(folder.listFiles() == null) return;

            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    String s = fileEntry.getName();
                    if (s.contains(".nosql")) {
                        s = s.replace(".nosql", "");
                        size += fileEntry.length();
                        tables.add(s);
                    }

                }
            }
        }catch (NullPointerException e)
        {
            System.out.println(e);
        }
    }

    /**
     * ���������� ������ ������ ���������� {@link ListOfTables#dir}
     * @return ���������� {@link ListOfTables#size}
     */
    public long getSize(){ return size;}

    /**
     * ���������� ������ ��� ������ � {@link ListOfTables#dir}
     * @return ���������� {@link ListOfTables#tables}
     */
    public ArrayList<String> getTables() {
        return tables;
    }


    public static void main(String[] args) {

        ListOfTables test = new ListOfTables();

        ArrayList<String> tables = test.getTables();



        for(String s: tables){
            System.out.println(s);
        }

        long size  = test.getSize();
        System.out.println(size);


    }


}
