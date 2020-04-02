package com.sadataljony.app.android.sqlitecrud.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // ######################### Insert Person ###########################
    // ######################### Insert Person ###########################
    // ######################### Insert Person ###########################
    public void insert_person(String strName, int intAge, String strPhone, String strEmail, String strAddress, double doubleSalary) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO  person VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, strName);
        statement.bindLong(2, intAge);
        statement.bindString(3, strPhone);
        statement.bindString(4, strEmail);
        statement.bindString(5, strAddress);
        statement.bindDouble(6, doubleSalary);
        statement.executeInsert();
    }

    // ######################### Update Person ###########################
    // ######################### Update Person ###########################
    // ######################### Update Person ###########################
    public void update_person(int intId, String strName, int intAge, String strPhone, String strEmail, String strAddress, double doubleSalary) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE person SET " + "name = ?," + "age = ?," + "phone = ?," + "email = ?," + "address = ?," + "salary = ?" + "WHERE id = " + intId;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, strName);
        statement.bindLong(2, intAge);
        statement.bindString(3, strPhone);
        statement.bindString(4, strEmail);
        statement.bindString(5, strAddress);
        statement.bindDouble(6, doubleSalary);
        statement.execute();
        database.close();
    }

    // ######################### Delete Person ###########################
    // ######################### Delete Person ###########################
    // ######################### Delete Person ###########################
    public void delete_person(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM person WHERE  id =" + id;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.execute();
        database.close();
    }

}
