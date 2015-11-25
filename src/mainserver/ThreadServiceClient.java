package mainserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by homie on 24.11.2015.
 */
public class ThreadServiceClient implements Runnable {
    Socket socket;
    MainServer ms;
    Thread t;
    ObjectInputStream ois;

    ThreadServiceClient(Socket _socket, MainServer _ms){
        ms = _ms;
        socket = _socket;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            Request r = (Request)ois.readObject();
            System.err.println(r);
            if(ms.isItemListOfWorkserver(r.getTo())){
                ms.getItemListOfWorkserver(r.getTo()).oos.writeObject(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
