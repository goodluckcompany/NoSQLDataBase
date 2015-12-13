package mainserver;

import com.sun.org.apache.xerces.internal.xs.StringList;

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

            String stringRequest = r.getData();
            String[] stringListRequest = stringRequest.split(" ");
            System.out.println(stringListRequest[0]);
            System.out.println(stringListRequest[1]);

            String ipAdressOfClient = socket.getInetAddress().toString();
            ipAdressOfClient = ipAdressOfClient.replace("/","");
            if(ms.isItemListOfWorkserver(r.getTo())){
                ms.getItemListOfWorkserver(r.getTo()).oos.writeObject(new Request(ipAdressOfClient,r.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
