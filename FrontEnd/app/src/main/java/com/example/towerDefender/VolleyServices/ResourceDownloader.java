package com.example.towerDefender.VolleyServices;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class ResourceDownloader {

    private String name;
    private String url;
    private Bitmap image;
    private long downloadID;

    public ResourceDownloader(String _name, String _url){
        name = _name;
        url = _url;
    }

    public void downloadResource(Context context, ResourceDownloader resource, String extension){
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(resource.url);

        DownloadManager.Request request=new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setTitle("Resource File")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + "test" + File.separator + resource.name + extension)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        downloadManager.enqueue(request);
    }
}

