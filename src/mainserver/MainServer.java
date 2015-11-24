package mainserver;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by homie on 22.11.2015.
 */
public class MainServer {
    ArrayList<InputOutputStreamWorkserver> listOfWorkserver;
    Iterator<InputOutputStreamWorkserver> itr;
    BufferedReader bufread = null;
    BufferedReader consoleInput = null;
    String cString = null;
    int WORK_PORT = 6667;
    Request r;

    Socket tempSocket;

    public static void main(String[] args) throws IOException {
        MainServer ms = new MainServer();

        ms.loadListWorkServers();
        ms.showListWorkServers();


        int i = 0;
        while (true){
            ms.r = new Request();
            ms.getItemListOfWorkserver("172.18.27.29").oos.writeObject(ms.r);
        }
    }

    public MainServer(){
        listOfWorkserver = new ArrayList<InputOutputStreamWorkserver>();
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
            System.err.println(tmp.getIpAdress() + " === /" +_ipAdress);
            if(tmp.getIpAdress().equals("/"+_ipAdress)) return true;
        }
        return false;
    }
}
