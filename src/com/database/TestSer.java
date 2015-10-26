package com.database;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by Anderson on 25.10.2015.
 */
public class TestSer implements Serializable {
    String name;
    Hashtable<String,String> table;
}
