package workserver;

import mainserver.MainServer;
import mainserver.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Anderson on 26.11.2015.
 */
public class ThreadWs implements Runnable {
    String ipAdress;
    Request req;
    Thread t;
    public ObjectOutputStream oos;

    public ResponseItem listR = new ResponseItem();

    ThreadWs(Request r, ObjectOutputStream _oos,List<NoSqlDB> listDb, ResponseItem items){
        System.out.println("Зашел в конструктор потока");
        req = r;
        ipAdress = r.getTo();
        oos = _oos;

        String nosqlR = req.getNosqlR();
        NoSqlDB db = new NoSqlDB("table2");
        NoSqlParser nsp = new NoSqlParser();
        nsp.execute("create tabl1", listDb, items);
        listDb.add(db);
        listDb.get(0).append("Nikita", "Main");
        listDb.get(0).append("Oleg", "Wserver");
        listDb.get(0).append("Alexey", "NoSqlDb");
        listR = listDb.get(0).getAll();
        String res = listR.ResponseItemList.get(0).toString();
        String res2 = res + listR.ResponseItemList.get(1).toString();
        System.out.println(res);
        System.out.println(res2);

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Вывел пришедший реквест");
            System.out.println(req);
            oos.writeObject(new Request(ipAdress,req.getData() + "FINISH"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getIpAdress() {
        return ipAdress;
    }

}
