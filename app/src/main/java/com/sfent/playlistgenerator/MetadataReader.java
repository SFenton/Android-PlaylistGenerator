package com.sfent.playlistgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.File;
import java.util.Date;

public class MetadataReader
{
    private String[] directories = new String[] { "Music" };
    private String mediaPath = "";
    private MediaMetadataRetriever mRetriever;
    private DatabaseReaderHelper dbHelper;
    private SQLiteDatabase db;

    public MetadataReader(DatabaseReaderHelper dbHelper)
    {
        mediaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_MUSIC;
        mRetriever = new MediaMetadataRetriever();
        this.dbHelper = dbHelper;
        db = dbHelper.getWritableDatabase();
    }

    public void UpdateMetadata()
    {
        // Temporary, until we know stuff is working properly
        db.execSQL(DatabaseReaderContract.SQL_DELETE_ENTRIES);

        GetMetadataFromFiles(mediaPath);
        db.close();
    }

    private void GetMetadataFromFiles(String directory)
    {
        File curFolder = new File(directory);
        File[] allFiles = curFolder.listFiles();

        for (File file : allFiles)
        {
            if (file.isDirectory())
            {
                GetMetadataFromFiles(file.getAbsolutePath());
            }
            else
            {
                GetMetatdataFromFile(file.getAbsolutePath());
            }
        }
    }

    private void GetMetatdataFromFile(String filePath)
    {
        if (!filePath.endsWith(".mp3"))
        {
            return;
        }

        File file = new File(filePath);

        mRetriever.setDataSource(filePath);
        String title = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String albumArtist = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        String album = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String yearString =  mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);

        int year = 0;
        if (yearString != null && !yearString.isEmpty())
        {
            year = Integer.parseInt(yearString);
        }

        String genre = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        String durationString = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int duration = 0;
        if (durationString != null && !durationString.isEmpty())
        {
            duration = Integer.parseInt(durationString);
        }

        Date modifiedDate = new Date(file.lastModified());

        ContentValues values = new ContentValues();
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_FILEPATH, filePath);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_ARTIST, artist);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_ALBUM_ARTIST, albumArtist);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_ALBUM, album);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_YEAR, year);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_GENRE, genre);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_DURATION, duration);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_ARTIST, artist);
        values.put(DatabaseReaderContract.SongEntry.COLUMN_NAME_LAST_MODIFIED_DATE, modifiedDate.toString());

        long newRowId = db.insert(DatabaseReaderContract.SongEntry.TABLE_NAME, null, values);
    }
}
