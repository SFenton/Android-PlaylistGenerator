package com.sfent.playlistgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MetadataReader extends AsyncTask<DatabaseReaderHelper, Integer, Integer>
{
    private String[] directories = new String[] { "Music" };
    private String mediaPath = "";
    private MediaMetadataRetriever mRetriever;
    private DatabaseReaderHelper dbHelper;
    private SQLiteDatabase db;
    private HashMap<String, SongMetadata> songMap;
    public AsyncResponse delegate = null;

    public MetadataReader(DatabaseReaderHelper dbHelper, AsyncResponse delegate)
    {
        mediaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_MUSIC;
        mRetriever = new MediaMetadataRetriever();
        songMap = new HashMap<>();
        this.dbHelper = dbHelper;
        db = dbHelper.getWritableDatabase();
        this.delegate = delegate;
    }

    public void UpdateMetadata()
    {
        // Read database into hash map
        ReadDatabase();

        GetMetadataFromFiles(mediaPath);
        db.close();
    }

    private void ReadDatabase()
    {
        Cursor cursor = db.rawQuery(DatabaseReaderContract.SQL_RETRIEVE_ENTRIES, null);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));

        try
        {
            while (cursor.moveToNext())
            {
                SongMetadata song = new SongMetadata();
                song.setFilePath(cursor.getString(1));
                song.setTitle(cursor.getString(2));
                song.setArtist(cursor.getString(3));
                song.setAlbumArtist(cursor.getString(4));
                song.setAlbum(cursor.getString(5));
                song.setYear(cursor.getInt(6));
                song.setGenre(cursor.getString(7));
                song.setDuration(cursor.getInt(8));
                song.setLastModifiedDate(sdf.parse(cursor.getString(9)));

                // Delete the entry and return if it doesn't exist
                File file = new File(song.getFilePath());
                if (!file.exists())
                {
                    db.delete(DatabaseReaderContract.SongEntry.TABLE_NAME, DatabaseReaderContract.SongEntry.COLUMN_NAME_FILEPATH + " = \"" + song.getFilePath() + "\"", null);
                    continue;
                }

                if (!songMap.containsKey(song.getFilePath()))
                {
                    songMap.put(song.getFilePath(), song);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally
        {
            cursor.close();
        }
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
        Date modifiedDate = new Date(file.lastModified());

        if (songMap.containsKey(filePath) && modifiedDate.equals(songMap.get(filePath).getLastModifiedDate()))
        {
            return;
        }

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

        SongMetadata song = new SongMetadata();
        song.setLastModifiedDate(modifiedDate);
        song.setDuration(duration);
        song.setGenre(genre);
        song.setYear(year);
        song.setAlbum(album);
        song.setAlbumArtist(albumArtist);
        song.setArtist(artist);
        song.setTitle(title);
        song.setFilePath(filePath);

        if (!songMap.containsKey(filePath))
        {
            db.insert(DatabaseReaderContract.SongEntry.TABLE_NAME, null, values);
            songMap.put(filePath, song);
        }
        else if (songMap.containsKey(filePath))
        {
            db.update(DatabaseReaderContract.SongEntry.TABLE_NAME, values, null, null);
            songMap.remove(filePath);
            songMap.put(filePath, song);
        }
    }

    @Override
    protected Integer doInBackground(DatabaseReaderHelper... databaseReaderHelpers) {
        UpdateMetadata();

        return songMap.size();
    }

    @Override
    protected void onPostExecute(Integer result) {
        delegate.processFinish(result);
    }
}
