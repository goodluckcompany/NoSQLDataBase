package workserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import mainserver.*;
/**
 * Created by homie on 22.11.2015.
 */




public class Workserver {


    ServerSocket socketToMain;
    Socket socket;
    InputStream in;
    OutputStream sout;
    int symbol;
    int PORT = 6667;
    int MAX_QUEUE = 100;
    List <NoSqlDB> listDb = new LinkedList<>();
    Request r;
    public static void main(String[] args){
        new Workserver();
    }

    Workserver(){





        try {
            socketToMain = new ServerSocket(PORT,MAX_QUEUE);
        } catch (IOException e) {
            System.out.println("SockeToMain");
        }
        System.out.println("Wait connect...");
        try {
            socket = socketToMain.accept();
        } catch (IOException e) {
            System.out.println("SockeToMain.accept");
        }

        InputStream sin = null;
        try {
            sin = socket.getInputStream();
        } catch (IOException e) {
            System.out.println("sin");
        }
        try {
            sout = socket.getOutputStream();

        } catch (IOException e) {
            System.out.println("sout");
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(sout);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // in = socket.getInputStream();
           // out = socket.getOutputStream();

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(sin);
        } catch (IOException e) {
            System.out.println("ois");
        }



            System.out.println("Connect to be!");
            int i = 0;
            while ( true){

                r = new Request();
                try {
                    r = (Request)ois.readObject();
                    System.out.println(r.toString());

                } catch (IOException e) {
                    System.out.printf("r=ois");
                } catch (ClassNotFoundException e) {
                    System.out.println("Request not found");
                }
                i++;
            }
        }

    }
