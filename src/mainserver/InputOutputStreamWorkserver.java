package mainserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** ����� �������� ������ ��� ������� � {@link workserver.Workserver} <br>
 *  ���� ������: <br>
 * {@link InputOutputStreamWorkserver#ipAdress},
 * {@link InputOutputStreamWorkserver#socket},
 * {@link InputOutputStreamWorkserver#oos},
 * {@link InputOutputStreamWorkserver#ios},
 * {@link InputOutputStreamWorkserver#status},
 * {@link InputOutputStreamWorkserver#WORK_PORT} <br>
 *  ������ ������: <br>
 * {@link InputOutputStreamWorkserver#InputOutputStreamWorkserver(Socket)},
 * {@link InputOutputStreamWorkserver#getIpAdress()},
 * {@link InputOutputStreamWorkserver#getSocket()},
 * {@link InputOutputStreamWorkserver#getStatus()} <br>
 * @author Maslov Nikita
 */
public class InputOutputStreamWorkserver {
    /** ���� - IP ����� �������� �������*/
    String ipAdress;

    /** ����� �������� �������*/
    Socket socket;

    /** ����� ������*/
    public ObjectOutputStream oos;

    /** ����� �����*/
    public ObjectInputStream ios;

    /** ������ �������: 0 - ��������, 1 - �������*/
    int status = 0;

    /** ���� �������� �������*/
    int WORK_PORT = 6667;

    /** ��������� ����� ������ {@link InputOutputStreamWorkserver}
     * @param _socket ����� �������� �������
     */
    InputOutputStreamWorkserver(Socket _socket) {
        ipAdress = _socket.getInetAddress().toString();
        socket = _socket;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Object output stream  for socket: " + ipAdress + " : " + WORK_PORT + " do not create!");
        }
        try {
            ios = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Object input stream  for socket: " + ipAdress + " : " + WORK_PORT + " do not create!");
        }
        status = 1;
    }

    /** ���������� IP ����� �������� �������
     * @return ipAdress
     */
    public String getIpAdress() {
        return ipAdress = ipAdress.replace("/","");
    }

    /** ���������� ����� �������� �������
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /** ���������� ������ �������
     * @return status
     */
    public int getStatus(){
        return status;
    }

}
