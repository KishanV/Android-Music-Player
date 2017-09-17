package Views.Home.PlayList.PlayListClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.linedeer.api.EventCall;
import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.linedeer.player.musicPlayer;
import com.player.audioHandler;
import com.player.playerEvents;
import com.shape.home.playlist.backgroundImg;
import com.shape.home.playlist.menu;
import com.shape.home.playlist.nextBtn;
import com.shape.home.playlist.prevBtn;
import com.shape.home.playlist.repeatIcon;
import com.shape.home.playlist.searchIcon;
import com.shape.home.playlist.shuffleIcon;
import com.shape.home.playlist.thumbBAck;
import com.shape.home.playlist.thumbRing;

import Views.ContentHome;
import Views.Home.songSlider;
import Views.Home.PlayList.playLIstHome;
import Views.Popups.playlistMenu;
import Views.Popups.searchView;
import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.textImg;

public class Screen {

    FMlyt layout;
    FMText title;
    FMView titleBack;
    public ListView listview;
    ImageView img;
    songSlider SongSlider;
    FMView Menu;
    ShapeView NextBtn;
    ShapeView PrevBtn;
    ShapeView tRing;

    public Screen(playLIstHome playLIstHome) {
        layout = playLIstHome;

        titleBack = new FMView(layout.getContext(),layout.width,(Ui.cd.getHt(50)));
        titleBack.setBackgroundColor(backgroundImg.Color0);
        layout.addView(titleBack);

        Menu = menu.getFMview(layout.getContext(),true);
        Menu.setX(layout.width - Menu.width);
        Menu.setRipple(true,0.2f);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FMlyt VL = new playlistMenu(layout.getContext(), Ui.cd.DPW, Ui.cd.DPH);
                VL.setClickable(true);
                ContentHome.This.addPopup(VL);
                Ui.ef.clickPlay();
                Ui.bk.add(new call() {
                    @Override
                    public void onCall(boolean bl) {
                        ContentHome.This.removePopup(VL);
                    }
                });
            }
        });
        layout.addView(Menu);

        if(Ui.ef.MusicPlayer != null){
            title = textImg.getFMText(layout.getContext(),"NowPlaying ( "+ Ui.ef.MusicPlayer.handler.playlist.listName+" )", Ui.cd.getHt(18));
        }else{
            title = textImg.getFMText(layout.getContext(),"NowPlaying ( SONGS )", Ui.cd.getHt(18));
        }

        title.img.setColor(0x99FFFFFF);
        title.setY((Ui.cd.getHt(50) - title.height) / 2);
        title.setX((Ui.cd.getHt(50) - title.height) / 2);
        if(title.width > layout.width - Ui.cd.getHt(50)){
            title.setSize(layout.width - Ui.cd.getHt(50),title.height);
        }
        layout.addView(title);

        listview = new ListView(layout.getContext());
        listview.setLayoutParams(new FrameLayout.LayoutParams(layout.width,layout.height - Ui.cd.getHt(250 + 50)));
        listview.setY(Ui.cd.getHt(250));
        listview.setDivider(null);
        //listview.setBackgroundColor(com.shape.home.slider.backgroundImg.Color0);
        listview.setBackgroundColor(com.shape.home.slider.backgroundImg.Color0);
        layout.addView(listview);

        int imgWh = (int) (layout.width * 0.5f);

        final ShapeView tBack = thumbBAck.getFMview(layout.getContext(),false);
        tBack.setSize(imgWh,imgWh);

        tRing = thumbRing.getFMview(layout.getContext(),false);
        tRing.setSize(imgWh + Ui.cd.getHt(2),imgWh + Ui.cd.getHt(2));

        img = new ImageView(layout.getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                canvas.clipPath(tBack.img.mask);
                tBack.img.draw(canvas);
                super.onDraw(canvas);
            }
        };
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //img.setBackgroundColor(0xFFcccccc);
        img.setLayoutParams(new FrameLayout.LayoutParams(imgWh,imgWh));
        img.setY(Ui.cd.getHt(50) + (((Ui.cd.getHt(200) - imgWh) / 2)));
        img.setX( (((layout.width - imgWh) / 2)));
        if(android.os.Build.VERSION.SDK_INT <= 18){
            img.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        layout.addView(img);

        tRing.setY(img.getY() - Ui.cd.getHt(1));
        tRing.setX(img.getX() - Ui.cd.getHt(1));
        layout.addView(tRing);

        PrevBtn = prevBtn.getFMview(layout.getContext(),true);
        PrevBtn.setX(((img.getX() - PrevBtn.width) / 2f) - 2);
        PrevBtn.setY(Ui.cd.getHt(50) + ((Ui.cd.getHt(200) - PrevBtn.height) / 2f));
        PrevBtn.setRipple(true,0.3f);
        PrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.ef.MusicPlayer.handler.playPrevious();
            }
        });
        layout.addView(PrevBtn);

        NextBtn = nextBtn.getFMview(layout.getContext(),true);
        NextBtn.setX((layout.width - img.getX() ) + (img.getX() - NextBtn.width ) / 2f);
        NextBtn.setY(Ui.cd.getHt(50) + ((Ui.cd.getHt(200) - NextBtn.height) / 2f));
        NextBtn.setRipple(true,0.3f);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.ef.MusicPlayer.handler.playNext();
            }
        });
        layout.addView(NextBtn);

        SongSlider = new songSlider(layout.getContext(),layout.width, Ui.cd.getHt(22));
        SongSlider.setY(Ui.cd.getHt(250) - Ui.cd.getHt(12));
        SongSlider.setBtns(true);
        layout.addView(SongSlider);


        RI = repeatIcon.getFMview(layout.getContext(),true);
        RI.setRipple(true,0.3f);
        RI.setY(layout.height - RI.height);
        RI.setX(RI.width);
        layout.addView(RI);
        RI .onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                if(Ui.ef.MusicPlayer.handler.playlist.reapeat){
                    Ui.ef.MusicPlayer.handler.playlist.reapeat = false;
                }else{
                    Ui.ef.MusicPlayer.handler.playlist.reapeat = true;
                }
                Ui.ef.MusicPlayer.handler.playlistMode();
            }
        });
        
        SI = shuffleIcon.getFMview(layout.getContext(),true);
        SI.setRipple(true,0.3f);
        SI.setY(layout.height - SI.height);

        layout.addView(SI);
        SI.onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                if(Ui.ef.MusicPlayer.handler.playlist.shuffle){
                    Ui.ef.MusicPlayer.handler.playlist.shuffle = false;
                }else{
                    Ui.ef.MusicPlayer.handler.playlist.shuffle = true;
                }
                Ui.ef.MusicPlayer.handler.playlistMode();
            }
        });


        SB = searchIcon.getFMview(layout.getContext(),true);
        SB.setRipple(true,0.3f);
        SB.setY(layout.height - SB.height);
        SB.setX(layout.width - SB.width);
        layout.addView(SB);
        SB.onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                searchOn();
            }
        });


        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.SONG_CHANGED,playerEvents.PLAYLIST_CHANGED, Ui.ef.Event_onBind}){
            @Override
            public void onCall(int eventId) {
                if(eventId == Ui.ef.Event_onBind ){
                    data = new songAdapter(layout);
                    listview.setAdapter(data);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadBitmaps();
                        }
                    }).start();
                }
                if(eventId == playerEvents.PLAYLIST_CHANGED){
                    data = new songAdapter(layout);
                    listview.setAdapter(data);
                    title = textImg.getFMText(layout.getContext(),"NowPlaying ( "+ Ui.ef.MusicPlayer.handler.playlist.listName+" )", Ui.cd.getHt(18));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadBitmaps();
                        }
                    }).start();
                }
                if(eventId == playerEvents.SONGS_ADDED){
                    listview.invalidateViews();
                    listview.invalidate();
                }
                if(eventId == playerEvents.SONG_CHANGED){
                    listview.invalidateViews();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadBitmaps();
                        }
                    }).start();
                }
            }
        });

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.PLAYLIST_MODE,playerEvents.SONG_CHANGED,playerEvents.PLAYLIST_CHANGED, Ui.ef.Event_onBind}){
            @Override
            public void onCall(int eventId) {

                if(eventId == Ui.ef.Event_onBind || eventId == playerEvents.PLAYLIST_MODE){
                    PlaylistMODE();
                }

            }
        });
    }

    FMView RI;
    FMView SI;
    FMView SB;
    songAdapter data;

    void PlaylistMODE(){
        if(Ui.ef.MusicPlayer.handler.playlist.reapeat){
            RI.setAlpha(1);
        }else{
            RI.setAlpha(0.4f);
        }

        if(Ui.ef.MusicPlayer.handler.playlist.shuffle){
            SI.setAlpha(1);
        }else{
            SI.setAlpha(0.4f);
        }
    }
    boolean serching = false;
    void searchOn(){
        listview.setY(Ui.cd.getHt(50));
        listview.setLayoutParams(new FrameLayout.LayoutParams(layout.width,layout.height- Ui.cd.getHt(50)));
        serching = true;

        final searchView sv = new searchView(layout.getContext(),layout.width, Ui.cd.getHt(50)){
            @Override
            public void onType(String str) {
                super.onType(str);
                if(str.length() != 0){
                    data.search(str);
                }else{
                    data.search(null);
                }
                listview.invalidateViews();
                listview.invalidate();
            }

            @Override
            public void onClose() {
                Ui.bk.back();
            }
        };
        sv.setBackgroundColor(backgroundImg.Color0);
        layout.addView(sv);

        img.setVisibility(View.GONE);
        SongSlider.setVisibility(View.GONE);
        NextBtn.setVisibility(View.GONE);
        PrevBtn.setVisibility(View.GONE);
        tRing.setVisibility(View.GONE);
        RI.setVisibility(View.GONE);
        SI.setVisibility(View.GONE);
        SB.setVisibility(View.GONE);

        Ui.bk.add(new call(){
            @Override
            public void onCall(boolean bl) {
                //super.onCall(bl);
                data.search(null);
                listview.setLayoutParams(new FrameLayout.LayoutParams(layout.width,layout.height - Ui.cd.getHt(250 + 50)));
                listview.setY(Ui.cd.getHt(250));
                layout.removeView(sv);
                serching = false;
                img.setVisibility(View.VISIBLE);
                SongSlider.setVisibility(View.VISIBLE);
                NextBtn.setVisibility(View.VISIBLE);
                PrevBtn.setVisibility(View.VISIBLE);
                tRing.setVisibility(View.VISIBLE);
                RI.setVisibility(View.VISIBLE);
                SI.setVisibility(View.VISIBLE);
                SB.setVisibility(View.VISIBLE);
            }
        });
    }

    void loadBitmaps(){
        if(Ui.ef.MusicPlayer != null){
            musicPlayer mp = Ui.ef.MusicPlayer;
            final Bitmap bm = audioHandler.getAlubumArtBitmapById(Ui.ef.getContentResolver(),mp.handler.AID);
            if(bm != null){
                Palette palette = Palette.from(bm).generate();
                int color = thumbRing.Color0;
                int newColor = palette.getMutedColor(color);
                if(newColor == color){
                    newColor = palette.getVibrantColor(color);
                }
                if(newColor == color){
                    newColor = palette.getLightVibrantColor(color);
                }
                tRing.img.maskPaint.setColor(newColor);
            }else{
                tRing.img.maskPaint.setColor(thumbRing.Color0);
            }

            Ui.ef.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tRing.invalidate();
                    img.setImageDrawable(null);
                    img.setImageBitmap(bm);
                }
            });

        }else{
            img.setImageDrawable(null);
            img.setImageBitmap(null);
        }
    }

    public void isAction(boolean state) {
        if(!state && serching){
            Ui.bk.back();
        }
    }

    public void animEnd(boolean state) {
        if(state){
            listview.invalidateViews();
            listview.invalidate();
            if(Ui.ef.MusicPlayer != null){
                listview.setSelection(Ui.ef.MusicPlayer.handler.PID - 3);
            }
        }
    }
}
