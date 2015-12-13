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
        listR = items;
        String nosqlR = req.getNosqlR();
        NoSqlParser nsp = new NoSqlParser();
        nsp.execute(nosqlR, listDb, items);


        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Вывел пришедший реквест");
            System.out.println(req);
            oos.writeObject(listR);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getIpAdress() {
        return ipAdress;
    }

}
