package mainserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Класс создает поток, принимающий сообщения клиента<br>
 * Поля класса: <br>
 * {@link ThreadServiceClient#socket},
 * {@link ThreadServiceClient#ms}
 * {@link ThreadServiceClient#t},
 * {@link ThreadServiceClient#ois},
 * {@link ThreadServiceClient#ON}<br>
 *  Методы класса: <br>
 * {@link ThreadServiceClient#ThreadServiceClient(Socket, MainServer)},
 * {@link ThreadServiceClient#run()} <br>
 * @author Maslov Nikita
 */
public class ThreadServiceClient implements Runnable {
    /** Сокет соединения с клиентом*/
    Socket socket;
    /** Главный сервер*/
    MainServer ms;
    /** Поток, принимающий сообщения от клиента*/
    Thread t;
    /** Создание потока приема информации*/
    ObjectInputStream ois;
    /** Состояние сервера*/
    int ON = 1;

    /** Создается новый объект {@link ThreadServiceClient}
     * @param _socket сокет соединения с клиентом
     * @param _ms главный сервер
     */
    ThreadServiceClient(Socket _socket, MainServer _ms){
        ms = _ms;
        socket = _socket;
        t = new Thread(this);
        t.start();
    }

    /** Принимается запрос клиента, обрабатывается и отправляется на рабочий сервер
     * @see workserver.Workserver
     * @see MainServer
     * @see Request
     */
    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            Request r;

            while (true) {
                r = (Request)ois.readObject();
                System.err.println(r);
                String stringRequest = r.getNosqlR();
                String[] stringListRequest = stringRequest.split(" ");

                r.setNameTable(stringListRequest[stringListRequest.length - 1]);

                String ipAdressOfClient = socket.getInetAddress().toString();
                ipAdressOfClient = ipAdressOfClient.replace("/", "");

                r.to = ipAdressOfClient;
                r.setIsOriginal(false);

                switch (stringListRequest[0].toLowerCase()) {
                    case "create": {
                        System.out.println(r.getNosqlR() + " -> create");
                        String main;
                        String reserv;
                        if(ms.listOfWorkserver.get(ms.takenTable).getStatus() == ON ){
                            main = ms.listOfWorkserver.get(ms.takenTable).getIpAdress();
                            ms.takenTable++;
                            ms.takenTable = ms.takenTable % ms.listOfWorkserver.size();
                            if(ms.listOfWorkserver.get(ms.takenTable).getStatus() == ON )
                            {
                                reserv = ms.listOfWorkserver.get(ms.takenTable).getIpAdress();
                            } else {
                                ms.takenTable++;
                                ms.takenTable = ms.takenTable % ms.listOfWorkserver.size();
                                reserv = ms.listOfWorkserver.get(ms.takenTable).getIpAdress();
                            }
                        } else {
                            ms.takenTable++;
                            ms.takenTable = ms.takenTable % ms.listOfWorkserver.size();
                            main = ms.listOfWorkserver.get(ms.takenTable).getIpAdress();
                            ms.takenTable++;
                            ms.takenTable = ms.takenTable % ms.listOfWorkserver.size();
                            reserv = ms.listOfWorkserver.get(ms.takenTable).getIpAdress();
                        }
                        if(ms.availableTables.addTable(stringListRequest[1], main, reserv) == 0 ) {
                            ms.availableTables.saveTable();

                            r.setIsOriginal(true);
                            ms.getItemListOfWorkserver(main).oos.writeObject(r);

                            r.setIsOriginal(false);
                            ms.getItemListOfWorkserver(reserv).oos.writeObject(r);
                        }else{
                            ObjectOutputStream oos = null;
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.flush();
                            r.setNosqlR("table create yet!");
                            oos.writeObject(r);
                        }
                        break;
                    }
                    case "output":
                    case "download": {
                        System.out.println(r.getNosqlR() + " -> output or download");
                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")){
                            if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                r.setIsOriginal(true);
                                ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                            } else if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                r.setIsOriginal(true);
                                ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                            }
                        }
                        else{
                            ObjectOutputStream oos = null;
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.flush();
                            r.setNosqlR("Name table not found!");
                            oos.writeObject(r);
                        }
                        break;
                    }
                    case "add": {
                        System.out.println(r.getNosqlR() + " -> add");
                        String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                        String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                        if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")){
                            if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                r.setIsOriginal(true);
                                ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                            }
                            if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON ) {
                                if(r.getIsOriginal()){
                                    r.setIsOriginal(false);
                                }
                                else r.setIsOriginal(true);
                                ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                            }
                        }
                        else{
                            ObjectOutputStream oos = null;
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.flush();
                            r.setNosqlR("Name table not found!");
                            oos.writeObject(r);
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
                                    r.setIsOriginal(true);
                                    ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                                }
                                if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                    if(r.getIsOriginal()){
                                        r.setIsOriginal(false);
                                    }
                                    else r.setIsOriginal(true);
                                    ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                                }
                            }
                            else{
                                ObjectOutputStream oos = null;
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.flush();
                                r.setNosqlR("Name table not found!");
                                oos.writeObject(r);
                            }
                            break;
                        } else {
                            String ipMainServer = ms.availableTables.getMainServerIP(stringListRequest[stringListRequest.length - 1]);
                            String ipReserveServer = ms.availableTables.getReserveServerIP(stringListRequest[stringListRequest.length - 1]);
                            ms.availableTables.removeTable(stringListRequest[1]);
                            ms.availableTables.saveTable();
                            if(!ipMainServer.equalsIgnoreCase("") && !ipReserveServer.equalsIgnoreCase("")) {
                                if (ms.getItemListOfWorkserver(ipMainServer).getStatus() == ON) {
                                    r.setIsOriginal(true);
                                    ms.getItemListOfWorkserver(ipMainServer).oos.writeObject(r);
                                }
                                if (ms.getItemListOfWorkserver(ipReserveServer).getStatus() == ON) {
                                    if(r.getIsOriginal()){
                                        r.setIsOriginal(false);
                                    }
                                    else r.setIsOriginal(true);
                                    ms.getItemListOfWorkserver(ipReserveServer).oos.writeObject(r);
                                }
                            }
                            else{
                                ObjectOutputStream oos = null;
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.flush();
                                r.setNosqlR("Name table not found!");
                                oos.writeObject(r);
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
