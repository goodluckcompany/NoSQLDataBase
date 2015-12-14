package mainserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by homie on 22.11.2015.
 */
public class MainServer {
    volatile ArrayList<InputOutputStreamWorkserver> listOfWorkserver;/*ѕеременна€ хранит список –абочих серверов*/
    Iterator<InputOutputStreamWorkserver> itr;

    volatile LinkedList<Socket> listOfClient;/*’ранит список подключенных на данный момент клиентов,
    у которых обрабатываетс€ запрос*/

    BufferedReader bufread = null;
    BufferedReader consoleInput = null;
    String cString = null;
    int WORK_PORT = 6667;
    int CLIENT_PORT = 6661;
    int MAX_QUEUE = 100;
    volatile int amountWorkserver = 0;/*—читает сколько всего рабочих серверов запущенно, если значение более 1, то возращает
         true иначе возращает false.*/
    volatile int amountInaccessibleServer = 0;/*—читает количество недоступных серверов, если значение более 1, то
        возращ€ет False, в противном случает True*/
    Request r;

    AvailableTables availableTables;

    volatile Socket tempSocket;/*’ранит сокет только что подключившегос€ клиента, до передачи его в другой поток*/
    ServerSocket socketToClient;/*—ерверный сокет, который ожидает подключени€ новых клиентов*/

    public static void main(String[] args) throws IOException {
        MainServer ms = new MainServer();

        if(!ms.loadListWorkServers()){
            ms.closeConnectionToListWorkserver();
            System.exit(0);
        };
        ms.showListWorkServers();
        ms.initThreadISFromClient();

        while (true){
            System.out.println("Wait user...");
            ms.tempSocket = ms.socketToClient.accept();
            ms.listOfClient.add(ms.tempSocket);

            new ThreadServiceClient(ms.tempSocket,ms);
            System.out.println("User connect to be!");
        }
    }

    public MainServer(){
        listOfWorkserver = new ArrayList<InputOutputStreamWorkserver>();
        listOfClient = new LinkedList<Socket>();
        try {
            socketToClient = new ServerSocket(CLIENT_PORT,MAX_QUEUE);
        } catch (IOException e) {
            System.err.println("Error with create socket of client!");
        }
        availableTables = new AvailableTables();
    }

    public boolean loadListWorkServers(String pathToListWorkserver){/*”станавливает соединение с рабочими серверами из
    файла*/
        try{
            bufread = new BufferedReader(new FileReader(pathToListWorkserver));
            while ((cString = bufread.readLine()) != null)
            {
                try {
                    tempSocket = new Socket(cString,WORK_PORT);
                    if(tempSocket.isConnected()) {
                        listOfWorkserver.add(new InputOutputStreamWorkserver(tempSocket));
                    }
                }
                catch (Exception e)
                {
                    System.err.println("Do not add "+cString);
                    amountInaccessibleServer++;
                }
                if(amountInaccessibleServer > 1) {
                    System.err.println("Amount inaccessible server is more than one!");
                    return false;
                };
                amountWorkserver++;
            }
            if(amountWorkserver < 2) {
                System.err.println("Amount work server less than two!");
                return false;
            }
            return true;
        }
        catch (IOException e)
        {
            System.err.println("Can not find or read: "+ pathToListWorkserver);
            return false;
        }
        finally {
            try {
                if (bufread != null)
                    bufread.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean loadListWorkServers(){/*ѕерегруженна€ функци€ соединени€ с рабочими серверами*/
        return loadListWorkServers("listworkservers.lst");
    }

    public void showListWorkServers(){/*¬ыводит список сокектов всех соединенных серверов*/
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next().getSocket());
        }
    }

    public InputOutputStreamWorkserver getItemListOfWorkserver(String _ipAdress){/*¬озращает –абочий сервер по
    определенному IP адресу.*/
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals("/"+_ipAdress)) return tmp;
        }
        return null;
    }

    public boolean isItemListOfWorkserver(String _ipAdress) { /*ќпределение наличи€ –абочего сервера с определенным
    IP адресом */
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals("/"+_ipAdress)) return true;
        }
        return false;
    }

    public boolean isItemListOfClient(String _ipAdress){/*ќпредел€ет наличие соединени€ с клиентом с определенным
    IP адресом*/
        Iterator<Socket> iterator = listOfClient.iterator();
        while (iterator.hasNext()){
            Socket tmp = iterator.next();
            if(tmp.getInetAddress().toString().equals("/"+_ipAdress)) return true;
        }
        return false;
    }

    public Socket getItemListOfClient(String _ipAdress){/*¬озращает сокет клиента с определенным IP адресом*/
        Iterator<Socket> iterator = listOfClient.iterator();
        while (iterator.hasNext()){
            Socket tmp = iterator.next();
            if(tmp.getInetAddress().toString().equals("/"+_ipAdress)) return tmp;
        }
        return null;
    }

    public void initThreadISFromClient(){/*»нициализирует потоки принимающие сообщени€ с Workserver*/
        itr = listOfWorkserver.iterator();
        while (itr.hasNext()){
            new ThreadInputStreamFromWorkserver(this,itr.next());
        }
    }

    public void closeConnectionToListWorkserver(){/*«акрывает все соединени€ с рабоими серверами*/
        itr = listOfWorkserver.iterator();
        Socket tmpSck;
        while(itr.hasNext()) {
            try {
                tmpSck = itr.next().getSocket();
                tmpSck.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
