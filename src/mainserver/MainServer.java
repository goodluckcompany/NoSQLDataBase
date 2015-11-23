package mainserver;


import java.io.*;
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
    Request r;
    public static void main(String[] args) throws IOException {
        MainServer ms = new MainServer();

        ms.loadListWorkServers();
        ms.loadWorkServerSockets();
        ms.showListWorkServers();

        Socket socket = ms.sockestOfWorkServers.get(0);

        OutputStream sout = socket.getOutputStream();
        // Конвертируем поток в другой тип
        ObjectOutputStream oos = new ObjectOutputStream(sout);



        int i = 0;
        while (i<10){
           ms.r = new Request();
           oos.writeObject(ms.r);
           i++;
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
