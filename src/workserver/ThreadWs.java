package workserver;

import mainserver.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Anderson on 26.11.2015.
 */
public class ThreadWs implements Runnable {
    String ipAdress;
    Request req;
    Thread t;
    public ObjectOutputStream oos;

    ThreadWs(Request r, ObjectOutputStream _oos,List<NoSqlDB> listDb, ResponseItem items){
        ArrayList <String> list = new ArrayList<>();
        req = r;
        ipAdress = r.getTo();
        oos = _oos;
        String nosqlR = req.getNosqlR();
        NoSqlParser nsp = new NoSqlParser();
        ResponseItem is = null;

        ListOfTables lt = new ListOfTables();
        list = lt.getTables();
        if (!list.isEmpty()) {
            for (String s : list) {
                listDb.add(new NoSqlDB(s));
            }
        }
        int numtable = tableNum(req.getNameTable(),listDb);

        if (numtable == -1){
            if (nosqlR.equals("size")) {
                r.setSize(lt.getSize());
            }else {
                is = nsp.execute(nosqlR, listDb, items);
                if (is.equals(null)){
                    r.setNosqlR(r.getNosqlR() + " FAILED");
                }
            }
        }else {
            if (nosqlR.equals("size")) {
                r.setSize(lt.getSize());
            }else
            is = nsp.execute(nosqlR, listDb.get(numtable), items);
        }

        req.setReqItems(is);
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            if ( req.getIsOriginal()){
                oos.flush();
                oos.writeObject(req);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static public int tableNum(String nametable,List<NoSqlDB> listDb){
        int i = 0;
        NoSqlDB ndb = null;
        ListIterator<NoSqlDB> itr = listDb.listIterator();
        while(itr.hasNext()){
            ndb = itr.next();
            if(ndb.getDbName().equals(nametable)){
                return i;
            }
            i++;
        }
        return -1;
    }


}
