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
    int ON = 1;
    int OFF = 0;

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

            String stringRequest = r.getNosqlR();
            String[] stringListRequest = stringRequest.split(" ");

            String ipAdressOfClient = socket.getInetAddress().toString();
            ipAdressOfClient = ipAdressOfClient.replace("/","");

            switch (stringListRequest[0].toLowerCase()){
                case "create":
                {
                    ms.availableTables.addTable(stringListRequest[1],"172.18.13.84","172.18.27.29");
                    ms.availableTables.saveTable();
                    ms.getItemListOfWorkserver("172.18.13.84").oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    ms.getItemListOfWorkserver("172.18.27.29").oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    break;
                }
                case "output":
                case "download":
                {
                    String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length-1]);
                    String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                    if(ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON){
                        ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    }
                    else if(ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                        ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    }
                    break;
                }
                case "add":
                {
                    String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length-1]);
                    String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                    if(ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON){
                        ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    }
                    if(ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                        ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                    }
                    break;
                }
                case "delete":
                {
                    if(stringListRequest.length > 2)
                    {
                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length-1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        if(ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON){
                            ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                        }
                        if(ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                            ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                        }
                        break;
                    }
                    else {
                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length-1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        ms.availableTables.removeTable(stringListRequest[1]);
                        ms.availableTables.saveTable();

                        if(ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON){
                            ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                        }
                        if(ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                            ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(new Request(ipAdressOfClient,r.getNosqlR()));
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
