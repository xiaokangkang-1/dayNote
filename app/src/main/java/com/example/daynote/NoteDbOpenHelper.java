package com.example.daynote;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.daynote.bean.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "noteDB.db";
    public static final String TABLE_NAME_NOTE = "dayNote";

    //创建表语句
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + "("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "created_time text)";

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

    public long insertData(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("created_time", note.getCreatedTime());
        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public List<Note> queryAllFromDB() {
        List<Note> list = new ArrayList<>();
        try (SQLiteDatabase db = getWritableDatabase();
             Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Note note = new Note();
                    int idColumnIndex = cursor.getColumnIndex("id");
                    int titleColumnIndex = cursor.getColumnIndex("title");
                    int contentColumnIndex = cursor.getColumnIndex("content");
                    int createdTimeColumnIndex = cursor.getColumnIndex("created_time");

                    // 确保列存在再获取值
                    if (idColumnIndex >= 0 && titleColumnIndex >= 0 && contentColumnIndex >= 0 && createdTimeColumnIndex >= 0) {
                        note.setId(cursor.getString(idColumnIndex));
                        note.setTitle(cursor.getString(titleColumnIndex));
                        note.setContent(cursor.getString(contentColumnIndex));
                        note.setCreatedTime(cursor.getString(createdTimeColumnIndex));
                        list.add(note);
                    } else {
                        // 如果发现列不存在，可以在这里处理错误情况
                        Log.e(TAG, "Column not found in the database.");
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

}
