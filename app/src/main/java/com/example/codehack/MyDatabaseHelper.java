package com.example.codehack;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String dbname="ContestDB";
    private static final int version=1;
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String sql = "CREATE TABLE CONTEST (_id INTEGER PRIMARY KEY AUTOINCREMENT,EVENT TEXT,ABOUT TEXT,START TEXT)";
            sqLiteDatabase.execSQL(sql);
        }catch (Exception ex){

        }
    }
    public void insertData(String event,String about,String start_date_time,SQLiteDatabase database){
        try {
            ContentValues values=new ContentValues();
            values.put("event",event);
            values.put("about",about);
            values.put("start",start_date_time);
            database.insert("CONTEST",null,values);
        }catch (Exception ex){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
