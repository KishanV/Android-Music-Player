package Views.Library.Menu.LibreryClass;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemMenu;
import com.shape.Library.allsong.itemRect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Views.ContentHome;
import Views.Popups.getText;
import Views.Popups.menuBtns;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.StringUtil;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class libreryAdapter extends BaseAdapter {

    public List<String[]> data = new ArrayList<String[]>();
    public List<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;


    public libreryAdapter(){
        reloadData();
    }

    public void onRerload(){

    }


    public void reloadData(){
        List<String[]> data = new ArrayList<String[]>();
        HashMap<String, Integer> index = new HashMap<String, Integer>();
        boolean[] selected;

        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] cols = new String[] {MediaStore.Audio.Playlists.NAME, MediaStore.Audio.Playlists._ID,  MediaStore.Audio.Playlists.DATE_ADDED};

        Cursor cursor =  Ui.ef.getBaseContext().getContentResolver().query( uri,cols, null,null, MediaStore.Audio.Playlists.NAME +" ");//+" COLLATE NOCASE ASC");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date today = Calendar.getInstance().getTime();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String str = cursor.getString(0);
            if(!str.startsWith(" ") && str.length() != 0){
                str = StringUtil.getFirstChar(str).toUpperCase();
                int count = playlistHandler.getPlaylistLength(Ui.ef.getBaseContext().getContentResolver(),cursor.getInt(1));

                if(!str.equals("") && !index.containsKey(str)){
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),count+" SONGS",str});
                    index.put(str,i);
                }else{
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),count+" SONGS",null});
                }
            }


           // Log.i("My","cursor.getLong(2) : " + cursor.getString(2));
        }
        selected = new boolean[data.size()];
        cursor.close();
        this.data = data;
        this.index = index;
        this.selected = selected;
        onRerload();
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

    public boolean isExist(String str) {
        if(str == null && str.length() == 0 && str.startsWith(" ")){
            return true;
        }
        str = str.toUpperCase();
        for(int i = 0;i < data.size();i++){
            if(data.get(i)[0].toUpperCase().equals(str)){
                return true;
            }
        }
        return false;
    }

    public class Item extends FMlyt{

        public squre btnLine;
        public radiusSqure rSqure;
        public radiusSqure rtop;
        FMText no;
        FMText name;
        FMText artist;


        int position;

        public void setData(int position) {
            this.position = position;

            no.setText((position+1)+".");

            name.setText(data.get(position)[0]);
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            if(width - Ui.cd.getHt(120) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                name.img.setEfects(new int[]{0xCCFFFFFF,0xCCFFFFFF,0x00FFFFFF});
            }else{
                name.img.setColor(0xCCFFFFFF);
            }

            artist.setText(data.get(position)[2]);
            artist.setX(name.getX());
            if(width - Ui.cd.getHt(120) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                artist.img.setEfects(new int[]{0x55FFFFFF,0x55FFFFFF,0x00FFFFFF});
            }else{
                artist.img.setColor(0x55FFFFFF);
            }
            invalidate();
        }

        public Item(final Context context, final int Id) {
            super(context, Ui.cd.DPW, Ui.cd.getHt(60));
            setLayoutParams(new ListView.LayoutParams(Ui.cd.DPW, Ui.cd.getHt(60)));
            setRipple(true,0.3f);
            setRippleDown(false);
            this.position = Id;

           /* setOnLongClickListener(new OnLongClickListener() {
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
*/
           /* ShapeView stick = itemStick.getFMview(context,false);
            stick.setInCenter(Ui.cd.getHt(16),height);
            //addView(stick);*/

            final ShapeView menu = itemMenu.getFMview(context,true);
            menu.setRipple(true,0.3f);
            menu.setRippleDown(false);
            menu.setX(width - menu.width);

              new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Ui.ef.clickPlay();
                    //Ui.ef.MusicPlayer.handler.playByPlaylistName("ok");
                    final menuBtns mn = new menuBtns(context, Ui.cd.DPW, Ui.cd.DPH){
                        @Override
                        public void onRename() {
                            //super.onRename();
                            final getText gt = new getText(getContext(), Ui.cd.DPW, Ui.cd.DPH,"ENTER TEXT FOR PLAYLIST"){
                                @Override
                                public boolean onEnter(String str) {
                                    if(!isExist(str)){
                                        playlistHandler.updateName(Ui.ef.getContentResolver(),str,data.get(position)[0]);
                                        reloadData();
                                        return  true;
                                    }
                                    return super.onEnter(str);
                                }
                            };
                            ContentHome.This.addPopup(gt);
                            gt.setClickable(true);
                            Ui.ef.clickPlay();
                            Ui.bk.add(new call() {
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.removePopup(gt);
                                    InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(ContentHome.This.getWindowToken(), 0);
                                }
                            });
                        }

                        @Override
                        public void onDelete() {
                            //Ui.bk.back();
                            getContext().getContentResolver().delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, MediaStore.Audio.Playlists._ID +" = ?", new String[]{data.get(position)[1]});
                            reloadData();
                        }

                        @Override
                        public void onClear() {
                            getContext().getContentResolver().delete(MediaStore.Audio.Playlists.Members.getContentUri( "external", Integer.parseInt(data.get(position)[1])), null, null);
                            reloadData();
                        }
                    };
                    ContentHome.This.addPopup(mn);
                    mn.setClickable(true);
                    Ui.ef.clickPlay();
                    Ui.bk.add(new call() {
                        @Override
                        public void onCall(boolean bl) {
                            ContentHome.This.removePopup(mn);
                        }
                    });
                }
            };

            final call menuCall = new call(){
                @Override
                public void onCall(boolean bl) {
                    ContentHome.This.MenuHome.drawCatch();
                    final libreryBtns mn = new libreryBtns(getContext(), Ui.cd.DPW, Ui.cd.DPH,data.get(position)){
                        @Override
                        public void needReload() {
                            reloadData();
                        }
                    };
                    //mn.fromHtml = true;
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
            };
            menu.onClick(menuCall);

            addView(menu);

            no = textImg.getFMText(context,(position+1) + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(8));
            no.setY(Ui.cd.getHt(13));
            no.img.P0.setColor(0x33FFFFFF);
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0], Ui.cd.getHtF(18));
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            name.setY(Ui.cd.getHt(12));
            if(width - Ui.cd.getHt(120) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                name.img.setEfects(new int[]{0xCCFFFFFF,0xCCFFFFFF,0x00FFFFFF});
            }else{
                name.img.P0.setColor(0xCCFFFFFF);
            }
            name.setClickable(false);
            addView(name);

            artist = textImg.getFMText(context,data.get(position)[2], Ui.cd.getHt(14));
            artist.setX(name.getX());
            artist.setY(Ui.cd.getHt(12 + 22));
            artist.setClickable(false);
            if(width - Ui.cd.getHt(120) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                artist.img.setEfects(new int[]{0x55FFFFFF,0x55FFFFFF,0x00FFFFFF});
            }else{
                artist.img.P0.setColor(0x55FFFFFF);
            }

            addView(artist);

            if(position < data.size() ){
                setBackgroundColor(itemBack.Color0);
            }else{
                setAlpha(0);
            }
            btnLine = new squre(width, Ui.cd.getHt(2),0, Ui.cd.getHt(60-2));
            btnLine.setColor(0x22000000);

            rSqure = new radiusSqure(width,height - Ui.cd.getHt(2),0,0,0);
            rSqure.setColor(itemRect.Color0);

            rtop = new radiusSqure(width,height - Ui.cd.getHt(2+4), Ui.cd.getHt(2), Ui.cd.getHt(2),0);
            rtop.setColor(0x99000000);

            onClick(new call(){
                @Override
                public void onCall(boolean bl) {
                    ContentHome.This.MenuHome.drawCatch();
                    final  songsPopup mn = new  songsPopup(context, Ui.cd.DPW, Ui.cd.DPH,data.get(position)){
                        @Override
                        public void onMenu() {
                            menuCall.onCall(false);
                        }
                    };
                    ContentHome.This.addPopup(mn);
                    mn.setClickable(true);
                    Ui.bk.add(new call() {
                        @Override
                        public void onCall(boolean bl) {
                            ContentHome.This.removePopup(mn);
                            ContentHome.This.MenuHome.setVisibility(VISIBLE);
                            ContentHome.This.MenuHome.removeCatch();
                            ContentHome.This.MenuHome.setAlpha(1,true);
                        }
                    });
                }
            });


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




    }

}
