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

/**
 * Created by linedeer on 10/7/2016.
 */

public class catchMain {

    public static ArrayList getCatch(Context context, String Id) {
        ArrayList<String[]> ar = null;
        File infoFl = new File(context.getCacheDir() +"\\" + Id);
        Log.i("My","!infoFl.exists()  : " +  infoFl.exists());
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
                int arLen = Din.readInt();
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
                //Log.i("My","Length  : " + Din.readInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  ar;
    }

    public static void saveCatch(Context context, String Id , ArrayList<String[]> data) {
        File infoFl = new File(context.getCacheDir() +"\\" + Id);
        if(!infoFl.exists()){
            try {
              infoFl.createNewFile();
            } catch (IOException e) {
            }
        }
        ByteArrayOutputStream Bout = new ByteArrayOutputStream();
        DataOutputStream Dout = new DataOutputStream(Bout);
        try {
            Dout.writeInt(data.size());
            for(int i = 0;i < data.size();i++){
                String[] ar = data.get(i);
                Dout.writeInt(ar.length);
                for(int j = 0;j < ar.length;j++){
                    Dout.writeUTF(ar[j]);
                }
            }
            FileOutputStream fos = new FileOutputStream(infoFl);
            fos.write(Bout.toByteArray());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
