package Views.Library.Menu.AlbumsClass;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.linedeer.api.ITask;
import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.albums.imgRing;
import com.shape.Library.albums.imgRingBack;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemMenu;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.allsong.itemStick;
import com.shape.home.slider.thumbRing;

import java.io.File;
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

public class albumAdapter extends BaseAdapter {

    public List<String[]> data = new ArrayList<String[]>();
    public List<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;


    public albumAdapter(){
        String[] albums_cols = { MediaStore.Audio.Albums.ALBUM,MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums.ALBUM_ART};
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,albums_cols,null,null, MediaStore.Audio.Albums.ALBUM +" COLLATE NOCASE ASC ");

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String str = cursor.getString(0);

            str = StringUtil.getFirstChar(str).toUpperCase();
            if(!str.equals("") && !index.containsKey(str)){
                 data.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),str,cursor.getString(3)});
                 index.put(str,i);
            }else{
                 data.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),null,cursor.getString(3)});
            }
        }

        selected = new boolean[data.size()];
        //Log.i("My","Song Count : " + cursor.getCount());
        cursor.close();
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
                //str = StringUtil.getFirstChar(str).toUpperCase();
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
        ImageView im;
        imgRing ring;
        imgRingBack ringBack;

        FMText txt;
        ShapeView stick;
        int position;

        public void setData(final int position) {


            no.setText((position+1)+".");

            name.setText(data.get(position)[0]);
            name.setX (no.getX());
            name.setY(no.getY() + Ui.cd.getHt(10 + 10));
            if(width - Ui.cd.getHt(200) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(180)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.setColor(0x66FFFFFF);
            }

            artist.setText(data.get(position)[2] + " SONGS");
            artist.setX(name.getX());
            artist.setY(name.getY() + Ui.cd.getHt(16 + 10));
            if(width - Ui.cd.getHt(200) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(180)),name.height);
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
            setBackgroundColor(itemBack.Color0);

            if(position != this.position){
                loadImg();
            }
            this.position = position;
            invalidate();
        }


        int IHt = Ui.cd.DPW - Ui.cd.getHt(14);

        int colorN;
        void loadImg(){
            ring.P0.setColor(imgRing.Color0);
            im.setImageBitmap(null);
            if(imgLoad != null){
                imgLoad.cancel(true);
                imgLoad = null;
            }

            imgLoad = new ITask(){
                Bitmap Bm;
                int color = imgRing.Color0;

                @Override
                public void Go() {
                    if(Item.this.position != position){
                        return;
                    }
                    try{
                        File file = new File(data.get(position)[4]);
                        Bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                        color = thumbRing.Color0;
                        if(Bm != null){
                            Palette palette = Palette.from(Bm).generate();
                            int newColor = palette.getMutedColor(color);
                            if(newColor == color){
                                newColor = palette.getVibrantColor(color);
                            }
                            if(newColor == color){
                                newColor = palette.getLightVibrantColor(color);
                            }
                            if(newColor == color){
                                newColor = palette.getDarkVibrantColor(color);
                            }
                            color = newColor;
                        }

                    }catch(Exception Ex){
                        Bm = null;
                    }
                };

                @Override
                protected void onCancelled() {
                    if(Bm != null){
                       // Log.i("My","BM...");
                        Bm.recycle();
                    };
                    Bm = null;
                    //Log.i("My","Ok...");
                }

                @Override
                public void than() {
                    if(Item.this.position != position){
                        return;
                    }
                    if(Bm == null){
                        return;
                    }
                    colorN = color;
                    ring.P0.setColor(color);
                    im.setImageDrawable(null);
                    im.setImageBitmap(Bm);
                    im.invalidate();
                    Bm = null;
                    //item.Ivback.setImageBitmap(BM);
                };
            };
            imgLoad.execute();
        }

        public Item(final Context context, final int Id) {
            super(context, Ui.cd.DPW - Ui.cd.getHt(14), Ui.cd.getHt(60));
            setSize(IHt, (int) (IHt*0.35));
            setLayoutParams(new ListView.LayoutParams(IHt, (int) (IHt*0.35)));
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
            menu.setY(height - menu.height);

            menu.onClick(new call(){
                @Override
                public void onCall(boolean bl) {
                    ContentHome.This.MenuHome.drawCatch();
                    final albumsBtns mn = new albumsBtns(context, Ui.cd.DPW, Ui.cd.DPH,data.get(position));
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

            no = textImg.getFMText(context,(position+1) + ".", Ui.cd.getHt(14));
            no.setX(IHt * 0.35f + Ui.cd.getHt(8+30));
            no.setY(Ui.cd.getHt(13));
            no.img.P0.setColor(0x33FFFFFF);
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0], Ui.cd.getHtF(18));
            name.setX(no.getX());
            name.setY(no.getY() + Ui.cd.getHt(10 + 10));
            if(width - Ui.cd.getHt(200) < name.width){
                name.setSize((int) (width - Ui.cd.getHt(180)),name.height);
                name.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                name.img.P0.setColor(0x66FFFFFF);
            }
            name.setClickable(false);
            addView(name);

            artist = textImg.getFMText(context,data.get(position)[2] + " SONGS", Ui.cd.getHt(14));
            artist.setX(name.getX());
            artist.setY(name.getY() + Ui.cd.getHt(16 + 10));
            artist.setClickable(false);
            if(width - Ui.cd.getHt(200) < artist.width){
                artist.setSize((int) (width - Ui.cd.getHt(180)),name.height);
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

            ring = new imgRing((int) ((IHt*0.35) - Ui.cd.getHt(2)),((int) (IHt*0.35) - Ui.cd.getHt(2)),0,0);
            ringBack = new imgRingBack((int) ((IHt*0.35) - Ui.cd.getHt(2)),((int) (IHt*0.35) - Ui.cd.getHt(2)),0,0);
            im = new ImageView(getContext()){
                @Override
                protected void onDraw(Canvas canvas) {
                    ringBack.draw(canvas);
                    super.onDraw(canvas);
                    ring.draw(canvas);
                }
            };
            im.setLayoutParams(new LayoutParams((int) (IHt*0.35) - Ui.cd.getHt(2),(int) (IHt*0.35) - Ui.cd.getHt(2)));
            //im.setBackgroundColor(0xFFFFFFFF);
            im.setX(Ui.cd.getHt(30));
            im.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(im);
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


            loadImg();


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

        }





        ITask imgLoad;

        @Override
        protected void onDraw(Canvas canvas) {
           //super.onDraw(canvas);
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
