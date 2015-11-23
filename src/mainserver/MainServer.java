package mainserver;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by homie on 22.11.2015.
 */
public class MainServer {
    ArrayList<String> listOfWorkserver;
    ArrayList<Socket> sockestOfWorkServers;
    Iterator<String> itr;
    BufferedReader bufread = null;
    BufferedReader consoleInput = null;
    String cString = null;
    int WORK_PORT = 6667;

    public static void main(String[] args) {
        MainServer ms = new MainServer();
        ms.loadListWorkServers();
        ms.loadWorkServerSockets();
        ms.showListWorkServers();
        while (true){
            System.in.read();
        }
    }

    public MainServer(){
        listOfWorkserver = new ArrayList<String>();
        sockestOfWorkServers = new ArrayList<Socket>();
    }

    public void loadListWorkServers(){
        try{
            bufread = new BufferedReader(new FileReader("listworkservers.lst"));
            while ((cString = bufread.readLine()) != null)
            {
                listOfWorkserver.add(cString);
            }
        }
        catch (IOException e)
        {
            System.err.println("Can not find or read: listworkservers.lst");
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

    public void showListWorkServers(){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public void loadWorkServerSockets(){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            try {
                sockestOfWorkServers.add(new Socket(itr.next(), WORK_PORT));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
