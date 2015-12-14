package workserver;

import mainserver.MainServer;
import mainserver.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Anderson on 26.11.2015.
 */
public class ThreadWs implements Runnable {
    String ipAdress;
    Request req;
    Thread t;
    public ObjectOutputStream oos;

    ThreadWs(Request r, ObjectOutputStream _oos,List<NoSqlDB> listDb, ResponseItem items){
        System.out.println("����� � ����������� ������");
        req = r;
        ipAdress = r.getTo();
        oos = _oos;
        String nosqlR = req.getNosqlR();
        NoSqlParser nsp = new NoSqlParser();
        ResponseItem its;
        //�������
        System.out.println(nosqlR);
        System.out.println(req.getNameTable());
        //�������
        int numtable = tableNum(req.getNameTable(),listDb);
        //�������
        System.out.println(numtable);
        //�������

        if (numtable == -1){
            its = nsp.execute(nosqlR,listDb,items);
        }else {
            its = nsp.execute(nosqlR, listDb.get(numtable), items);
            items = its;
        }
        req.setReqItems(items);
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("����� ��������� �������");
            oos.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getIpAdress() {
        return ipAdress;
    }
    public int tableNum(String nametable,List<NoSqlDB> listDb){
        int i = 0;
        NoSqlDB ndb = null;
        ListIterator<NoSqlDB> itr = listDb.listIterator();
        while(itr.hasNext()){
            ndb = itr.next();
            if(ndb.getDbName().equals(nametable)){
                System.out.println(nametable);
                System.out.println(i);
                return i;
            }
            i++;
        }
        return -1;
    }


}
