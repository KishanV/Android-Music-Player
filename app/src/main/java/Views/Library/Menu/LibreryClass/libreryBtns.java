package Views.Library.Menu.LibreryClass;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.Icon.deleteIcon;
import com.shape.Library.Icon.fileInfoIcon;
import com.shape.Library.Icon.musiclibreryIcon;
import com.shape.Library.Icon.renameIcon;
import com.shape.Library.Icon.ringToneIcon;
import com.shape.Library.Icon.shareFileIcon;
import com.shape.Library.allsong.menucloseBtn;
import com.shape.home.menu.backgroundImg;
import com.shape.home.menu.btmBack;

import java.io.File;
import java.util.ArrayList;

import Views.ContentHome;
import Views.Home.Menu.Item;
import Views.Library.Menu.SongsClass.playListsAdapter;
import Views.Popups.getText;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class libreryBtns extends FMlyt {

    FMlyt Menu;
    AnimatorSet Set;
    String val;
    ShapeView MainIcon;
    public static radiusSqure back;
    String[] data;


    public libreryBtns(Context context, int width, int height, String[] data) {
        super(context, width, height);
        setBackgroundColor(0x99000000);
        this.data = data;

        Menu = new FMlyt(context, Ui.cd.DPW - Ui.cd.getHt(40), Ui.cd.getHt(185)){
            @Override
            protected void onDraw(Canvas canvas){
                back.draw(canvas);
                canvas.clipPath(back.S0);
                super.onDraw(canvas);
            }
        };
        back = new radiusSqure(Menu.width,Menu.height,0,0, Ui.cd.getHt(13));
        back.setColor(backgroundImg.Color0);
        Menu.InCenter(width,height);
        Menu.setBackgroundColor(0x00000000);
        addView(Menu);
        setAlpha(0);

        MainIcon = musiclibreryIcon.getFMview(context,true);
        MainIcon.setSize(Ui.cd.getHt(40), Ui.cd.getHt(40));
        MainIcon.setX(Ui.cd.getHt(5));
        MainIcon.setY(Ui.cd.getHt(5));
        Menu.addView(MainIcon);

        final ShapeView cb = menucloseBtn.getFMview(getContext(),true);
        cb.setRipple(true,0.3f);
        cb.onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                Ui.bk.back();
            }
        });
        cb.setX(Menu.width - cb.width);
        Menu.addView(cb);

        FMText title = textImg.getFMText(getContext(),"PLALIST OPTIONS", Ui.cd.getHt(16));
        title.InCenter(MainIcon);
        title.img.setColor(0x99ffffff);
        title.setX(MainIcon.width + Ui.cd.getHt(20));
        Menu.addView(title);

        FMText forText = textImg.getFMText(getContext(),"SHARE AND PLAY.", Ui.cd.getHt(12));
        forText.img.setColor(0x66ffffff);
        forText.setX(MainIcon.width + Ui.cd.getHt(20));
        forText.setY(title.getY() + title.height + Ui.cd.getHt(10));
        Menu.addView(forText);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });

        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(200);
        Set.playTogether(
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );
        Set.start();
        init(Ui.cd.getHt(60));
    }




    void setList(){
        ListView listview;
        listview = new ListView(getContext());
        listview.setBackgroundColor(btmBack.Color0);
        listview.setLayoutParams(new LayoutParams(Menu.width,Menu.height - Ui.cd.getHt(70+120)));
        listview.setY(Ui.cd.getHt(50));
        listview.setDivider(null);
        listview.setX(0);
        listview.setY(Ui.cd.getHt(70));
        listview.setAdapter(new playListsAdapter(Menu){
            @Override
            public void onSelect(String Id) {
                libreryBtns.this.onSelect(Id);
            }
        });
        Menu.addView(listview);
        init((int) (Menu.height - Ui.cd.getHt(70+120) + listview.getY()));
    }

    void init(int top){
        int len = 4;
        int line = 1;
        int spaceWidth = Ui.cd.getHt(15);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)Menu.width - Ui.cd.getHt(15 * (len + 1))) / len);
        int itemHeight = (int)(itemWidth * 1.5f);
        int iconSize = (int)(itemWidth * 0.7f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                if((j*len) + i < 6){
                    final Item item = new Item(getContext(),itemWidth,itemHeight);
                    item.onClick(new call(){
                        @Override
                        public void onCall(boolean bl) {
                            onBtn(item.name.Text);
                        }
                    });

                    //item.setBackgroundColor(0xFFCCCCCC);
                    item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                    item.setY(top + (j * itemHeight) + (spaceWidth * (j + 1)));
                    Menu.addView(item);
                    Log.i("My","(i*len) + j = " + ((i*len) + j));
                    switch ((j*len) + i){
                        case 0:
                            item.setData("PLAY", new com.shape.Library.Icon.playAllIcon(iconSize,iconSize,0,0));
                            break;
                        case 1:
                            item.setData("RENAME",new renameIcon(iconSize,iconSize,0,0));
                            break;
                        case 2:
                            item.setData("DELETE",new deleteIcon(iconSize,iconSize,0,0));
                            break;
                        case 3:
                            item.setData("SHARE",new shareFileIcon(iconSize,iconSize,0,0));
                            break;
                        case 4:
                            item.setData("RINGTONE",new ringToneIcon(iconSize,iconSize,0,0));
                            break;
                        case 5:
                            item.setData("INFO",new fileInfoIcon(iconSize,iconSize,0,0));
                            break;
                    }
                }

            }
        }

    }

    public void needReload(){

    }

    public boolean fromHtml = false;
    public void onBtn(String name){
        if(name.equals("PLAY")){
            Ui.ef.MusicPlayer.handler.playByPlaylistId(data[1]);
            Ui.bk.back();
        } else if(name.equals("RENAME")){
            Ui.bk.back();
            final getText gt = new getText(getContext(), Ui.cd.DPW, Ui.cd.DPH,"ENTER TEXT FOR PLAYLIST"){
                @Override
                public boolean onEnter(String str) {
                    if(-1 == playlistHandler.getPlaylistIdByName(Ui.ef.getContentResolver(),str)){
                        playlistHandler.updateName(Ui.ef.getContentResolver(),str,data[0]);
                        needReload();
                        return  true;
                    }
                    return super.onEnter(str);
                }
            };
            ContentHome.This.addPopup(gt);
            gt.setClickable(true);
            Ui.bk.add(new call() {
                @Override
                public void onCall(boolean bl) {
                    ContentHome.This.removePopup(gt);
                    InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ContentHome.This.getWindowToken(), 0);
                }
            });
        }else if(name.equals("DELETE")){
            Ui.bk.back();
            getContext().getContentResolver().delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, MediaStore.Audio.Playlists._ID +" = ?", new String[]{data[1]});
            needReload();
        }else if(name.equals("SHARE")){
            Ui.bk.back();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "All Songs from this Albums...");
            intent.setType("image/jpeg"); /* This example is sharing jpeg images. */

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getAlbumsFiles());
            Ui.ef.startActivity(intent);
        }
    }

    int[] getArtistsAIDs(){

        String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?" + "AND " + MediaStore.Audio.Media.ARTIST_ID + " = " + data[1];
        String[] searchPram = new String[]{"1"};
        String[] cols = new String[] {MediaStore.Audio.Media._ID};
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString  ,searchPram,null);
        int[] AIDs = new int[cursor.getCount()];
        int count = 0;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            AIDs[count] = cursor.getInt(0);
            count++;
        }
        cursor.close();
        return  AIDs;

    }

    String getFirstPath(){

        String searchString = MediaStore.Audio.Media.IS_MUSIC + "=?" + "AND " + MediaStore.Audio.Media.ARTIST_ID + " = " + data[1];
        String[] searchPram = new String[]{"1"};
        String[] cols = new String[] {MediaStore.Audio.Media.DATA};
        Cursor cursor = Ui.ef.getBaseContext().getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cols, searchString  ,searchPram,null);



        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            return  cursor.getString(0);
        }
        cursor.close();
        return  null;

    }


    ArrayList<Uri> getAlbumsFiles(){


        String[] projection = {MediaStore.Audio.Playlists.Members.DATA};
        Cursor cursor = Ui.ef.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", Integer.parseInt(data[1])), projection,
                MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null,  MediaStore.Audio.Playlists.Members.PLAY_ORDER +" ASC");

        ArrayList<Uri> files = new ArrayList<Uri>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            File file = new File(cursor.getString(0));
            Uri uri = Uri.fromFile(file);
            files.add(uri);
        }
        cursor.close();

        return  files;

    }

    public void onSelect(String id){
        Log.i("My","Id : " + id);
        Ui.bk.back();
        int[] AIDs = getArtistsAIDs();
        if(Integer.parseInt(id) != -1){
            playlistHandler.addTOPlaylistByID(Ui.ef.getContentResolver(),Integer.parseInt(id),AIDs);
        }
        if(Integer.parseInt(id) == (Ui.ef.MusicPlayer.handler.playlist.id)){
            Ui.ef.MusicPlayer.handler.addSongs(AIDs);
        }
    }

    public boolean onEnter(String str){
        return false;
    }
}
