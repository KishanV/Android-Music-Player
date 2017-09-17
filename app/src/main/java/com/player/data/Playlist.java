package com.player.data;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.linedeer.player.musicPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Playlist {

    public ArrayList<String[]> songs;
    public String listName = "START UP";
    public int id = -1;
    public boolean shuffle = false;
    public boolean reapeat = true;


    public catchBase catchDATA;

    public Playlist(){
        catchDATA = new catchBase(musicPlayer.THIS.getBaseContext(),"PLAYINGNOW","2"){
            @Override
            public void onRead(DataInputStream din) throws IOException {
                listName = din.readUTF();

                id = din.readInt();
                songs = readArrayList(din);
                shuffle = din.readBoolean();
                reapeat =  din.readBoolean();
            }

            @Override
            public void onWrite(DataOutputStream dout) throws IOException {
                if(listName == null || listName.length() == 0){
                    listName = "Error(0)";
                }
                dout.writeUTF(listName);
                dout.writeInt(id);
                writeArrayList(dout,songs);
                dout.writeBoolean(shuffle);
                dout.writeBoolean(reapeat);
            }
        };
        catchDATA.readCatch();

        if(songs == null){
            songs = getAllSongAid();
            catchDATA.saveCatch();
        }


    }


    public  ArrayList<String[]> getAllSongAid(){
        ArrayList<String[]> data = new ArrayList<String[]>();
        String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?";
        String[] searchPram = new String[]{"1"};
        String[] cols = new String[] {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA};
        Cursor cursor = musicPlayer.THIS.getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString,searchPram, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC");
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            //Log.i("My","Duration : " + cursor.getString(2));
            data.add(new String[]{ cursor.getString(0)+"",cursor.getString(1)+"","0"+cursor.getString(2),""+cursor.getString(3) });
        }
        return data;
    }

    public void save(ArrayList<String[]> list, String playListname) {
        songs = list;
        listName = playListname;
        Log.i("My"," save(listName)" + listName);
        catchDATA.saveCatch();
    }
}
