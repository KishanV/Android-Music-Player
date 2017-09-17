package Views.Library.Menu.ArtistClass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemRect;
import com.shape.home.menu.songDots;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Views.ContentHome;
import Views.Popups.songsBtns;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.StringUtil;
import Views.radiusSqure;
import Views.textImg;

public class popupAdapter extends BaseAdapter {

    public ArrayList<String[]> data = new ArrayList<String[]>();
    public ArrayList<String[]> old = data;
    public HashMap<String, Integer> index = new HashMap<String, Integer>();
    public HashMap<String, Integer> oldIndex = index;
    public boolean[] selected;

    public popupAdapter(){
            String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?";
            String[] searchPram = new String[]{"1"};
            String[] cols = new String[] {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST};
            Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString + getId(),searchPram, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC");

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

        selected = new boolean[data.size()];
        //Log.i("My","Song Count : " + cursor.getCount());

    }

    public  String getId(){
        return "AND " + MediaStore.Audio.Media.ARTIST_ID +" = ";
    }

    public  String getName(){
        return "Error.";
    }

    public  String[] getAlbumData(){
        return null;
    }

    public int  getHeight(){
        return Ui.cd.DPH;
    }

    public int  getWidth(){
        return Ui.cd.DPW;
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

    public void onMenu(){

    }

    public void onBack(){

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

    public int[] getAIDs(){
        int[] AIDs = new int[data.size()];
        if(seclection){
            int count = 0;
            for(int a = 0;a < selected.length;a++){
                if(selected[0] == true){
                    count++;
                }
            }
            AIDs =  new int[count];
            count = 0;
            for(int a = 0;a < selected.length;a++){
                if(selected[0] == true){
                    AIDs[count] = Integer.parseInt(data.get(a)[1]);
                    count++;
                }
            }
        }else{
            for(int a = 0;a < selected.length;a++){
                AIDs[a] = Integer.parseInt(data.get(a)[1]);
            }
        }
        return AIDs;
    }
    public class Item extends FMlyt{



        public radiusSqure rSqure;
        public radiusSqure rtop;
        //FMText no;
        //FMText listName;
        textImg nameText;
        textImg noText;
        FMText artist;


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
            nameText.setText(data.get(position)[0]);
            nameText.InCenter(0,height);
            nameText.setX((int) (noText.width + Ui.cd.getHt(5) + noText.getX()));

            noText.setY((int) nameText.y);
            if(width - Ui.cd.getHt(120) < nameText.width){
                nameText.setSize((int) (width - Ui.cd.getHt(100)), (int) nameText.height);
                nameText.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                nameText.setColor(0x66FFFFFF);
            }





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
            super(context,popupAdapter.this.getWidth(), Ui.cd.getHt(44));
            setLayoutParams(new ListView.LayoutParams(width, Ui.cd.getHt(44)));
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


            rSqure = new radiusSqure(width,height - Ui.cd.getHt(2),0,0,0);
            rSqure.setColor(itemRect.Color0);
            addShape(rSqure);

            rtop = new radiusSqure(width - Ui.cd.getHt(4),height - Ui.cd.getHt(2+4), Ui.cd.getHt(2), Ui.cd.getHt(2),0);
            rtop.setColor(0x99000000);
            rtop.setDrawing(false);
            addShape(rtop);


            final ShapeView menu = songDots.getFMview(context,true);
            menu.setRipple(true,0.3f);
            menu.setRippleDown(false);
            menu.setX(width - menu.width);

            menu.onClick(new call(){
                @Override
                public void onCall(boolean bl) {

                    onMenu();
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
                                Ui.ef.MusicPlayer.handler.playALlSong(position,getAIDs(),"ARTIST : " + getName());
                                Ui.bk.back();
                            }else if(name.equals("ADD NEXT")) {

                                if (seclection) {
                                    Ui.ef.MusicPlayer.handler.addSongsNext(getAIDs());
                                } else{
                                    Ui.ef.MusicPlayer.handler.addSongsNext(new int[]{Integer.parseInt(data.get(position)[1])});
                                }
                                Ui.bk.back();

                            }else if(name.equals("SHOW FILE")){
                                Ui.bk.back();
                                Ui.bk.back();
                                Ui.bk.add(new call(){
                                    @Override
                                    public void onCall(boolean bl) {
                                        ContentHome.This.MenuHome.goingBack();
                                        ContentHome.This.MenuHome.openPage(3);
                                        ContentHome.This.MenuHome.drawCatch();
                                        final  songsPopup mn = new songsPopup(context, Ui.cd.DPW, Ui.cd.DPH,getAlbumData());
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
                    //ContentHome.This.MenuHome.drawCatch();
                    ContentHome.This.addPopup(mn);
                    mn.setClickable(true);
                    Ui.bk.add(new call() {
                        @Override
                        public void onCall(boolean bl) {
                            ContentHome.This.removePopup(mn);
                           // ContentHome.This.MenuHome.removeCatch();
                            onBack();
                        }
                    });
                }
            });
            addView(menu);

            noText = textImg.getText((position+1)+".", Ui.cd.getHtF(14));
            noText.setX(Ui.cd.getHt(10));
            noText.setColor(0x33FFFFFF);
            addShape(noText);

            nameText = textImg.getText(data.get(position)[0], Ui.cd.getHtF(16));
            nameText.InCenter(0,height);
            nameText.setX((int) (noText.width + Ui.cd.getHt(5) + noText.getX()));
            noText.setY((int) nameText.y);
            if(width - Ui.cd.getHt(120) < nameText.width){
                nameText.setSize((int) (width - Ui.cd.getHt(100)), (int) nameText.height);
                nameText.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});
            }else{
                nameText.setColor(0x66FFFFFF);
            }
            addShape(nameText);



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
                        Ui.ef.MusicPlayer.handler.playALlSong(position,getAIDs(),"ARTIST : " + getName());
                    }

                }
            });

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.drawShape(canvas);
            super.postDraw(canvas);
            super.afterDraw(canvas,rSqure.S0);

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
