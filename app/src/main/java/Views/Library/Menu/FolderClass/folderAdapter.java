package Views.Library.Menu.FolderClass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.Icon.fileIcon;
import com.shape.Library.Icon.folderIcon;
import com.shape.Library.Icon.folderbackIcon;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemMenu;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.allsong.itemStick;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import Views.ContentHome;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.StringUtil;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class folderAdapter extends BaseAdapter {

    public List<String[]> allsong = new ArrayList<String[]>();
    public List<String[]> data = new ArrayList<String[]>();
    public List<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;

    public String crtPath = "/";
    public String crtFolder = "/";
    public folderAdapter(){
        String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?";
        String[] searchPram = new String[]{"1"};
        String[] cols = new String[] {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE};
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString,searchPram, MediaStore.Audio.Media.DATA +" COLLATE NOCASE ASC");
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String path = cursor.getString(0);
            //String str = path.substring(0,1).toUpperCase();
            String str = StringUtil.getFirstChar(path).toUpperCase();
                if(!str.equals("") && !index.containsKey(str)){
                    allsong.add(new String[]{path,cursor.getString(1),cursor.getString(2),str});
                    index.put(str,i);
                }else{
                    allsong.add(new String[]{path,cursor.getString(1),cursor.getString(2),null});
                }
        }
        reloadFolder();
    }

    public void onReload(){

    }

    public List<String[]> songs;
    public HashMap<String, Integer> folder;

    public void reloadFolder(){
        songs = new ArrayList<String[]>();
        folder = new HashMap<String, Integer>();
        data = new ArrayList<String[]>();
        index.clear();
       // Log.i("My","crtPath : " + crtPath);
        for (int i = 0; i < allsong.size(); i++) {
            String path = allsong.get(i)[0];
            if(path.contains(crtPath)){
                //Log.i("My","OrgPath : " + path);
                int indexOf = path.indexOf("/", crtPath.length()+1);
                //Log.i("My","indexOf : " + indexOf);
                //Log.i("My","indexOf : " + indexOf);
                //Log.i("My","crtPath.length() : " + crtPath.length());

                String isFile = "0";
                if(indexOf == -1){
                    isFile = null;
                    path = path.substring(crtPath.length());
                }else{
                    path = path.substring(crtPath.length(),indexOf);
                }
                //Log.i("My","path : " + path);

                String str = path;
                str = StringUtil.getFirstChar(str).toUpperCase();
                if(!folder.containsKey(path)){
                    folder.put(path,0);
                    String[] itemData = new String[]{path,allsong.get(i)[1],allsong.get(i)[2],null,isFile};
                    data.add(itemData);
                    if(isFile == null){
                        songs.add(itemData);
                    }
                }

            }
        }

        //Log.i("My","OK" + "B".toUpperCase().compareTo("AB".toUpperCase()));

        Collections.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] fruit2, String[] fruit1)
            {
                if(fruit1[4] != null){
                    return  1;
                }
                return -1;
            }
        });

        Collections.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] fruit2, String[] fruit1)
            {
                if(fruit1[4] != null){
                    return  0;
                }
                return fruit2[0].toUpperCase().compareTo(fruit1[0].toUpperCase());
            }
        });


        if(!crtPath.equals("/")){
            String[] folders = crtPath.split("/");
            crtFolder = folders[folders.length-1];
             data.add(0,new String[]{"Go Back ( " + folders[folders.length-1] +" )","Click here to go back.","-",null,"-1"});
        }

        createIndex();

        old = data;
        selected = new boolean[data.size()];
        onReload();
    }

    int[] getSongsAID(){
        int[] AIDs = new int[songs.size()];
        int j = 0;
        for(int i = 0;i < data.size();i++){
            String[] nData = data.get(i);
            if(nData[4] == null){
                AIDs[j] = Integer.parseInt(nData[1]);
                j++;
            }
        }
        return AIDs;
    }

    int[] getFolderAID(String Name){
        List<Integer> songs  = new ArrayList<Integer>();
        String path = crtPath +  Name + "/";
        for(int i = 0;i < allsong.size();i++){
            if(allsong.get(i)[0].contains(path)){
                songs.add(Integer.parseInt(allsong.get(i)[1]));
            }
        }
        int[] AIDs = new int[songs.size()];
        for(int i = 0;i < songs.size();i++){
            AIDs[i] = songs.get(i);
        }
        return AIDs;
    }





    void  getSongsFiles(String Name){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT, "All Songs from this Albums...");
        intent.setType("image/jpeg"); /* This example is sharing jpeg images. */

        ArrayList<Uri> files = new ArrayList<Uri>();
        String path = crtPath +  Name + "/";
        for(int i = 0;i < allsong.size();i++){
            if(allsong.get(i)[0].startsWith(path)){
                File file = new File(allsong.get(i)[0]);
                Uri uri = Uri.fromFile(file);
                files.add(uri);
            }
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        Ui.ef.startActivity(intent);
    }


    void createIndex(){
        if(data.size() != 0){
            data.get(0)[3] = "-";
            index.put("-",0);
        }

        for(int i=0;i < data.size();i++){
            String[] ar = data.get(i);
            //String str = ar[0].substring(0,1).toUpperCase();
            String str = StringUtil.getFirstChar(ar[0]).toUpperCase();
            if(ar[4] == null && !str.equals("") && !index.containsKey(str)){
                ar[3] = str;
                index.put(str,i);
            }
        }
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
        createIndex();
        onReload();
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

    public class Item extends FMlyt {

        public squre btnLine;
        public squre leftSq;
        public radiusSqure rSqure;
        public radiusSqure rtop;
        FMText no;
        FMText name;
        FMText artist;
        public String Schar;

        FMText txt;
        ShapeView stick;
        int position;

        public void setData(int position) {
            this.position = position;
            String[] dataAr = data.get(position);
            no.setText(position+".");

            name.setText(dataAr[0]);
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            if(width - Ui.cd.getHt(170) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(150)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.setColor(0x66FFFFFF);
            }

            if(dataAr[4] == null){
                artist.setText(dataAr[2]);
                artist.setX(name.getX());
                if(width - Ui.cd.getHt(170) < artist.width){
                    artist.setSize((int) (width - Ui.cd.getHt(150)),name.height);
                    artist.img.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
                }else{
                    artist.img.setColor(0x33FFFFFF);
                }
            }else{
                artist.setText(dataAr[1]);
                artist.setX(name.getX());
                if(width - Ui.cd.getHt(170) < artist.width){
                    artist.setSize((int) (width - Ui.cd.getHt(150)),name.height);
                    artist.img.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
                }else{
                    artist.img.setColor(0x33FFFFFF);
                }
            }



            if(dataAr[3] != null){
                stick.setVisibility(VISIBLE);
                txt.setVisibility(VISIBLE);
                txt.setText(dataAr[3]);
                txt.InCenter(stick.width,stick.width);
            }else{
                stick.setVisibility(GONE);
                txt.setVisibility(GONE);
            }

            if(dataAr[4] != null){
                Schar = "-";
            }else{
                //Schar = dataAr[0].substring(0,1).toUpperCase();
                Schar = StringUtil.getFirstChar(dataAr[0]).toUpperCase();
            }

            if(dataAr[4] == null){
                fileicon.setVisibility(VISIBLE);
                foldericon.setVisibility(GONE);
                folderBack.setVisibility(GONE);
            }else if(dataAr[4] != null && dataAr[4].equals("-1")){
                folderBack.setVisibility(VISIBLE);
                fileicon.setVisibility(GONE);
                foldericon.setVisibility(GONE);
            }else{
                folderBack.setVisibility(GONE);
                fileicon.setVisibility(GONE);
                foldericon.setVisibility(VISIBLE);
            }
            invalidate();
        }

        ShapeView foldericon;
        ShapeView folderBack;
        ShapeView fileicon;

        public Item(final Context context, final int Id) {
            super(context, Ui.cd.DPW - Ui.cd.getHt(14), Ui.cd.getHt(60));
            setLayoutParams(new ListView.LayoutParams(width, Ui.cd.getHt(60)));
            setRipple(true,0.3f);
            setRippleDown(false);
            //this.position = Id;
            position = Id;
            /*setOnLongClickListener(new OnLongClickListener() {
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
            });*/

           /* ShapeView stick = itemStick.getFMview(context,false);
            stick.setInCenter(Ui.cd.getHt(16),height);
            //addView(stick);*/

            final ShapeView menu = itemMenu.getFMview(context,true);
            menu.setRipple(true,0.3f);
            menu.setRippleDown(false);
            menu.setX(width - menu.width);

            menu.onClick(new call(){
                @Override
                public void onCall(boolean bl) {
                    if(data.get(position)[4] == null){
                        ContentHome.This.MenuHome.drawCatch();
                        final folderBtns mn = new folderBtns(getContext(), Ui.cd.DPW, Ui.cd.DPH,data.get(position)){
                            @Override
                            public void onBtn(String name) {
                                if(name.equals("PLAY")){
                                    Ui.ef.MusicPlayer.handler.playALlSong((folderAdapter.this.data.size() - songs.size() -1)+(position-1),getSongsAID(),"From Folder : " + crtFolder);
                                    Ui.bk.back();
                                }else if(name.equals("ADD NEXT")){

                                    Ui.ef.MusicPlayer.handler.addSongsNext(new int[]{Integer.parseInt(folderAdapter.this.data.get(position)[1])});
                                    Ui.bk.back();
                                }else if(name.equals("SHARE")){
                                    Ui.bk.back();
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "All Songs from this Albums...");
                                    intent.setType("image/jpeg"); /* This example is sharing jpeg images. */

                                    ArrayList<Uri> files = new ArrayList<Uri>();
                                    for(int i = 0;i < 1;i++){
                                        File file = new File(folderAdapter.this.data.get(position)[0]);
                                        Uri uri = Uri.fromFile(file);
                                        files.add(uri);
                                    }

                                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                                    Ui.ef.startActivity(intent);
                                }
                            }
                        };
                        ContentHome.This.addPopup(mn);
                        mn.setClickable(true);
                        Ui.bk.add(new call() {
                            @Override
                            public void onCall(boolean bl) {
                                ContentHome.This.MenuHome.removeCatch();
                                ContentHome.This.removePopup(mn);
                            }
                        });
                    }else{
                        ContentHome.This.MenuHome.drawCatch();
                        final folderBtns mn = new folderBtns(getContext(), Ui.cd.DPW, Ui.cd.DPH,data.get(position)){
                            @Override
                            public void onBtn(String name) {
                                if(name.equals("PLAY")){
                                    Ui.ef.MusicPlayer.handler.playALlSong(0,getFolderAID(folderAdapter.this.data.get(position)[0]),"From Folder : " + folderAdapter.this.data.get(position)[0]);
                                    Ui.bk.back();
                                }else if(name.equals("ADD NEXT")){
                                    Ui.ef.MusicPlayer.handler.addSongsNext(getFolderAID(folderAdapter.this.data.get(position)[0]));
                                    Ui.bk.back();
                                }else if(name.equals("SHARE")){
                                    Ui.bk.back();
                                    getSongsFiles(folderAdapter.this.data.get(position)[0]);
                                }
                            }
                        };
                        ContentHome.This.addPopup(mn);
                        mn.setClickable(true);
                        Ui.bk.add(new call() {
                            @Override
                            public void onCall(boolean bl) {
                                ContentHome.This.MenuHome.removeCatch();
                                ContentHome.This.removePopup(mn);
                            }
                        });
                    }

                }
            });
            addView(menu);

            no = textImg.getFMText(context,position + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(30) + height);
            no.setY(Ui.cd.getHt(13));
            no.img.P0.setColor(0x33FFFFFF);
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0], Ui.cd.getHtF(18));
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            name.setY(Ui.cd.getHt(12));
            if(width - Ui.cd.getHt(170) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(150)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.P0.setColor(0x66FFFFFF);
            }
            name.setClickable(false);
            addView(name);

            artist = textImg.getFMText(context,data.get(position)[2], Ui.cd.getHt(14));
            artist.setX(name.getX());
            artist.setY(Ui.cd.getHt(12 + 22));
            artist.setClickable(false);
            if(width - Ui.cd.getHt(170) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(150)),name.height);
                artist.img.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
            }else{
                artist.img.P0.setColor(0x33FFFFFF);
            }

            addView(artist);


             setBackgroundColor(itemBack.Color0);

            btnLine = new squre(width, Ui.cd.getHt(2), Ui.cd.getHt(30), Ui.cd.getHt(60-2));
            btnLine.setColor(0x22000000);

            rSqure = new radiusSqure(width - Ui.cd.getHt(30),height - Ui.cd.getHt(2), Ui.cd.getHt(30),0,0);
            rSqure.setColor(itemRect.Color0);

            rtop = new radiusSqure(width - Ui.cd.getHt(30+4),height - Ui.cd.getHt(2+4), Ui.cd.getHt(30+2), Ui.cd.getHt(2),0);
            rtop.setColor(0x99000000);

            leftSq = new squre(Ui.cd.getHt(30), Ui.cd.getHt(60),0,0);
            leftSq.setColor(0x33000000);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Ui.ef.clickPlay();
                   //Ui.ef.MusicPlayer.handler.playByPlaylistName("ok");
                    if(seclection){
                        if(selected[position]){
                            selected[position] = false;
                        }else{
                            selected[position] = true;
                        }
                        Item.this.invalidate();
                        onSelect();
                    }else{
                        if(data.get(position)[4] != null && data.get(position)[4].equals("-1")){
                            goBack();
                        }else{
                            //For Folder Only
                            if(data.get(position)[4] != null && data.get(position)[4].equals("0")){
                                crtPath += data.get(position)[0] + "/";
                                reloadFolder();
                            }

                        }
                    }
                }
            });

            //if(data.get(position)[3] != null){
                stick = itemStick.getFMview(getContext(),false);
                stick.setX(Ui.cd.getHt(0));
                stick.setY(Ui.cd.getHt(0));
                //stick.height = stick.width;
                addView(stick);

                txt = textImg.getFMText(getContext(),"", Ui.cd.getHt(18));

                txt.img.setColor(0x88FFFFFF);
                addView(txt);
            //}
            if(data.get(position)[3] != null){
                stick.setVisibility(VISIBLE);
                txt.setVisibility(VISIBLE);
                txt.setText(data.get(position)[3]);
                txt.InCenter(stick.width,stick.width);
            }else{
                stick.setVisibility(GONE);
                txt.setVisibility(GONE);
            }
            //Schar = data.get(position)[0].substring(0,1).toUpperCase();

            foldericon = folderIcon.getFMview(context,false);
            foldericon.setClickable(false);
            foldericon.setSize(height - Ui.cd.getHt(12),height - Ui.cd.getHt(12));
            foldericon.setX(Ui.cd.getHt(30)+ Ui.cd.getHt(6));
            foldericon.setY(Ui.cd.getHt(6));
            addView(foldericon);

            folderBack = folderbackIcon.getFMview(context,false);
            folderBack.setClickable(false);
            folderBack.setSize(height - Ui.cd.getHt(12),height - Ui.cd.getHt(12));
            folderBack.setX(Ui.cd.getHt(30)+ Ui.cd.getHt(6));
            folderBack.setY(Ui.cd.getHt(6));
            addView(folderBack);

            fileicon = fileIcon.getFMview(context,false);
            fileicon.setClickable(false);
            fileicon.setSize(height - Ui.cd.getHt(12),height - Ui.cd.getHt(12));
            fileicon.setX(Ui.cd.getHt(30)+ Ui.cd.getHt(6));
            fileicon.setY(Ui.cd.getHt(6));
            addView(fileicon);
            setData(Id);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(selected[position]){
                rSqure.draw(canvas);
                rtop.draw(canvas);
            }else{
                rSqure.draw(canvas);
            }
            super.postDraw(canvas);
            super.afterDraw(canvas,rSqure.S0);
        }

        public void setChar(boolean aChar) {
            if(txt != null){
                if(!aChar){
                    stick.setVisibility(GONE);
                    txt.setVisibility(GONE);
                }else{
                    stick.setVisibility(VISIBLE);
                    txt.setVisibility(VISIBLE);
                }
            }
        }


    }



    public void goBack() {
        crtPath = crtPath.substring(0,crtPath.length()-1);
        crtPath = crtPath.substring(0,crtPath.lastIndexOf("/")+1);
        reloadFolder();
    }

}
