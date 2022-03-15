package com.enigmacamp.betatestapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class RencanaKerja extends ContentProvider {
    public static final String NAMA_PROVIDER = "com.example.rencana";
    public static Uri URI_ISI = Uri.parse("content://" + NAMA_PROVIDER + "/rencana");
    public static final String _ID = Konstanta.ID_RENCANA;
    public static final String NAMA_RENCANA = Konstanta.NAMA_RENCANA;
    public static final String SIFAT_RENCANA = Konstanta.SIFAT_RENCANA;

    private static final int RENCANA = 1;
    private static final int ID_RENCANA = 2;

    private DBHelper dbHelper;
    private SQLiteDatabase dbRencana;

    private static final UriMatcher uriMatcher = new
            UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(NAMA_PROVIDER, "rencana", RENCANA);
        uriMatcher.addURI(NAMA_PROVIDER, "rencana/#", ID_RENCANA);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        dbRencana = dbHelper.getWritableDatabase();
        return (dbRencana == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv) {
        long IDBaris = dbRencana.insert(Konstanta.NAMA_TABEL, null, cv);

        if (IDBaris > 0) {
            Uri sUri = ContentUris.withAppendedId(URI_ISI, IDBaris);
            getContext().getContentResolver().notifyChange(sUri, null);

            return sUri;
        }

        throw new SQLException("Gagal menyisipkan data ke " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues cv, String seleksi, String[] argSeleksi) {
        int tipeUri = uriMatcher.match(uri);
        int cacah = 0;

        switch (tipeUri) {
            case RENCANA:
                cacah = dbRencana.update(Konstanta.NAMA_TABEL, cv, seleksi, argSeleksi);
                break;
            case ID_RENCANA:
                cacah = dbRencana.update(Konstanta.NAMA_TABEL, cv, _ID + " = " +
                        uri.getPathSegments().get(1), null);
                break;
            default:
                throw new IllegalArgumentException("URI " + uri + " tidak kenal");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cacah;
    }

    @Override
    public int delete(Uri uri, String seleksi, String[] argSeleksi) {
        int tipeUri = uriMatcher.match(uri);
        int cacah = 0;

        switch (tipeUri) {
            case RENCANA:
                cacah = dbRencana.delete(Konstanta.NAMA_TABEL, seleksi, argSeleksi);
                break;
            case ID_RENCANA:
                cacah = dbRencana.delete(Konstanta.NAMA_TABEL, _ID + " = " +
                        uri.getPathSegments().get(1), null);
                break;
            default:
                throw new IllegalArgumentException("URI " + uri + " tidak dikenal");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cacah;
    }

    @Override
    public Cursor query(Uri uri, String[] proyeksi, String seleksi, String[] argSeleksi,
                        String pengurutan) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(Konstanta.NAMA_TABEL);
        if (uriMatcher.match(uri) == ID_RENCANA)
            sqlBuilder.appendWhere(Konstanta.ID_RENCANA + "="
                    + uri.getPathSegments().get(1));
        if (pengurutan == null || pengurutan == "")
            pengurutan = Konstanta.NAMA_RENCANA;

        Cursor c = sqlBuilder.query(dbRencana, proyeksi, seleksi, argSeleksi,
                null, null, pengurutan);
        getContext().getContentResolver().notifyChange(uri, null);
        return c;
    }
}