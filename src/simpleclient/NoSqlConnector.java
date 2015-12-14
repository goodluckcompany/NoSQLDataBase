package simpleclient;

import mainserver.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Anna on 13.12.2015.
 */
public class NoSqlConnector {
    Socket socket;
    Request response;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    int MAIN_PORT = 6661;
    String mainServerIP;

    NoSqlConnector(String ip){
        this.mainServerIP = ip;
    }

    int establishConnection(){
        try {
            this.socket = new Socket(mainServerIP, MAIN_PORT);
        } catch (IOException e) {
            System.err.println(e);
            return 1;
        }
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
        } catch (IOException e) {
            System.err.println(e);
            return 2;
        }
        return 0;
    }
    
    Request sendCommand(String command){
        if(socket.isConnected()){
            try{
                oos.flush();
                oos.writeObject(response);
                oos.flush();
            } catch (IOException e) {
                System.err.println(e);
            }
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.err.println(e);
            }

            try{
                response = (Request)ois.readObject();
            } catch (ClassNotFoundException e) {
               // System.err.println(e);
                System.err.println("I was here 0");
            } catch (IOException e) {
              //  System.err.println(e);
                System.err.println("I was here 1");
            }
        }
        return response;
    }

    int breakConnection(){
        try {
            oos.close();
        } catch (IOException e) {
            System.err.println(e);
            return 1;
        }
        try {
            ois.close();
        } catch (IOException e) {
            System.err.println(e);
            return 2;
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e);
            return 3;
        }
        return 0;
    }
}
