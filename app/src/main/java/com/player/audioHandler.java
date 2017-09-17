package com.player;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;


public class audioHandler {

    public static Bitmap getAlubumArtBitmapById(ContentResolver resolver,int AID){
        Bitmap IMG = null;
        Cursor Cr = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Media.ALBUM_ID} , MediaStore.Audio.Media._ID + "=?" ,new String[]{AID+""}, null);
        if(Cr != null && Cr.getCount() != 0){
            Cr.moveToNext();
            String albumnId = Cr.getString(0);
            Cr.close();
            Cr = null;
            Cr = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Albums.ALBUM_ART} , MediaStore.Audio.Albums._ID + "=?" ,new String[]{albumnId}, null);
            if(Cr != null && Cr.getCount() != 0){
                Cr.moveToNext();
                try {
                    String path = Cr.getString(0);
                    if(path != null && !path.equals("")){
                        IMG =  BitmapFactory.decodeFile(Cr.getString(0));
                    }else{
                        IMG = null;
                    }
                } catch (Exception e) {
                    IMG = null;
                }
                Cr.close();
            }else{
                Cr.close();
            }
        }
        return IMG;
    }

    public static String getAlubumArtPAthBitmapById(ContentResolver resolver,int AID){
        String IMG = null;
        Cursor Cr = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Media.ALBUM_ID} , MediaStore.Audio.Media._ID + "=?" ,new String[]{AID+""}, null);
        if(Cr != null && Cr.getCount() != 0){
            Cr.moveToNext();
            String albumnId = Cr.getString(0);
            Cr.close();
            Cr = null;
            Cr = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Albums.ALBUM_ART} , MediaStore.Audio.Albums._ID + "=?" ,new String[]{albumnId}, null);
            if(Cr != null && Cr.getCount() != 0){
                Cr.moveToNext();
                try {
                    IMG =   Cr.getString(0) ;
                } catch (Exception e) {
                    IMG = null;
                }
                Cr.close();
            }else{
                Cr.close();
            }
        }else{
            Cr.close();
        }
        return IMG;
    }

    public static String[] getAudioTrackDetailById(ContentResolver resolver, int AID) {
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DISPLAY_NAME
        };
        String[] audioPath = null;
        if(AID != -1){
            Cursor DataCursor = resolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                    MediaStore.Audio.Media._ID + " = " + AID, null, null);
           // Log.i("My","DataCursor : " + DataCursor.getCount());
            if(DataCursor.getCount() != 0){
                DataCursor.moveToNext();
                audioPath = new String[]{DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),DataCursor.getString(3),DataCursor.getString(4)};
                DataCursor.close();
            }
        }
        return audioPath;
    }

}
