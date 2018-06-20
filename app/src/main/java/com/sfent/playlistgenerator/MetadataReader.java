package com.sfent.playlistgenerator;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class MetadataReader
{
    private String[] directories = new String[] { "Music" };
    private String mediaPath = "";
    private MediaMetadataRetriever mRetriever;

    public MetadataReader()
    {
        mediaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_MUSIC;
        mRetriever = new MediaMetadataRetriever();
    }

    public void UpdateMetadata()
    {
        GetMetadataFromFiles(mediaPath);
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

        mRetriever.setDataSource(filePath);
        String title = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        System.out.println(title);
    }
}
