package workserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    public static void main(String[] args){
        new Workserver();
    }

    Workserver(){
        try {
            socketToMain = new ServerSocket(PORT,MAX_QUEUE);
            System.out.println("Wait connect...");
            socket = socketToMain.accept();
            in = socket.getInputStream();
            out = socket.getOutputStream();
            System.out.println("Connect to be!");
            out.write("Connect successfully!".getBytes());

            while (true){
                symbol = in.read();
                System.out.print((char) symbol);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
