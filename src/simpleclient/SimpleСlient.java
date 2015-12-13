package simpleclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

import mainserver.Request;
import workserver.Item;
import workserver.ResponseItem;

/**
 * Created by homie on 22.11.2015.
 */
public class Simple—lient {
    Socket socket;
    Request req;
    ResponseItem respItem;
    ObjectOutputStream oos;
    ObjectInputStream ios;
    int MAIN_PORT = 6661;

    public static void main(String[] args){
        Simple—lient sc = new Simple—lient();
        sc.sendRequest(new Request("172.18.13.84","create cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","add key nikita value 100$ cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","add key oleg value 150$ cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","download cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","delete key oleg cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","delete all key value 100$ cash"));
        sc.waitResponse();

        sc.sendRequest(new Request("172.18.13.84","delete cash"));
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
                ResponseItem response = (ResponseItem) ios.readObject();

                Iterator<Item> itr;
                itr = response.ResponseItemList.iterator();
                while(itr.hasNext()) {
                    System.out.println(itr.next());
                }
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
