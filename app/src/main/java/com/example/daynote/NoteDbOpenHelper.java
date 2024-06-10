package com.example.daynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daynote.bean.Note;

public class NoteDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "noteDB.db";
    public static final String TABLE_NAME_NOTE = "dayNote";

    //创建表语句
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + "("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "created-time text)";

    public NoteDbOpenHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
