package com.database;
import java.net.*;
import java.io.*;
/**
 * ����� ������� ���� ������
 */
public class Server {

    public static void main(String[] ar)    {
        int port = 6666; // ��������� ���� (����� ���� ����� ����� �� 1025 �� 65535)
        TestSer ts = new TestSer();
        ts.name = "TEST SER";

        try {
            ServerSocket ss = new ServerSocket(port); // ������� ����� ������� � ����������� ��� � �������������� �����
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // ���������� ������ ����� ����������� � ������� ��������� ����� ���-�� �������� � ��������
            System.out.println("Got a client!");
            System.out.println();

            // ����� ������� � �������� ������ ������, ������ ����� �������� � �������� ������ �������.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // ������������ ������ � ������ ���
            ObjectOutputStream oos = new ObjectOutputStream(sout); // ������� ����� ��� �������� ��������
            oos.writeObject(ts); // ����������� � ���������� � �������� �����
            // oos.flush();
        } catch(Exception x) { x.printStackTrace(); }
    }

}
