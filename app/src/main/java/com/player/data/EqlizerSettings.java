package com.player.data;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class EqlizerSettings {

    public int BASS = 0;
    public int VOICE = 0;
    public int TRABLE = 0;
    public int VIRCHULIZER = 0;
    public int LOUDNESS = 0;
    public int EQ_PRESETS = 4;

    public int BAND_01 = 0;
    public int BAND_02 = 0;
    public int BAND_03 = 0;
    public int BAND_04 = 0;
    public int BAND_05 = 0;

    catchBase data;
    public boolean isOn = true;

    public EqlizerSettings(Context baseContext) {
        data = new catchBase(baseContext,"EqlizerSettings","1"){
            @Override
            public void onRead(DataInputStream din) throws IOException {
                //super.onRead(din);
                BASS = din.readInt();
                VOICE = din.readInt();
                VIRCHULIZER = din.readInt();
                LOUDNESS = din.readInt();
                TRABLE = din.readInt();
                EQ_PRESETS = din.readInt();
                isOn = din.readBoolean();

                BAND_01 = din.readInt();
                BAND_02 = din.readInt();
                BAND_03 = din.readInt();
                BAND_04 = din.readInt();
                BAND_05 = din.readInt();

            }

            @Override
            public void onWrite(DataOutputStream dout) throws IOException {
                //super.onWrite(dout);
                dout.writeInt(BASS);
                dout.writeInt(VOICE);
                dout.writeInt(VIRCHULIZER);
                dout.writeInt(LOUDNESS);
                dout.writeInt(TRABLE);
                dout.writeInt(EQ_PRESETS);
                dout.writeBoolean(isOn);

                dout.writeInt(BAND_01);
                dout.writeInt(BAND_02);
                dout.writeInt(BAND_03);
                dout.writeInt(BAND_04);
                dout.writeInt(BAND_05);

                Log.i("My","Saved...!" + BAND_01 + " " + BAND_02 + " " + BAND_03 + " " + BAND_04 + " " + BAND_05);
            }
        };
        data.readCatch();
    }

    public void save(){
        data.saveCatch();
    }

    public void resetEQs() {
        BASS = 0;
        VOICE = 0;
        TRABLE = 0;
        VIRCHULIZER = 0;
        LOUDNESS = 0;
        EQ_PRESETS = 4;

        BAND_01 = 0;
        BAND_02 = 0;
        BAND_03 = 0;
        BAND_04 = 0;
        BAND_05 = 0;

        isOn = true;
    }
}
