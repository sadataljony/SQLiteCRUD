package com.sadataljony.app.android.sqlitecrud.db;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class SQLiteTables {

    // person table structure
    public String person = "CREATE TABLE  IF NOT EXISTS person" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "name VARCHAR UNIQUE," +
            "age INTEGER," +
            "phone VARCHAR," +
            "email VARCHAR," +
            "address VARCHAR," +
            "salary DOUBLE)";
}


