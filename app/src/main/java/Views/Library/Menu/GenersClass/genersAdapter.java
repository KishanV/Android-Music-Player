package Views.Library.Menu.GenersClass;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemMenu;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.allsong.itemStick;

import java.util.ArrayList;
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

public class genersAdapter extends BaseAdapter {

    public List<String[]> data = new ArrayList<String[]>();
    public List<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;


    public genersAdapter(){
        String[] albums_cols = { MediaStore.Audio.Genres.NAME,MediaStore.Audio.Genres._ID,MediaStore.Audio.Genres._ID };
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,albums_cols,null,null, MediaStore.Audio.Genres.NAME +" COLLATE NOCASE ASC ");

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String str = cursor.getString(0);
            str = StringUtil.getFirstChar(str).toUpperCase();
            int count = getSongsCount(cursor.getString(1));
            if(count != 0 && count != -1){
                if(!str.equals("") && !index.containsKey(str)){
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),count + " SONGS",str});
                    index.put(str,i);
                }else{
                    data.add(new String[]{cursor.getString(0),cursor.getString(1),count + " SONGS",null});
                }
            }

        }

        selected = new boolean[data.size()];
        cursor.close();
    }

    public int getSongsCount(String Id){
        String searchString = MediaStore.Audio.Genres.Members.IS_MUSIC+"=?";
        String[] searchPram = new String[]{"1"};
        String[] cols = new String[] {  MediaStore.Audio.Genres.Members.AUDIO_ID };
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query(MediaStore.Audio.Genres.Members.getContentUri("external", Long.parseLong(Id)),cols, searchString,searchPram, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC");
        return cursor.getCount();
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

            no.setText(position+".");

            name.setText(data.get(position)[0]);
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            if(width - Ui.cd.getHt(120) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.setColor(0x66FFFFFF);
            }

            artist.setText(data.get(position)[2] + " TRACKS");
            artist.setX(name.getX());
            if(width - Ui.cd.getHt(120) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                artist.img.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
            }else{
                artist.img.setColor(0x33FFFFFF);
            }


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
            Schar = StringUtil.getFirstChar(data.get(position)[0]).toUpperCase();
            invalidate();
        }

        public Item(final Context context, final int Id) {
            super(context, Ui.cd.DPW - Ui.cd.getHt(14), Ui.cd.getHt(60));
            setLayoutParams(new ListView.LayoutParams(width, Ui.cd.getHt(60)));
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
                    ContentHome.This.MenuHome.drawCatch();
                    final genersBtns mn = new genersBtns(getContext(), Ui.cd.DPW, Ui.cd.DPH,data.get(position));
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
            });
            addView(menu);

            no = textImg.getFMText(context,position + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(8+30));
            no.setY(Ui.cd.getHt(13));
            no.img.P0.setColor(0x33FFFFFF);
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0], Ui.cd.getHtF(18));
            name.setX(no.width + Ui.cd.getHt(5) + no.getX());
            name.setY(Ui.cd.getHt(12));
            if(width - Ui.cd.getHt(120) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.P0.setColor(0x66FFFFFF);
            }
            name.setClickable(false);
            addView(name);

            artist = textImg.getFMText(context,data.get(position)[2]+ " TRACKS", Ui.cd.getHt(14));
            artist.setX(name.getX());
            artist.setY(Ui.cd.getHt(12 + 22));
            artist.setClickable(false);
            if(width - Ui.cd.getHt(120) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(100)),name.height);
                artist.img.setEfects(new int[]{0x33FFFFFF,0x33FFFFFF,0x00FFFFFF});
            }else{
                artist.img.P0.setColor(0x33FFFFFF);
            }

            addView(artist);

            if(position < data.size() ){
                setBackgroundColor(itemBack.Color0);
            }else{
                setAlpha(0);
            }
            btnLine = new squre(width, Ui.cd.getHt(2), Ui.cd.getHt(30), Ui.cd.getHt(60-2));
            btnLine.setColor(0x22000000);

            rSqure = new radiusSqure(width - Ui.cd.getHt(30),height - Ui.cd.getHt(2), Ui.cd.getHt(30),0,0);
            rSqure.setColor(itemRect.Color0);

            rtop = new radiusSqure(width - Ui.cd.getHt(30+4),height - Ui.cd.getHt(2+4), Ui.cd.getHt(30+2), Ui.cd.getHt(2),0);
            rtop.setColor(0x99000000);

            leftSq = new squre(Ui.cd.getHt(30), Ui.cd.getHt(60),0,0);
            leftSq.setColor(0x33000000);

            onClick(new call(){
                @Override
                public void onCall(boolean bl) {

                    if(seclection){
                        if(selected[position]){
                            selected[position] = false;
                        }else{
                            selected[position] = true;
                        }
                        Item.this.invalidate();
                        onSelect();
                    }else{
                        ContentHome.This.MenuHome.drawCatch();
                        final songsPopup mn = new songsPopup(context, Ui.cd.DPW, Ui.cd.DPH,data.get(position));
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
            Schar = StringUtil.getFirstChar(data.get(position)[0]).toUpperCase();
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

}
