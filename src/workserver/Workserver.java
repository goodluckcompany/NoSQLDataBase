package workserver;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import mainserver.Request;
/**
 * Created by homie on 22.11.2015.
 */
public class Workserver {
    ServerSocket socketToMain;
    Socket socket;
    InputStream in;
    OutputStream out;
    int symbol;
    int PORT = 6667;
    int MAX_QUEUE = 100;
    List <NoSqlDB> listDb = new LinkedList<>();

    public static void main(String[] args){
        new Workserver();
    }

    Workserver(){
        try {
            socketToMain = new ServerSocket(PORT,MAX_QUEUE);
            System.out.println("Wait connect...");
            socket = socketToMain.accept();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
           //   in = socket.getInputStream();
           // out = socket.getOutputStream();

            ObjectInputStream ois = new ObjectInputStream(sin);
            Request r = new Request();

            System.out.println("Connect to be!");
            out.write("Connect successfully!".getBytes());

            while (true){
                r = (Request)ois.readObject();
                System.out.print(r.toString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
