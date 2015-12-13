package mainserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by homie on 26.11.2015.
 */
public class ThreadInputStreamFromWorkserver implements Runnable {
    Thread thread;
    MainServer ms;
    InputOutputStreamWorkserver IOStreamWorkserver;
    Request r;
    Socket tmpSocket;

    ThreadInputStreamFromWorkserver(MainServer _ms, InputOutputStreamWorkserver _IOStreamWorkserver){
        ms = _ms;
        IOStreamWorkserver = _IOStreamWorkserver;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                r = (Request)IOStreamWorkserver.ios.readObject();
            } catch (IOException e) {
                ms.amountInaccessibleServer++;
                IOStreamWorkserver.status = 0;
                System.err.println("Connection with " + IOStreamWorkserver.getSocket() + " is lost!");
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(ms.isItemListOfClient(r.getTo())) {
                tmpSocket = ms.getItemListOfClient(r.getTo());
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(tmpSocket.getOutputStream());
                    oos.flush();
                    oos.writeObject(r);
                } catch (IOException e) {
                    System.err.println(tmpSocket + " has closed!");
                    try {
                        tmpSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                }
            }
        if(ms.amountInaccessibleServer > 1){
            System.err.println("Amount inaccessible server is more than one!");
            System.exit(0);
        }
    }
}
