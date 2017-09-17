package Views.Home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.view.MotionEvent;

import com.linedeer.api.EventCall;
import com.linedeer.api.ITask;
import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.linedeer.player.musicPlayer;
import com.player.audioHandler;
import com.player.playerEvents;
import com.shape.home.reapeatBtn;
import com.shape.home.shuffleBtn;
import com.shape.home.slider.backgroundImg;
import com.shape.home.slider.thumbRing;

import Views.Home.views.songSliderThumb;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.animLis;
import Views.textImg;

public class thumbSlider extends FMlyt{

    songSliderThumb firstThumb;
    songSliderThumb secondThumb;
    songSliderThumb threeThumb;
    songSliderThumb fourThumb;
    songSliderThumb fiveThumb;

    int thumbWidth;
    int thumbHeght;
    int thumbY;
    int counterImg = 3;

    public thumbSlider(Context context, int width, int height) {
        super(context, width, height);
       setBackgroundColor(backgroundImg.Color0);
        // setBackgroundColor(0x00000000);

        thumbWidth = (int)(width*0.550f);
       // thumbHeght = (int)(thumbWidth*1.40f);
        thumbHeght = thumbWidth;
        thumbY = (int) ((int)(height - thumbHeght ) /2f);

        FirstPoint =  -(width / 2) - (thumbWidth/2);
        SecondPoint =  0 - (thumbWidth/2);
        ThirdPoint =  (width / 2) - (thumbWidth/2);
        FourPoint = (width) - (thumbWidth/2);
        FivePoint = (width + width / 2) - (thumbWidth/2);

        firstThumb = new songSliderThumb(context,thumbWidth,thumbHeght,0);
        firstThumb.setX(FirstPoint);
        firstThumb.setY(thumbY);
        firstThumb.setPivotX(thumbWidth/2);
        firstThumb.setPivotY(thumbHeght/2);
        firstThumb.setClickable(false);
        addView(firstThumb);

        secondThumb = new songSliderThumb(context,thumbWidth,thumbHeght,1);
        secondThumb.setX(SecondPoint);
        secondThumb.setY(thumbY);
        secondThumb.setPivotX(thumbWidth/2);
        secondThumb.setPivotY(thumbHeght/2);
        secondThumb.setClickable(false);
        addView(secondThumb);

        threeThumb = new songSliderThumb(context,thumbWidth,thumbHeght,2);
        threeThumb.setX(ThirdPoint);
        threeThumb.setY(thumbY);
        threeThumb.setPivotX(thumbWidth/2);
        threeThumb.setPivotY(thumbHeght/2);
        threeThumb.setClickable(false);
        addView(threeThumb);

        fourThumb = new songSliderThumb(context,thumbWidth,thumbHeght,3);
        fourThumb.setX(FourPoint);
        fourThumb.setY(thumbY);
        fourThumb.setPivotX(thumbWidth/2);
        fourThumb.setPivotY(thumbHeght/2);
        fourThumb.setClickable(false);
        addView(fourThumb);

        fiveThumb = new songSliderThumb(context,thumbWidth,thumbHeght,4);
        fiveThumb.setX(FivePoint);
        fiveThumb.setY(thumbY);
        fiveThumb.setPivotX(thumbWidth/2);
        fiveThumb.setPivotY(thumbHeght/2);
        fiveThumb.setClickable(false);
        addView(fiveThumb);

        FirstX = firstThumb.getX();
        SecondX = secondThumb.getX();
        ThirdX = threeThumb.getX();
        FourX = fourThumb.getX();
        FiveX = fiveThumb.getX();
        setSlider(0);

        counterImg = -1;
        changImg(1);

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.PLAYLIST_MODE,playerEvents.SONG_CHANGED,playerEvents.PLAYLIST_CHANGED, Ui.ef.Event_onBind}){
            @Override
            public void onCall(int eventId) {
                if(eventId == Ui.ef.Event_onBind || eventId == playerEvents.SONG_CHANGED){
                    songChanged();
                }

                if(eventId == Ui.ef.Event_onBind || eventId == playerEvents.PLAYLIST_MODE){
                    PlaylistMODE();
                }

                if(eventId == Ui.ef.Event_onBind || playerEvents.PLAYLIST_CHANGED == eventId){
                    if(Ui.ef.MusicPlayer.handler.list!= null){
                      /*  int size = Ui.ef.MusicPlayer.handler.list.size();
                        btms = new Bitmap[size];
                        colors = new int[size];
                        AIDs = new int[size];*/
                        counterImg = 0;
                        songChanged();
                    }
                }
            }
        });
       // loadBitmaps();

          SpaceHt = (int) (height - (threeThumb.getY() + thumbHeght));

        timeText = textImg.getFMText(context,"10:20", Ui.cd.getHt(14));
        timeText.setMargin(true, Ui.cd.getHt(30), Ui.cd.getHt(20));
        timeText.setSqure(true, Ui.cd.getHt(13));
        timeText.img.setColor(0x66FFFFFF);
        timeText.rs.setColor(0x22000000);
        timeText.InCenter(width, SpaceHt);
        timeText.setY(threeThumb.getY() + thumbHeght + timeText.getY());
        addView(timeText);

        artist = textImg.getFMText(context,"Google", Ui.cd.getHt(14));
        songName = textImg.getFMText(context,"Google", Ui.cd.getHt(16));

        songHt = songName.height;
        songName.height = artist.height + Ui.cd.getHt(6) + songName.height;
        songName.setSize((int)(Ui.cd.DPW - Ui.cd.getHt(140)),songName.width);
        songName.InCenter(width,SpaceHt);
        //songName.setBackgroundColor(0x66FFFFFF);
        addView(songName);

        artist.img.setColor(0x22FFFFFF);
        artist.InCenter(width,0);
        artist.setY(songName.getY() + Ui.cd.getHt(6) + songHt);
        addView(artist);

        songName.setText("ABC999000");
        songName.setSize(Ui.cd.DPW - Ui.cd.getHt(60),songName.height + Ui.cd.getHt(4));
        artist.setText("ABCDEFG");
        artist.setSize(Ui.cd.DPW - Ui.cd.getHt(120),artist.height  + Ui.cd.getHt(4));
        songHt = songName.height;
        songName.height = artist.height + Ui.cd.getHt(6) + songName.height;
        songName.InCenter(width,SpaceHt);
        artist.InCenter(width,0);
        artist.setY(songName.getY() + Ui.cd.getHt(6) + songHt);

        artist.img.setEfects(new int[]{0x22FFFFFF,0x22FFFFFF,0x00FFFFFF});
        songName.img.setEfects(new int[]{0x66FFFFFF,0x66FFFFFF,0x00FFFFFF});

        shuffle = shuffleBtn.getFMview(context,true);
        shuffle.setRipple(true,0.3f);
        shuffle.InCenter(timeText);
        shuffle.setX(timeText.getX() - shuffle.width - Ui.cd.getHt(10));
        shuffle.setAlpha(0.4f);
        shuffle.onClick(new call(){
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
        addView(shuffle);

        reapeat = reapeatBtn.getFMview(context,true);
        reapeat.setRipple(true,0.3f);
        reapeat.InCenter(timeText);
        reapeat.setX(timeText.getX() + timeText.width +  Ui.cd.getHt(10));
        reapeat.setAlpha(0.4f);
        reapeat.onClick(new call(){
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
        addView(reapeat);
    }

    ShapeView shuffle;
    ShapeView reapeat;
    int songHt;
    int SpaceHt;
    FMText timeText;
    FMText songName;
    FMText artist;
    ITask dataLoadTask;

    void PlaylistMODE(){
        if(Ui.ef.MusicPlayer.handler.playlist.reapeat){
            reapeat.setAlpha(1);
        }else{
            reapeat.setAlpha(0.4f);
        }

        if(Ui.ef.MusicPlayer.handler.playlist.shuffle){
            shuffle.setAlpha(1);
        }else{
            shuffle.setAlpha(0.4f);
        }
    }

    void songChanged(){

        if(dataLoadTask != null){
            dataLoadTask.cancel(true);
            dataLoadTask = null;
        }

        dataLoadTask = new ITask(){
            String[] detail;
            @Override
            public void Go() {
                int AID = Ui.ef.MusicPlayer.handler.getAIDfrom(0);
                detail = audioHandler.getAudioTrackDetailById(getContext().getContentResolver(),AID);
            }

            @Override
            public void than() {
                if(detail != null){
                    Ui.ef.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            songName.img.setText(detail[0],true);
                            artist.img.setText(detail[1],true);
                            songName.invalidate();
                            artist.invalidate();

                            long Ms = Long.parseLong(detail[2]);
                            long second = (Ms / 1000) % 60;
                            long minute = (Ms / (1000 * 60)) % 60;
                            long hour = (Ms / (1000 * 60 * 60)) % 24;

                            if (hour == 0) {
                                detail[2] = String.format("%02d:%02d", minute, second);
                            } else {
                                detail[2] = String.format("%02d:%02d", hour, minute);
                            }

                            //Log.i("My","DURATIN : " + detail[2]);
                            timeText.setText(detail[2]);
                            timeText.setMargin(true, Ui.cd.getHt(30), Ui.cd.getHt(20));
                            timeText.setSqure(true, Ui.cd.getHt(13));
                            timeText.img.setColor(0x66FFFFFF);
                            timeText.rs.setColor(0x22000000);
                            timeText.InCenter(width, SpaceHt);
                            timeText.setY(threeThumb.getY() + thumbHeght + timeText.getY());
                        }
                    });
                }
            }
        };
        dataLoadTask.execute();
        counterImg = Ui.ef.MusicPlayer.handler.PID;
        changImg(0);
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
                threeThumb.ring.img.maskPaint.setColor(newColor);
            }else{
                threeThumb.ring.img.maskPaint.setColor(thumbRing.Color0);
            }

            Ui.ef.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //threeThumb.setImg(bm);
                    threeThumb.invalidate();
                    threeThumb.ring.invalidate();
                }
            });
        }
    }

    float DonwX = 0;
    float DonwY = 0;

    float FirstX;
    float SecondX;
    float ThirdX;
    float FourX;
    float FiveX;

    float FirstPoint;
    float SecondPoint;
    float ThirdPoint;
    float FourPoint;
    float FivePoint;

    @Override
    public void onDown(MotionEvent event) {
        DonwX = event.getX();
        DonwY = event.getY();
        if(Set != null){
            if(Set.isRunning()){
                Set.cancel();
                exPixle = 0;
            }
        }
        super.onDown(event);
    }

    float pointer;
    float pointVariation;
    AnimatorSet Set;
    float exPixle = 0;

    public int getColor(Bitmap bm){
        int color = thumbRing.Color0;
        if(bm != null){
            Palette palette = Palette.from(bm).generate();
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
        //Log.i("My","Color Exacted : " + color);
        return  color;
    }

    Bitmap loadBitmap(int AID){
        Bitmap bm = null;
        if(Ui.ef.MusicPlayer != null){
            musicPlayer mp = Ui.ef.MusicPlayer;
            bm = audioHandler.getAlubumArtBitmapById(Ui.ef.getContentResolver(),AID);
        }
        //Log.i("My","AID Bitmap : " + AID);
        return bm;
    }

    void changImg(int val){
        synchronized (firstThumb){
            int temlVal = counterImg + val;

            songSliderThumb.removeExtra(temlVal);
            firstThumb.setADI(temlVal-2);
            secondThumb.setADI(temlVal-1);
            threeThumb.setADI(temlVal);
            fourThumb.setADI(temlVal+1);
            fiveThumb.setADI(temlVal+2);
            counterImg = temlVal;
        }


    }

    boolean is_Cancled = false;

    @Override
    public void onUp(MotionEvent event) {

        //setClickable(false);
        int whHalf = width/2;
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        int time = 750;
        time = (int)(time / whHalf * Math.abs(whHalf - Math.abs(pointer)));
        //Log.i("My","ThirdX : " + (Math.abs(ThirdX) - ThirdPoint));
        int Mid = Ui.cd.getHt(20);

        if(pointer < 0){
            if(counterImg < Ui.ef.MusicPlayer.handler.list.size() - 1){
                //Ui.ef.MusicPlayer.handler.playNext();
                changImg(1);
                exPixle  = whHalf;
                Set.playTogether( ObjectAnimator.ofFloat(thumbSlider.this, "Slider",pointer, -whHalf));
            }else{
                Set.playTogether( ObjectAnimator.ofFloat(thumbSlider.this, "Slider",pointer, 0));
            }
        }else{
            if(counterImg > 0 && Math.abs(pointer) > Mid){
                //Ui.ef.MusicPlayer.handler.playPrevious();
               changImg(-1);
                exPixle = -whHalf;
                Set.playTogether( ObjectAnimator.ofFloat(thumbSlider.this, "Slider", pointer, whHalf));
            }else{
                Set.playTogether( ObjectAnimator.ofFloat(thumbSlider.this, "Slider", pointer, 0));
            }
        }

        pointVariation = 0;
        Set.setDuration(time).start();
        is_Cancled = false;

        Set.addListener(new animLis(){
            @Override
            public void onAnimationCancel(Animator animation) {
                is_Cancled = true;
                if(pointer > 0){
                   // DonwX += valPoint = (width / 2 - (pointer)) * 0.5f;
                }else{
                    //DonwX -= valPoint = (width / 2 - (pointer)) * 0.5f;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(is_Cancled == false){
                    Ui.ef.MusicPlayer.handler.playByNumber(counterImg);
                    valPoint = 0;
                }
                pointer = 0;
                exPixle = 0;
            }
        });
        super.onDown(event);
    }

    void setSlider(float pointer){
        int whHalf = width/2;
        int TwhHalf = thumbWidth/2;
        float variation = (Math.abs(ThirdX) - ThirdPoint);
        if(pointer < 0){
            if(pointer  <= -whHalf - variation ){
                pointer =  (-whHalf - variation ) + ((pointer + whHalf) * 0.2f);
            }
        }else{
            if(pointer  >= whHalf - variation){
                pointer = (whHalf - variation) + ((pointer - whHalf) * 0.2f);
            }
        }

        this.pointer = pointer;
        float scalVal = 0.003f;
        float Fscale = (float)(1f - (scalVal * ((100f/whHalf) * Math.abs(whHalf - ((FirstPoint + pointer  + exPixle) + TwhHalf)))));
        float Sscale = (float)(1f - (scalVal * ((100f/whHalf) * Math.abs(whHalf - ((SecondPoint + pointer + exPixle) + TwhHalf)))));
        float Tscale = (float)(1f - (scalVal * ((100f/whHalf) * Math.abs(whHalf - ((ThirdPoint + pointer + exPixle) + TwhHalf )))));
        float Frscale = (float)(1f - (scalVal * ((100f/whHalf) * Math.abs(whHalf - ((FourPoint + pointer + exPixle) + TwhHalf)))));
        float Fiscale = (float)(1f - (scalVal * ((100f/whHalf) * Math.abs(whHalf - ((FivePoint + pointer + exPixle) + TwhHalf)))));

        //Log.i("My", whHalf + " --- " + (0.003 * ((100f/whHalf) * Math.abs(whHalf - ((FirstX + pointer) + TwhHalf)))) + "");
        firstThumb.setScaleX(Fscale);
        firstThumb.setScaleY(Fscale);
        firstThumb.setX(FirstX + pointer + exPixle);

        secondThumb.setScaleX(Sscale);
        secondThumb.setScaleY(Sscale);
        secondThumb.setX(SecondX + pointer + exPixle);

        threeThumb.setScaleX(Tscale);
        threeThumb.setScaleY(Tscale);
        threeThumb.setX(ThirdX + pointer + exPixle);

        fourThumb.setScaleX(Frscale);
        fourThumb.setScaleY(Frscale);
        fourThumb.setX(FourX + pointer + exPixle);

        fiveThumb.setScaleX(Fiscale);
        fiveThumb.setScaleY(Fiscale);
        fiveThumb.setX(FiveX + pointer + exPixle);
    }

    float valPoint = 0;
    @Override
    public void onMove(MotionEvent event) {
        super.onMove(event);

            float val = (int)(event.getX() - DonwX );
            valPoint = 0;
            if(exPixle == 0){
                //val = valPoint;
                if(counterImg == Ui.ef.MusicPlayer.handler.list.size() -1 && val < 0){
                    setSlider(val * 0.2f);
                }else if(counterImg == 0 && val > 0){
                    setSlider(val * 0.2f);
                }else{
                    setSlider(val * (0.5f));
                }
            }else{
                if(counterImg == Ui.ef.MusicPlayer.handler.list.size()-1 && val < 0){
                    valPoint = (val * 0.2f);
                }else if(counterImg == 0 && val > 0){
                    valPoint = (val * 0.2f);
                }else{
                    valPoint = (val * (0.5f));
                }
            }

    }
}
