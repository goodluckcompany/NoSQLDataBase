package mainserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** ����� ������� �����, ����������� ��������� � �������� �������.<br>
 * ���� ������: <br>
 * {@link ThreadInputStreamFromWorkserver#thread},
 * {@link ThreadInputStreamFromWorkserver#ms}
 * {@link ThreadInputStreamFromWorkserver#IOStreamWorkserver},
 * {@link ThreadInputStreamFromWorkserver#r},
 * {@link ThreadInputStreamFromWorkserver#tmpSocket} <br>
 *  ������ ������: <br>
 * {@link ThreadInputStreamFromWorkserver#ThreadInputStreamFromWorkserver(MainServer, InputOutputStreamWorkserver)},
 * {@link ThreadInputStreamFromWorkserver#run()} <br>
 * @author Maslov Nikita
 */
public class ThreadInputStreamFromWorkserver implements Runnable {
    /** �����, ����������� ��������� �� �������� �������*/
    Thread thread;
    /** ������� ������*/
    MainServer ms;
    /** ������ ����� � ������� ��������*/
    InputOutputStreamWorkserver IOStreamWorkserver;
    /** ������������ ������*/
    Request r;
    /** ����� ��� �������� ��������� ��������*/
    Socket tmpSocket;

    /** ��������� ����� ������ {@link ThreadInputStreamFromWorkserver}
     * @param _ms ������� ������
     * @param _IOStreamWorkserver ������ ����� � ������� ��������
     */
    ThreadInputStreamFromWorkserver(MainServer _ms, InputOutputStreamWorkserver _IOStreamWorkserver){
        ms = _ms;
        IOStreamWorkserver = _IOStreamWorkserver;
        thread = new Thread(this);
        thread.start();
    }

    /** ����������� ��������� �� �������� ������� � ���������������� �������
     * @see InputOutputStreamWorkserver
     * @see MainServer
     */
    @Override
    public void run() {
        while (true){
            try {
                r = (Request)IOStreamWorkserver.ios.readObject();
            } catch (IOException e) {
                ms.amountInaccessibleServer++;
                IOStreamWorkserver.status = 0;
                System.err.println("Connection with " + IOStreamWorkserver.getSocket() + " is lost!");
                e.printStackTrace();
                try {
                    IOStreamWorkserver.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }



            if(ms.isItemListOfClient(r.getTo())) {
                tmpSocket = ms.getItemListOfClient(r.getTo());
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(tmpSocket.getOutputStream());
                    oos.flush();

                    Request req1 = new Request(r.getTo(),r.getNosqlR());
                    req1.setReqItems(r.getReqItems());
                    //System.out.println(tmpSocket);
                    //System.out.println(r);

                    oos.writeObject(req1);
                    oos.flush();

                } catch (IOException e) {
                    System.err.println(tmpSocket + " has closed!");
                }
                }
            }
        if(ms.amountInaccessibleServer > 1){
            System.err.println("Amount inaccessible server is more than one!");
            ms.closeConnectionToListWorkserver();
            System.exit(0);
        }
    }
}
