package com.player.data;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class catchBase {

    private String name;
    String virson;
    Context context;


    public catchBase(Context context, String name,String virson){
        this.name = name;
        this.context = context;
        this.virson = virson;
    }

    public void readCatch() {
        File infoFl = new File(context.getCacheDir() +"\\" + name);
        //Log.i("My","!infoFl.exists()  : " +  infoFl.exists());
        if(!infoFl.exists()){
            try {
              infoFl.createNewFile();
            } catch (IOException e) {
            }
        }
        byte[] data = new byte[(int) infoFl.length()];
        FileInputStream fos = null;
        try {
            fos = new FileInputStream(infoFl);
            fos.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fos != null){
            ByteArrayInputStream Bin = new ByteArrayInputStream(data);
            DataInputStream Din = new DataInputStream(Bin);
            try {
                String val = Din.readUTF();
                if(!val.equals(virson)){
                    onFail(0);
                }else{
                    onRead(Din);
                }
            } catch (IOException e) {

            }
        }else{
            onFail(-1);
        }
    }

    public void onRead(DataInputStream din) throws IOException { }
    public void onFail(int v){ }
    public  void onWrite(DataOutputStream dout) throws IOException { }

    public ArrayList<String[]> readArrayList(DataInputStream Din) throws IOException {
        int arLen = Din.readInt();
        ArrayList<String[]> ar = null;
        if(arLen > 0){
            ar = new ArrayList<String[]>();
        }
        for(int i = 0;i < arLen;i++){
            int sLen = Din.readInt();
            String[] sr = new String[sLen];
            for(int j = 0;j < sLen;j++) {
                sr[j] = Din.readUTF();
            }
            ar.add(sr);
        }
        return  ar;
    }

    public void writeArrayList(DataOutputStream Dout,  ArrayList<String[]> data) throws IOException {
        if(data != null){
            Dout.writeInt(data.size());
            for(int i = 0;i < data.size();i++){
                String[] ar = data.get(i);
                Dout.writeInt(ar.length);
                //Log.i("My"," ok " + ar[0]);
                for(int j = 0;j < ar.length;j++){
                    Dout.writeUTF(ar[j]);
                }
            }
        }else{
            Dout.writeInt(0);
        }
    }


    public void saveCatch() {
        File infoFl = new File(context.getCacheDir() +"\\" + name);
        if(!infoFl.exists()){
            try {
                infoFl.createNewFile();
            } catch (IOException e) {
            }
        }
        ByteArrayOutputStream Bout = new ByteArrayOutputStream();
        DataOutputStream Dout = new DataOutputStream(Bout);
        try {
            Dout.writeUTF(virson);
            onWrite(Dout);
            FileOutputStream fos = new FileOutputStream(infoFl);
            fos.write(Bout.toByteArray());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
