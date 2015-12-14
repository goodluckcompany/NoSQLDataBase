package simpleclient;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

import mainserver.Request;
import workserver.Item;
import workserver.ResponseItem;

/**
 * Created by homie on 22.11.2015.
 */
public class Simple—lient {
    Request req;
    ResponseItem respItem;
    BufferedReader br;
    String command;
    NoSqlConnector conn;

    public static void main(String[] args){
        new Simple—lient();
    }

    Simple—lient(){
        br = new BufferedReader(new InputStreamReader(System.in));
        command = "";
        conn = new NoSqlConnector("172.18.27.29");
        if(conn.establishConnection() == 0){
            System.out.println("Input command:");
            try {
                command = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(!command.equalsIgnoreCase("stop")) {
                req = conn.sendCommand(command);
                System.out.println(req.toString());
                System.out.println("Input command:");
                try {
                    command = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*
            req = conn.sendCommand("create 1");
            System.out.println(req.toString());
            req = conn.sendCommand("add key car value blue 1");
            System.out.println(req.toString());
            req = conn.sendCommand("add key vehicle value red 1");
            System.out.println(req.toString());
            req = conn.sendCommand("download 1");
            System.out.println(req);*/

            /*int key = 0;
            req = conn.sendCommand("create table_m");
            while (key < 500){
                req = conn.sendCommand("add key item"+key+" value "+key+" table_m");
                key++;
            }*/
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.breakConnection();
        }
    }


}
