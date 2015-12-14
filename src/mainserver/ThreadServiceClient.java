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
            Request r;

            while (true) {
                r = (Request) ois.readObject();

                String stringRequest = r.getNosqlR();
                String[] stringListRequest = stringRequest.split(" ");

                r.setNameTable(stringListRequest[stringListRequest.length - 1]);

                String ipAdressOfClient = socket.getInetAddress().toString();
                ipAdressOfClient = ipAdressOfClient.replace("/", "");

                r.to = ipAdressOfClient;

                switch (stringListRequest[0].toLowerCase()) {
                    case "create": {
                        System.out.println(r.getNosqlR() + " -> create");
                        if(ms.availableTables.addTable(stringListRequest[1], "172.18.13.84", "172.18.27.29") == 0 ) {
                            ms.availableTables.saveTable();
                            ms.getItemListOfWorkserver("172.18.13.84").oos.writeObject(r);
                            ms.getItemListOfWorkserver("172.18.27.29").oos.writeObject(r);
                        }else{

                        }
                        break;
                    }
                    case "output":
                    case "download": {
                        System.out.println(r.getNosqlR() + " -> output or download");
                        /*String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);*/

                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")){
                            if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                            } else if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                            }
                        }
                        break;
                    }
                    case "add": {
                        System.out.println(r.getNosqlR() + " -> add");
                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")){
                            if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                            }
                            if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                            }
                        }
                        break;
                    }
                    case "delete": {
                        System.out.println(r.getNosqlR() + " -> delete");
                        if (stringListRequest.length > 2) {
                            String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                            String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                            if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")) {
                                if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                    ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                                }
                                if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                    ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                                }
                            }
                            break;
                        } else {
                            String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                            String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                            ms.availableTables.removeTable(stringListRequest[1]);
                            ms.availableTables.saveTable();
                            if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")) {
                                if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                    ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                                }
                                if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                    ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            try {
                ms.listOfClient.remove(socket);
                socket.getInputStream().close();
//                socket.getOutputStream().close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
