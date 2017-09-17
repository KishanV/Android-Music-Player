package Views.Home.QuickEq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.player.data.catchBase;
import com.shape.home.QEq.itemBack;
import com.shape.home.QEq.itemDivider;
import com.shape.home.QEq.selectedIcon;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Views.api.FMlyt;
import Views.api.StringUtil;
import Views.radiusSqure;
import Views.textImg;
import utils.EqualizerUtil;

public class presetAdapter extends BaseAdapter {

    public ArrayList<String[]> data = new ArrayList<String[]>();
    public ArrayList<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;

    catchBase catchData;
    ArrayList<String[]> customPreset = new ArrayList<String[]>();
    public  String custom = "0 0 0 0 0";
    public presetAdapter(){

        catchData = new catchBase(Ui.ef.getBaseContext(),"EQpreset","3"){
            @Override
            public void onRead(DataInputStream din) throws IOException {
                custom = din.readUTF();
                customPreset = readArrayList(din);
                if(customPreset == null){
                    customPreset = new ArrayList<String[]>();
                }
            }
        };
        catchData.readCatch();

        if(data == null){
            old = data;
            data = new ArrayList<String[]>();
        }else{
            old = data;
        }

        if(data == null || data.size() == 0){
            for (int i = 0; i < EqualizerUtil.NUMBER_OF_PRESETS; i++) {
                data.add(new String[]{EqualizerUtil.PRESETS[i].name});
            }
            data.add(new String[]{"Custom",custom});

            for (int i = 0; i < customPreset.size(); i++) {
                data.add(customPreset.get(i));
            }
        }else{
            old = data  ;
        }
        selected = new boolean[data.size()];
        //Log.i("My","Song Count : " + cursor.getCount());

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item vm;
        if(convertView == null){
            vm = new Item(parent.getContext(),position);
        }else{
            vm = (Item) convertView;
            vm.setData(position);
        }
        return vm;
    }

    public void  onSelect(){
        seclection = true;
    }

    public void  onReload(){

    }



    boolean searching = false;
    public void searchData(String strStr){
        strStr = (""+strStr).toUpperCase();
        searching = true;
        data = new ArrayList<String[]>();
        index = new HashMap<String, Integer>();
        int length  = old.size();
        for (int i = 0; i < length; i++) {
            String str = old.get(i)[0];
            if(str.toUpperCase().contains(strStr)){
                str = StringUtil.getFirstChar(str).toUpperCase();
                if(!str.equals("") && !index.containsKey(str)){
                    data.add(old.get(i));
                    index.put(str,i);
                }else{
                    data.add(old.get(i));
                }
            }
        }
    }

    public void closeSearch(){
        searching = false;
        data = old;
        index = oldIndex;
    }

    boolean seclection = false;
    public int  getSelected(){
        int val = 0;
        for(int i = 0;i < selected.length;i++){
            if(selected[i]){
                val++;
            }
        }
        return val;
    }

    public void removeSelection(){
        seclection = false;
        int val = 0;
        for(int i = 0;i < selected.length;i++){
            selected[i] = false;
        }
    }

    public  int getHeigtht(){
        return Main.This.width;
    }

    public class Item extends FMlyt {

        public radiusSqure rSqure;
        textImg nameText;
        textImg noText;
        int position;

        public void setData(int position) {
            this.position = position;

            noText.setSize(0,0);
            noText.setText((position + 1)+".");

            nameText.setSize(0,0);
            nameText.setText(data.get(position)[0]);
            nameText.InCenter(width,height - Ui.cd.getHt(2));
            noText.setY((int) nameText.y);
            nameText.setX((int) (noText.width + Ui.cd.getHt(5) + noText.getX()));

            if(width - Ui.cd.getHt(120) < nameText.width){
                nameText.setSize((int) (width - Ui.cd.getHt(100)), (int) nameText.height);
                nameText.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                nameText.setColor(0x66FFFFFF);
            }

            if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler.EQs.EQ_PRESETS == position){
                selectico.setDrawing(true);
            }else if(Ui.ef.MusicPlayer  == null && 0 == position){
                selectico.setDrawing(true);
            }else{
                selectico.setDrawing(false);
            }
            invalidate();
        }

        Bitmap bm;
        Canvas cn;

        public Item(final Context context, final int Id) {
            super(context,getHeigtht(), Ui.cd.getHt(44));
            setLayoutParams(new ListView.LayoutParams(width, Ui.cd.getHt(44)));
            setRipple(true,0.3f);
            setRippleDown(false);
            this.position = Id;

            setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(selected[position]){
                        selected[position] = false;
                    }else{
                        selected[position] = true;
                    }
                    Item.this.invalidate();
                    onSelect();

                    return true;
                }
            });

            rSqure = new radiusSqure(width,height - Ui.cd.getHt(2),0,0,0);
            rSqure.setColor(itemBack.Color0);
            addShape(rSqure);

            noText = textImg.getText((position + 1)+".", Ui.cd.getHtF(14));
            noText.setX(Ui.cd.getHt(10));
            noText.setY(Ui.cd.getHt(13));
            noText.setColor(0x33FFFFFF);
            addShape(noText);

            nameText = textImg.getText(data.get(position)[0], Ui.cd.getHtF(18));
            addShape(nameText);
            setBackgroundColor(itemDivider.Color0);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ui.ef.clickPlay();
                    //&& Ui.ef.MusicPlayer.handler.EQs.EQ_PRESETS != position
                    if(Ui.ef.MusicPlayer != null){
                        if(data.get(position).length == 2){
                            Ui.ef.MusicPlayer.handler.setPreset(position,data.get(position)[1]);
                        }else{
                            Ui.ef.MusicPlayer.handler.setPreset(position);
                        }
                        Ui.ef.MusicPlayer.handler.EQs.save();
                    }
                    onReload();
                }
            });

            selectico = new selectedIcon((int)height,(int)height,0,0);
            selectico.setX(width - height);
            selectico.setDrawing(false);
            addShape(selectico);

            setData(Id);
        }
        selectedIcon selectico;

        @Override
        protected void onDraw(Canvas canvas) {
            super.drawShape(canvas);
            super.postDraw(canvas);
            super.afterDraw(canvas,rSqure.S0);

        }
    }

}
