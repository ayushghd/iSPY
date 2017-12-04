package com.example.ajaykumar.drawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AJAY KUMAR on 9/27/2017.
 */
public class NDb extends SQLiteOpenHelper {
    public static final String dbname = "MyNotes1.db";
    public static final String _id = "_id";
    public static final String name = "name";
    public static final String remark = "remark";
    public static final String dates = "dates";
    public static final String mynotes = "mynotes";
    private HashMap hp;
    SQLiteDatabase db;
    public NDb(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table mynotes"
                + "(_id integer primary key, name text,remark text,dates text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + mynotes);
        onCreate(db);
    }
    public Cursor fetchAll() {
        db = this.getReadableDatabase();
        Cursor mCursor = db.query(mynotes, new String[] { "_id", "name",
                "dates", "remark" }, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean insertNotes(String name, String dates, String remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("dates", dates);
        contentValues.put("remark", remark);
        db.insert(mynotes, null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor z = db.rawQuery("select * from " + mynotes + " where _id=" + id
                + "", null);
        return z;
    }
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, mynotes);
        return numRows;
    }
    public boolean updateNotes(Integer id, String name, String dates,
                               String remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("dates", dates);
        contentValues.put("remark", remark);
        db.update(mynotes, contentValues, "_id = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }
    public Integer deleteNotes(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(mynotes, "_id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList getAll() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + mynotes, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("_id")));
            array_list.add(res.getString(res.getColumnIndex(remark)));
            array_list.add(res.getString(res.getColumnIndex(dates)));
            array_list.add(res.getString(res.getColumnIndex(name)));
            res.moveToNext();
        }
        return array_list;
    }
}