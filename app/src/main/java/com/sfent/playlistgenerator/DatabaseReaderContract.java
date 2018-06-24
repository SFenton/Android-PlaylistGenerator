package com.sfent.playlistgenerator;
import android.provider.BaseColumns;

public final class DatabaseReaderContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseReaderContract() {}

    /* Inner class that defines the table contents */
    public static class SongEntry implements BaseColumns {
        public static final String TABLE_NAME = "MusicMetadata";
        public static final String COLUMN_NAME_FILEPATH = "Filepath";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_ARTIST = "Artist";
        public static final String COLUMN_NAME_ALBUM_ARTIST = "AlbumArtist";
        public static final String COLUMN_NAME_ALBUM = "Album";
        public static final String COLUMN_NAME_YEAR = "Year";
        public static final String COLUMN_NAME_GENRE = "Genre";
        public static final String COLUMN_NAME_DURATION = "Duration";
        public static final String COLUMN_NAME_LAST_MODIFIED_DATE = "LastModifiedDate";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SongEntry.TABLE_NAME + " (" +
                    SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SongEntry.COLUMN_NAME_FILEPATH + " TEXT," +
                    SongEntry.COLUMN_NAME_TITLE + " TEXT," +
                    SongEntry.COLUMN_NAME_ARTIST + " TEXT, " +
                    SongEntry.COLUMN_NAME_ALBUM_ARTIST + " TEXT," +
                    SongEntry.COLUMN_NAME_ALBUM + " TEXT," +
                    SongEntry.COLUMN_NAME_YEAR + " INT," +
                    SongEntry.COLUMN_NAME_GENRE + " TEXT," +
                    SongEntry.COLUMN_NAME_DURATION + " INT," +
                    SongEntry.COLUMN_NAME_LAST_MODIFIED_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SongEntry.TABLE_NAME;
}
