package com.database;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Anna on 26.10.2015.
 */
public class UserInterface<K, V> {
    Hashtable db;

    UserInterface(Hashtable table) {
        db = table;
    }

    //добавить запись в таблицу
    public void addToDb(K key, V value){
        db.put(key, value);
    }

    //получить значение по ключу
    public Object getValue(K key){
        if (db.get(key) != null)
            return db.get(key);
        else return "Not found";
    }

    //получить все записи в таблице
    public StringBuilder readAll(){
        StringBuilder sb = new StringBuilder("");
        Enumeration en = db.keys(); //получаем все ключи
        K key;
        V value;

        while(en.hasMoreElements()){ //проходим по всем ключам и получаем значения
            key = (K)en.nextElement();
            value = (V)db.get(key);
            sb.append(key + ": " + value + "\n");
        }

        return sb;
    }

    //удалить запись по ключу
    public void rmvFromDb(K key){
        db.remove(key);
    }

    //удалиь все
    public void deleteAll(){
        db.clear();
    }
    
}
