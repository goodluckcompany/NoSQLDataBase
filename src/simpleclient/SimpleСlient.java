package simpleclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import mainserver.Request;

/**
 * Created by homie on 22.11.2015.
 */
public class Simple—lient {
    Socket socket;
    Request req;
    ObjectOutputStream oos;
    ObjectInputStream ios;
    int MAIN_PORT = 6661;

    public static void main(String[] args){
        Simple—lient sc = new Simple—lient();
        sc.sendRequest(new Request("172.18.13.84","create cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","add key "));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","Create CASH"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","Create CASH"));
        sc.waitResponse();
    }

    Simple—lient(){
        try {
            socket = new Socket("172.18.27.29",MAIN_PORT);
        } catch (IOException e) {
            System.err.println("Server do not work now!");
        }

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    public void sendRequest(Request _request){
        System.out.println(socket);
        if(socket.isConnected()){
            try{
                oos.writeObject(_request);
            } catch (IOException e) {
                System.err.println("Sending messages is not successful");
            }
        }
    }

    public void waitResponse(){
        try {
            ios = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

        if(socket.isConnected()){
            try{
                Request response = (Request) ios.readObject();
                System.out.println(response);
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
