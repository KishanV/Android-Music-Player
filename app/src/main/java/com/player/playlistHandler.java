package com.player;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.player.data.Playlist;

import java.io.File;
import java.util.ArrayList;

public class playlistHandler {


    public static ArrayList<String[]> getALlPlalists(ContentResolver Cr){
        ArrayList<String[]> list = new ArrayList<String[]>();
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] cols = new String[] {MediaStore.Audio.Playlists.NAME, MediaStore.Audio.Playlists._ID};
        Cursor DataCursor =  Cr.query( uri,cols, null,null, MediaStore.Audio.Playlists.NAME +" COLLATE NOCASE ASC");// +" COLLATE NOCASE ASC");

        int length = DataCursor.getCount();
        for (int i = 0; i < length; i++) {
            DataCursor.moveToNext();
            String str = DataCursor.getString(0);
            if(!str.startsWith(" ") && str.length() != 0){
                list.add(new String[] {str,DataCursor.getInt(1)+""});
            }
        }
        DataCursor.close();
        return list;
    }

    public static void addTOPlaylistByID(ContentResolver Cn,int plalistId,int[] AIDs){
        String[] cols = new String[] { "count(*)" };
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", plalistId);
        Cursor cur = Cn.query(uri, cols, null, null, null);
        final int base;
        cur.moveToNext();
        base = cur.getInt(0);
        cur.close();


        for(int i = 0;i < AIDs.length;i++){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID,AIDs[i]);
            values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf( base+AIDs[i]));
            Cn.insert(uri, values);
        }
    }

    public static void addTOPlaylistByID(ContentResolver Cn,int plalistId,Integer[] AIDs){
        String[] cols = new String[] { "count(*)" };
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", plalistId);
        Cursor cur = Cn.query(uri, cols, null, null, null);
        final int base;
        cur.moveToNext();
        base = cur.getInt(0);
        cur.close();


        for(int i = 0;i < AIDs.length;i++){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID,AIDs[i]);
            values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf( base+AIDs[i]));
            Cn.insert(uri, values);
        }

    }

    public static int getPlaylistIdByName(ContentResolver Cr, String Name) {

        int Id = -1;
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] cols = new String[] { MediaStore.Audio.Playlists._ID};
        Cursor DataCursor =  Cr.query( uri,cols, MediaStore.Audio.Playlists.NAME + " = '" + Name +"'",null, MediaStore.Audio.Playlists.NAME +" COLLATE NOCASE ASC");

        int length = DataCursor.getCount();
        for (int i = 0; i < length; i++) {
            DataCursor.moveToNext();
            Id = DataCursor.getInt(0);
        }
        DataCursor.close();
        return Id;

    }

    public static String getPlaylistNameById(ContentResolver Cr, int ID) {

        String Name = null;
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] cols = new String[] { MediaStore.Audio.Playlists.NAME};
        Cursor DataCursor =  Cr.query( uri,cols, MediaStore.Audio.Playlists._ID + " = " + ID ,null, MediaStore.Audio.Playlists.NAME +" COLLATE NOCASE ASC");

        int length = DataCursor.getCount();
        for (int i = 0; i < length; i++) {
            DataCursor.moveToNext();
            Name = DataCursor.getString(0);
        }
        DataCursor.close();
        return Name;

    }

    public static ArrayList getPlaylistByIds(ContentResolver Cr, int Id) {
        ArrayList list = null;
        String[] projection = {MediaStore.Audio.Playlists.Members.TITLE,MediaStore.Audio.Playlists.Members.AUDIO_ID,MediaStore.Audio.Playlists.Members.DURATION};
        Cursor DataCursor = Cr.query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", Id), projection,
                MediaStore.Audio.Playlists.Members.IS_MUSIC + " != 0 ", null,  MediaStore.Audio.Playlists.Members.PLAY_ORDER +" ASC");

        int length = DataCursor.getCount();

        if(length != 0){
            list = new ArrayList<String[]>();
        }
        for (int i = 0; i < length; i++) {
            DataCursor.moveToNext();
            list.add(new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
        }
        DataCursor.close();
        return list;
    }

    public static int getPlaylistLength(ContentResolver Cr, int Id) {
        ArrayList list = null;
        String[] projection = {"count(*)"};
        Cursor DataCursor = Cr.query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", Id), projection,
                MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null, null);

        int length = DataCursor.getCount();
        if(length > 0){
            DataCursor.moveToNext();
            length = DataCursor.getInt(0);
        }
        return length;
    }

    public static ArrayList getPlaylistByNames(ContentResolver Cr,int Id) {
        ArrayList list = null;
        String[] projection = {MediaStore.Audio.Playlists.Members.DISPLAY_NAME};
        Cursor DataCursor = Cr.query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", Id), projection,
                MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null, null);

        int length = DataCursor.getCount();

        if(length != 0){
            list = new ArrayList<String>();
        }
        for (int i = 0; i < length; i++) {
            DataCursor.moveToNext();
            list.add(DataCursor.getString(0));
        }
        DataCursor.close();
        return list;
    }

    public static Uri addNewPlaylist(ContentResolver Cr, String str){
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, str);
        values.put(MediaStore.Audio.Playlists.DATE_ADDED,
                System.currentTimeMillis());
        values.put(MediaStore.Audio.Playlists.DATE_MODIFIED,
                System.currentTimeMillis());

        Uri Got = Cr.insert(uri, values);
        return  Got;
    }

    public static void updateName(ContentResolver Cr, String str, String name) {
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, str);

        int Got = Cr.update(uri, values,MediaStore.Audio.Playlists.NAME + " = ?" ,new String[]{name});
    }
}