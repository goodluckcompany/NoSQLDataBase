package mainserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by homie on 26.11.2015.
 */
public class ThreadInputStreamFormWorkserver implements Runnable {
    Thread thread;
    MainServer ms;
    InputOutputStreamWorkserver IOStreamWorkserver;
    Request r;
    Socket tmpSocket;

    ThreadInputStreamFormWorkserver(MainServer _ms,InputOutputStreamWorkserver _IOStreamWorkserver){
        ms = _ms;
        IOStreamWorkserver = _IOStreamWorkserver;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (true){
                r = (Request)IOStreamWorkserver.ios.readObject();
                if(ms.isItemListOfClient(r.getTo())){
                    tmpSocket = ms.getItemListOfClient(r.getTo());
                }
                //System.out.println(ms.listOfClient);
                //System.out.println(r);
                //System.out.println(tmpSocket);
                if(tmpSocket != null) {
                    ObjectOutputStream oos = new ObjectOutputStream(tmpSocket.getOutputStream());
                    oos.flush();
                    oos.writeObject(r);
                    ms.listOfClient.remove(tmpSocket);
                    tmpSocket.close();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
