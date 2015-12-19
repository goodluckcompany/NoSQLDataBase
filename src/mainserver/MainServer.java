package mainserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/** Класс служит для создания главного сервера со свойствами: <br>
 * {@link MainServer#listOfWorkserver}, {@link MainServer#itr}, {@link MainServer#listOfClient},
 * {@link MainServer#takenTable}, {@link MainServer#bufread}, {@link MainServer#cString}, {@link MainServer#WORK_PORT},
 * {@link MainServer#CLIENT_PORT}, {@link MainServer#MAX_QUEUE}, {@link MainServer#amountWorkserver},
 * {@link MainServer#amountInaccessibleServer}, {@link MainServer#availableTables},
 * {@link MainServer#tempSocket}, {@link MainServer#socketToClient} <br>
 *  Методы класса: <br>
 * {@link MainServer#MainServer()}, {@link MainServer#main(String[])}, {@link MainServer#getItemListOfClient(String)},
 * {@link MainServer#initThreadISFromClient()},
 * {@link MainServer#getItemListOfWorkserver(String)}, {@link MainServer#isItemListOfClient(String)},
 * {@link MainServer#loadListWorkServers()},
 * {@link MainServer#isItemListOfWorkserver(String)}, {@link MainServer#loadListWorkServers(String)},
 * {@link MainServer#showListWorkServers()}, {@link MainServer#closeConnectionToListWorkserver()}
 */

public class MainServer {
    /** Свойство - список Рабочих серверов*/
    volatile ArrayList<InputOutputStreamWorkserver> listOfWorkserver;

    /** Свойство - итератор для прохождения списка InputOutputStreamWorkserver*/
    Iterator<InputOutputStreamWorkserver> itr;

    /** Свойство - список подключенных на данный момент клиентов, у которых обрабатывается запрос*/
    volatile LinkedList<Socket> listOfClient;

    /** Свойство - порядковый номер таблицы*/
    int takenTable = 0;

    /** Свойство - результат чтения с консоли*/
    BufferedReader bufread = null;

    /** Свойство - ip адрес*/
    String cString = null;

    /** Свойство - порт рабочего сервера*/
    int WORK_PORT = 6667;

    /** Свойство - порт клиента*/
    int CLIENT_PORT = 6661;

    /** Свойство - максимальное количество клиентов*/
    int MAX_QUEUE = 100;

    /** Свойство - количество запущенных рабочих сервров*/
    volatile int amountWorkserver = 0;

    /** Свойство - количество недоступных серверов*/
    volatile int amountInaccessibleServer = 0;

    /** Свойство - список доступных таблиц*/
    AvailableTables availableTables;

    /** Свойство - сокет только что подключившегося клиента до передачи его в другой поток*/
    volatile Socket tempSocket;
    /** Свойство - серверный сокет, который ожидает подключения новых клиентов*/
    ServerSocket socketToClient;

    /** Создается объект {@link MainServer}, загружается список рабочих сервров ({@link workserver.Workserver}).
     *  Если loadListWorkServers() возвращает false,
     *  то {@link MainServer} прекращает свою работу, иначе выводится список
     *  подключенных рабочих сервров.
     *  Ожидается подключение клиентов.
     * @see MainServer#MainServer()
     */
    public static void main(String[] args) throws IOException {
        MainServer ms = new MainServer();

        if(!ms.loadListWorkServers()){
            ms.closeConnectionToListWorkserver();
            System.exit(0);
        };
        ms.showListWorkServers();
        ms.initThreadISFromClient();

        while (true){
            System.out.println("Wait user...");
            ms.tempSocket = ms.socketToClient.accept();
            ms.listOfClient.add(ms.tempSocket);

            new ThreadServiceClient(ms.tempSocket,ms);
            System.out.println("User connect to be!");
        }
    }

    /** Создается новый объект {@link MainServer}, инициализируются параметры
     * {@link MainServer#listOfWorkserver}, {@link MainServer#listOfClient},
     * {@link MainServer#availableTables}, {@link MainServer#socketToClient}.
     * @see AvailableTables
     */
    public MainServer(){
        listOfWorkserver = new ArrayList<InputOutputStreamWorkserver>();
        listOfClient = new LinkedList<Socket>();
        try {
            socketToClient = new ServerSocket(CLIENT_PORT,MAX_QUEUE);
        } catch (IOException e) {
            System.err.println("Error with create socket of client!");
        }
        availableTables = new AvailableTables();
    }

    /** Функция загружает список рабочих серверов из файла
     * @param pathToListWorkserver путь к файлу списка серверов
     * @return true, если все сервера работают,<br>
     * false, если более одного сервера не работает.
     */
    public boolean loadListWorkServers(String pathToListWorkserver){/*Устанавливает соединение с рабочими серверами из
    файла*/
        try{
            bufread = new BufferedReader(new FileReader(pathToListWorkserver));
            while ((cString = bufread.readLine()) != null)
            {
                try {
                    tempSocket = new Socket(cString,WORK_PORT);
                    if(tempSocket.isConnected()) {
                        listOfWorkserver.add(new InputOutputStreamWorkserver(tempSocket));
                    }
                }
                catch (Exception e)
                {
                    System.err.println("Do not add "+cString);
                    amountInaccessibleServer++;
                }
                if(amountInaccessibleServer > 1) {
                    System.err.println("Amount inaccessible server is more than one!");
                    return false;
                };
                amountWorkserver++;
            }
            if(amountWorkserver < 2) {
                System.err.println("Amount work server less than two!");
                return false;
            }
            return true;
        }
        catch (IOException e)
        {
            System.err.println("Can not find or read: "+ pathToListWorkserver);
            return false;
        }
        finally {
            try {
                if (bufread != null)
                    bufread.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Функция загружает список рабочих серверов из файла по умолчанию (listworkservers.lst)
     * @return true, если все сервера работают,<br>
     * false, если более одного сервера не работает.
     */
    public boolean loadListWorkServers(){
        return loadListWorkServers("listworkservers.lst");
    }

    /** Выводит на консоль список сокектов всех соединенных серверов
     */
    public void showListWorkServers(){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next().getSocket());
        }
    }

    /** Возращает рабочий сервер по определенному IP адресу
     * @param _ipAdress IP адрес
     * @return объект класса {@link InputOutputStreamWorkserver}, если сервер с таким адресом есть,<br>
     * null, в другом случае.
     */
    public InputOutputStreamWorkserver getItemListOfWorkserver(String _ipAdress){
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals(_ipAdress)) return tmp;
        }
        return null;
    }

    /** Определение наличия Рабочего сервера с определенным IP адресом
     * @param _ipAdress IP адрес
     * @return true, если такой сервер есть,<br>
     * false, в другом случае
     * @see workserver.Workserver
     */
    public boolean isItemListOfWorkserver(String _ipAdress) {
        itr = listOfWorkserver.iterator();
        while(itr.hasNext()) {
            InputOutputStreamWorkserver tmp = itr.next();
            if(tmp.getIpAdress().equals(_ipAdress)) return true;
        }
        return false;
    }

    /** Определяет наличие соединения с клиентом с определенным IP адресом
     * @param _ipAdress IP адрес
     * @return true, если соединение есть,<br>
     * false, в другом случае
     */
    public boolean isItemListOfClient(String _ipAdress){
        Iterator<Socket> iterator = listOfClient.iterator();
        while (iterator.hasNext()){
            Socket tmp = iterator.next();
            if(tmp.getInetAddress().toString().equals("/"+_ipAdress)) return true;
        }
        return false;
    }

    /** Возращает сокет клиента с определенным IP адресом
     * @param _ipAdress IP адрес
     * @return объект класса {@link Socket}, если сокет с таким адресом есть,<br>
     * false, в другом случае
     */
    public Socket getItemListOfClient(String _ipAdress){
        Iterator<Socket> iterator = listOfClient.iterator();
        while (iterator.hasNext()){
            Socket tmp = iterator.next();
            if(tmp.getInetAddress().toString().equals("/"+_ipAdress)) return tmp;
        }
        return null;
    }

    /** Инициализирует потоки, принимающие сообщения с Workserver
     * @see workserver.Workserver
     */
    public void initThreadISFromClient(){
        itr = listOfWorkserver.iterator();
        while (itr.hasNext()){
            new ThreadInputStreamFromWorkserver(this,itr.next());
        }
    }

    /** Закрывает все соединения с рабочими серверами
     */
    public void closeConnectionToListWorkserver(){
        itr = listOfWorkserver.iterator();
        Socket tmpSck;
        while(itr.hasNext()) {
            try {
                tmpSck = itr.next().getSocket();
                tmpSck.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
