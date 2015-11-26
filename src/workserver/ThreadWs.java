package workserver;

import mainserver.MainServer;
import mainserver.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Anderson on 26.11.2015.
 */
public class ThreadWs implements Runnable {
    String ipAdress;
    Request req;
    Thread t;
    public ObjectOutputStream oos;


    ThreadWs(Request r, ObjectOutputStream _oos){
        System.out.println("Зашел в конструктор потока");
        req = r;
        ipAdress = r.getTo();
        oos = _oos;
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
