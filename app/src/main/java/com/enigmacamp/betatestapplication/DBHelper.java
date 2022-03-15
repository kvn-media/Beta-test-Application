package com.enigmacamp.betatestapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static String BUAT_TABEL = "create table " +
            Konstanta.NAMA_TABEL + " (" +
            Konstanta.ID_RENCANA + " integer primary key autoincrement, " +
            Konstanta.SIFAT_RENCANA + " text not null, " +
            Konstanta.NAMA_RENCANA + " text not null);";

    public DBHelper(Context konteks) {
        super(konteks, Konstanta.NAMA_DB, null, Konstanta.VERSI_DB);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(BUAT_TABEL);
        }
        catch(SQLiteException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versiLama, int versiBaru) {
        db.execSQL("drop table if exists " + Konstanta.NAMA_TABEL);
        onCreate(db);
    }
}