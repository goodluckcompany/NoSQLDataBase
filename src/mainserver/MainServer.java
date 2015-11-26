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
    volatile ArrayList<InputOutputStreamWorkserver> listOfWorkserver;
    Iterator<InputOutputStreamWorkserver> itr;

    LinkedList<Socket> listOfClient;

    BufferedReader bufread = null;
    BufferedReader consoleInput = null;
    String cString = null;
    int WORK_PORT = 6667;
    int CLIENT_PORT = 6661;
    int MAX_QUEUE = 100;
    Request r;

    volatile Socket tempSocket;
    ServerSocket socketToClient;

    public static void main(String[] args) throws IOException {
        MainServer ms = new MainServer();

        ms.loadListWorkServers();
        ms.showListWorkServers();

        while (true){
            System.out.println("Wait user...");
            ms.tempSocket = ms.socketToClient.accept();
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
    }

    public void loadListWorkServers(String pathToListWorkserver){
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
                }

            }
        }
        catch (IOException e)
        {
            System.err.println("Can not find or read: "+ pathToListWorkserver);
        }
        finally
        {
            try
            {
                if (bufread != null)
                    bufread.close();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void loadListWorkServers(){
        loadListWorkServers("listworkservers.lst");
    }

    public void showListWorkServers(){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next().getSocket());
        }
    }

    public InputOutputStreamWorkserver getItemListOfWorkserver(String _ipAdress){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals("/"+_ipAdress)) return tmp;
        }
        return null;
    }

    public boolean isItemListOfWorkserver(String _ipAdress) {
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals("/"+_ipAdress)) return true;
        }
        return false;
    }
}
