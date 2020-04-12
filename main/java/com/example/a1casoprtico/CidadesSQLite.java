package com.example.a1casoprtico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class CidadesSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "Cidades.db";
    private static final int DB_VERSION = 1;

    public CidadesSQLite (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        if (db.isReadOnly()) {
            db = getWritableDatabase();
        }
        db.execSQL("CREATE TABLE cidades (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, cidade TEXT);");

    }

    public void adicionar (SQLiteDatabase db, String reg){
        db.execSQL("INSERT INTO cidades (cidade) VALUES('" + reg + "')");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS cidades");
        onCreate(db);
    }
}

