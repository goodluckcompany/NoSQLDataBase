package com.database;
import java.net.*;
import java.io.*;
/**
 *  ласс сервера база данных
 */
public class Server {

    public static void main(String[] ar)    {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        TestSer ts = new TestSer();
        ts.name = "TEST SER";

        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и прив€зываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставл€ем сервер ждать подключений и выводим сообщение когда кто-то св€залс€ с сервером
            System.out.println("Got a client!");
            System.out.println();

            // Ѕерем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            //  онвертируем потоки в другой тип
            ObjectOutputStream oos = new ObjectOutputStream(sout); // создаем поток дл€ передачи объектов
            oos.writeObject(ts); // —ериализуем и отправл€ем в выходной поток
            // oos.flush();
        } catch(Exception x) { x.printStackTrace(); }
    }

}
