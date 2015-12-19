package mainserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Класс создает поток, принимающий сообщения с рабочего сервера.<br>
 * Поля класса: <br>
 * {@link ThreadInputStreamFromWorkserver#thread},
 * {@link ThreadInputStreamFromWorkserver#ms}
 * {@link ThreadInputStreamFromWorkserver#IOStreamWorkserver},
 * {@link ThreadInputStreamFromWorkserver#r},
 * {@link ThreadInputStreamFromWorkserver#tmpSocket} <br>
 *  Методы класса: <br>
 * {@link ThreadInputStreamFromWorkserver#ThreadInputStreamFromWorkserver(MainServer, InputOutputStreamWorkserver)},
 * {@link ThreadInputStreamFromWorkserver#run()} <br>
 * @author Maslov Nikita
 */
public class ThreadInputStreamFromWorkserver implements Runnable {
    /** Поток, принимающий сообщения от рабочего сервера*/
    Thread thread;
    /** Главный сервер*/
    MainServer ms;
    /** Каналы связи с рабочим сервером*/
    InputOutputStreamWorkserver IOStreamWorkserver;
    /** Передаваемый запрос*/
    Request r;
    /** Сокет для передачи сообщений клиентам*/
    Socket tmpSocket;

    /** Создается новый объект {@link ThreadInputStreamFromWorkserver}
     * @param _ms главный сервер
     * @param _IOStreamWorkserver каналы связи с рабочим сервером
     */
    ThreadInputStreamFromWorkserver(MainServer _ms, InputOutputStreamWorkserver _IOStreamWorkserver){
        ms = _ms;
        IOStreamWorkserver = _IOStreamWorkserver;
        thread = new Thread(this);
        thread.start();
    }

    /** Считывается сообщения от рабочего сервера и перенаправляется клиенту
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
