package com.sfent.playlistgenerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseReaderHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SongMetadata.db";

    public DatabaseReaderHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseReaderContract.SQL_CREATE_ENTRIES);
    }

    public ArrayList<SongMetadata> getAllSongs(SQLiteDatabase db)
    {
        ArrayList<SongMetadata> songs = new ArrayList<>();
        Cursor cursor = db.rawQuery(DatabaseReaderContract.SongEntry.TABLE_NAME, DatabaseReaderContract.SQL_COLUMNS);


        return songs;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DatabaseReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
