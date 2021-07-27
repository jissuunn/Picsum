package com.example.blast_asiaexam_jayson.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.blast_asiaexam_jayson.Model.Items;

import java.util.ArrayList;
import java.util.List;

public class DbHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "picsum";

    private static final String CREATE_ITEMS =
            String.format("CREATE TABLE [ITEMS_DATA](" +
                    "[ID][nvarchar](255) NULL," +
                    "[AUTHOR][nvarchar](255) NULL," +
                    "[WIDTH][int] (11) NULL," +
                    "[HEIGHT][int](11) NULL," +
                    "[URL][nvarchar](255) NULL, " +
                    "[DOWNLOAD_URL][nvarchar](255) NULL)");

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                          DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ITEMS_DATA");
        db.execSQL(CREATE_ITEMS);
    }

    public void insert_items(List<Items> items){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Items item : items) {
                if(!check(item.getId())){
                    values.put("ID", item.getId());
                    values.put("AUTHOR", item.getAuthor());
                    values.put("WIDTH", item.getWidth());
                    values.put("HEIGHT", item.getHeight());
                    values.put("URL", item.getUrl());
                    values.put("DOWNLOAD_URL", item.getDownload_url());
                    db.insert("ITEMS_DATA", null, values);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Items> getData(int page, int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Items> items = new ArrayList<>();
        db.beginTransaction();
        try{
            String query = "SELECT * FROM ITEMS_DATA LIMIT " + ((page-1)*limit) + ", " + limit;
            Cursor cursor = db.rawQuery(query,null);
            while (cursor.moveToNext()){
                Items item = new Items();
                item.setId(cursor.getString(cursor.getColumnIndex("ID")));
                item.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
                item.setWidth(cursor.getString(cursor.getColumnIndex("WIDTH")));
                item.setHeight(cursor.getString(cursor.getColumnIndex("HEIGHT")));
                item.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
                item.setDownload_url(cursor.getString(cursor.getColumnIndex("DOWNLOAD_URL")));
                items.add(item);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        return  items;
    }

    public boolean check(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        boolean exist = false;
        try{
            String query = "SELECT COUNT(*) FROM ITEMS_DATA WHERE ID = " + id;
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            if(cursor.getInt(0) != 0){
                exist = true;
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        return  exist;
    }

}