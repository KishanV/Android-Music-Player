package Views.Library.Menu.SongsClass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemMenu;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.allsong.itemStick;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Views.ContentHome;
import Views.Popups.songsBtns;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.StringUtil;
import Views.api.shapeImg;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class songAdapter extends BaseAdapter {

    public ArrayList<String[]> data = new ArrayList<String[]>();
    public ArrayList<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;


    public  songAdapter(){

        if(data == null || data.size() == 0){
            String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?";
            String[] searchPram = new String[]{"1"};
            String[] cols = new String[] {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST};
            Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString,searchPram, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC");

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String str = cursor.getString(0);
                str = StringUtil.getFirstChar(str).toUpperCase();
                if(!str.equals("") && !index.containsKey(str)){
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),str});
                    index.put(str,i);
                }else{
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),""});
                }
            }
            cursor.close();
        }else{
            old = data  ;
            for (int i = 0; i < data.size(); i++) {
                String str = data.get(i)[3];
                if(str.length() != 0){
                    index.put(str,i);
                }
            } Log.i("My","Data on read;");
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

    public class Item extends FMlyt{


        public squre leftSq;
        public radiusSqure rSqure;
        public radiusSqure rtop;
        //FMText no;
        //FMText listName;
        textImg nameText;
        textImg noText;
        textImg artistText;
        FMText artist;
        public String Schar;

        textImg txt;
        shapeImg stick;
        int position;

        public void setData(int position) {
            this.position = position;

            noText.setSize(0,0);
            noText.setText((position+1)+".");

           /* listName.setText(data.get(position)[0]);
            listName.setX(no.width + Ui.cd.getHt(5) + no.getX());
            if(width - Ui.cd.getHt(120) < listName.width){
                listName.setSize((int) (width - Ui.cd.getHt(100)),listName.height);
                listName.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                listName.img.setColor(0x66FFFFFF);
            }*/
            nameText.setSize(0,0);
            nameText.setX((int) (noText.width + Ui.cd.getHt(5) + noText.getX()));
            nameText.setText(data.get(position)[0]);
            if(width - Ui.cd.getHt(120) < nameText.width){
                nameText.setSize((int) (width - Ui.cd.getHt(100)), (int) nameText.height);
                nameText.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                nameText.setColor(0x66FFFFFF);
            }

            artistText.setSize(0,0);
            artistText.setX((int) nameText.getX());
            artistText.setText(data.get(position)[2]);
            if(width - Ui.cd.getHt(120) < artistText.width){
                artistText.setSize((int) (width - Ui.cd.getHt(100)), (int) artistText.height);
                artistText.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
            }else{
                artistText.setColor(0x33FFFFFF);
            }


            if(data.get(position)[3].length() != 0){
                stick.setDrawing(true);
                txt.setDrawing(true);
                txt.setText(data.get(position)[3],true);
            }else{
                stick.setDrawing(false);
                txt.setDrawing(false);
            }
            //Schar = data.get(position)[0].substring(0,1).toUpperCase();
            Schar = StringUtil.getFirstChar(data.get(position)[0]).toUpperCase();
            if(selected[position]){
                rtop.setDrawing(true);
            }else{
                rtop.setDrawing(false);
            }

            invalidate();
        }

        Bitmap bm;
        Canvas cn;

        public Item(final Context context, final int Id) {
            super(context, Ui.cd.DPW - Ui.cd.getHt(14), Ui.cd.getHt(60));
            setLayoutParams(new ListView.LayoutParams(width, Ui.cd.getHt(60)));
            setRipple(true,0.3f);
            setRippleDown(false);
            this.position = Id;

            //bm = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            //cn = new Canvas(bm);

            setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                        if(selected[position]){
                            selected[position] = false;
                            rtop.setDrawing(false);
                        }else{
                            selected[position] = true;
                            rtop.setDrawing(true);
                        }

                        Item.this.invalidate();
                        onSelect();

                    return true;
                }
            });

           /* ShapeView stick = itemStick.getFMview(context,false);
            stick.setInCenter(Ui.cd.getHt(16),height);
            //addView(stick);*/

            rSqure = new radiusSqure(width - Ui.cd.getHt(30),height - Ui.cd.getHt(2), Ui.cd.getHt(30),0,0);
            rSqure.setColor(itemRect.Color0);
            addShape(rSqure);

            rtop = new radiusSqure(width - Ui.cd.getHt(30+4),height - Ui.cd.getHt(2+4), Ui.cd.getHt(30+2), Ui.cd.getHt(2),0);
            rtop.setColor(0x99000000);
            rtop.setDrawing(false);
            addShape(rtop);

            leftSq = new squre(Ui.cd.getHt(30), Ui.cd.getHt(60),0,0);
            leftSq.setColor(0x33000000);
            addShape(leftSq);

            final ShapeView menu = itemMenu.getFMview(context,true);
            menu.setRipple(true,0.3f);
            menu.setRippleDown(false);
            menu.setX(width - menu.width);

            menu.onClick(new call(){
                @Override
                public void onCall(boolean bl) {
                    final songsBtns mn = new songsBtns(context, Ui.cd.DPW, Ui.cd.DPH){
                        @Override
                        public void onSelect(String id) {
                            Ui.bk.back();
                            int Id = Integer.parseInt(id);
                            if(Id != -1){
                                playlistHandler.addTOPlaylistByID(Ui.ef.getContentResolver(),Id,new int[]{Integer.parseInt(data.get(position)[1])});
                            }
                            if(Id == Ui.ef.MusicPlayer.handler.playlist.id){
                                Ui.ef.MusicPlayer.handler.addSongs(new int[]{Integer.parseInt(data.get(position)[1])});
                            }
                        }

                        @Override
                        public void onBtn(String name) {
                            super.onBtn(name);
                            if(name.equals("PLAY ALL")){
                                Ui.ef.MusicPlayer.handler.playALlSong(Item.this.position);
                                Ui.bk.back();
                            }else if(name.equals("ADD NEXT")){
                                Ui.ef.MusicPlayer.handler.addSongsNext(new int[]{Integer.parseInt(data.get(position)[1])});
                                Ui.bk.back();
                            }else if(name.equals("SHOW FILE")){
                                Ui.bk.back();
                                Ui.bk.add(new call(){
                                    @Override
                                    public void onCall(boolean bl) {
                                        ContentHome.This.MenuHome.goingBack();
                                        ContentHome.This.MenuHome.openPage(1);
                                    }
                                });
                                String path = getAudiopath(Integer.parseInt(data.get(position)[1]));
                                ContentHome.This.MenuHome.openFolder(path);
                            }else if(name.equals("SHARE")){
                                Ui.bk.back();
                                String myFilePath = getAudiopath(Integer.parseInt(data.get(position)[1]));
                                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                                File fileWithinMyDir = new File(myFilePath);

                                if(fileWithinMyDir.exists()) {
                                    intentShareFile.setType("application/pdf");
                                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+myFilePath));
                                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
                                    intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
                                    Ui.ef.startActivity(Intent.createChooser(intentShareFile, "Share File"));
                                }
                            }
                        }
                    };
                    ContentHome.This.MenuHome.drawCatch();
                    ContentHome.This.addPopup(mn);
                    mn.setClickable(true);
                    Ui.bk.add(new call() {
                        @Override
                        public void onCall(boolean bl) {
                            ContentHome.This.removePopup(mn);
                            ContentHome.This.MenuHome.removeCatch();
                        }
                    });
                }
            });
            addView(menu);

            noText = textImg.getText(position+".", Ui.cd.getHtF(14));
            noText.setX(Ui.cd.getHt(8+30));
            noText.setY(Ui.cd.getHt(13));
            noText.setColor(0x33FFFFFF);
            addShape(noText);

            nameText = textImg.getText(data.get(position)[0], Ui.cd.getHtF(18));
            nameText.setX((int) (noText.width + Ui.cd.getHt(5) + noText.getX()));
            nameText.setY(Ui.cd.getHt(12));
            if(width - Ui.cd.getHt(120) < nameText.width){
                nameText.setSize((int) (width - Ui.cd.getHt(100)), (int) nameText.height);
                nameText.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                nameText.setColor(0x66FFFFFF);
            }
            addShape(nameText);

            artistText = textImg.getText(data.get(position)[2], Ui.cd.getHtF(14));
            artistText.setX((int) nameText.getX());
            artistText.setY(Ui.cd.getHt(12 + 22));
            if(width - Ui.cd.getHt(120) < artistText.width){
                artistText.setSize((int) (width - Ui.cd.getHt(100)), (int) artistText.height);
                artistText.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
            }else{
                artistText.setColor(0x33FFFFFF);
            }
            addShape(artistText);


                setBackgroundColor(itemBack.Color0);



            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Ui.ef.clickPlay();
                   //Ui.ef.MusicPlayer.handler.playByPlaylistName("ok");
                    if(seclection){
                        if(selected[position]){
                            selected[position] = false;
                            rtop.setDrawing(false);
                        }else{
                            rtop.setDrawing(true);
                            selected[position] = true;
                        }
                        Item.this.invalidate();
                        onSelect();
                    }else{
                        Ui.ef.MusicPlayer.handler.playALlSong(Item.this.position);
                    }
                }
            });

            //if(data.get(position)[3] != null){
                stick = itemStick.getShape();
                stick.setX(Ui.cd.getHt(0));
                stick.setY(Ui.cd.getHt(0));
                addShape(stick);

                txt = textImg.getText("", Ui.cd.getHt(18));
                txt.setSize((int)stick.width,(int)stick.width);
                txt.setColor(0x88FFFFFF);
                txt.setText(data.get(position)[3],true);
                addShape(txt);
            //}
            if(data.get(position)[3].length() != 0){
                stick.setDrawing(true);
                txt.setDrawing(true);
                txt.setText(data.get(position)[3],true);
            }else{
                stick.setDrawing(false);
                txt.setDrawing(false);
            }
            //Schar = data.get(position)[0].substring(0,1).toUpperCase();
            Schar = StringUtil.getFirstChar(data.get(position)[0]).toUpperCase();
        }

        @Override
        protected void onDraw(Canvas canvas) {
           /* if(selected[position]){
                rSqure.draw(canvas);
                rtop.draw(canvas);
            }else{
                rSqure.draw(canvas);
            }*/
            //canvas.saveLayer(0,0, width, height, rSqure.P0);


            //cn.drawColor(itemBack.Color0);
            //super.drawShape(cn);
            //canvas.drawBitmap(bm,0,0,null);
            super.drawShape(canvas);
            super.postDraw(canvas);
            super.afterDraw(canvas,rSqure.S0);

        }

        public void setChar(boolean aChar) {
            if(txt != null){
                if(!aChar){
                    stick.setDrawing(false);
                    txt.setDrawing(false);
                }else{
                    stick.setDrawing(true);
                    txt.setDrawing(true);
                }
            }
            invalidate(0,0,(int)stick.width,(int)stick.height);
        }


    }

    public String getAudiopath(int Id) {
        String[] projection = { MediaStore.Audio.Media.DATA };
        Cursor DataCursor = Ui.ef.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Audio.Media._ID + " = " + Id, null, null);

        DataCursor.moveToNext();
        String audioPath = DataCursor.getString(0);
        DataCursor.close();
        return audioPath;
    }

}
