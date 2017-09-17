package Views.Home;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.linedeer.api.EventCall;
import com.linedeer.player.Ui;
import com.player.playerEvents;
import com.shape.home.mainBtnBack;
import com.shape.home.slider.thumbBack;
import com.shape.home.songslider.pauseImg;
import com.shape.home.songslider.playImg;

import java.util.Timer;
import java.util.TimerTask;

import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class songSlider extends FMlyt {


    radiusSqure RadiusSqure;
    FMText timeText;
    FMView Squre;
    int margin;
    ShapeView playBtn;
    ShapeView pauseBtn;

    public songSlider(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00FFFFFF);

        Squre = new FMView(getContext(),width, Ui.cd.getHt(2));
        Squre.setY(height/2 - Ui.cd.getHt(1));
        Squre.setBackgroundColor(thumbBack.Color0);
        addView(Squre);
        //Squre.setColor(0xFF413964);

        margin = Ui.cd.getHt(14);
        timeText = textImg.getFMText(getContext(),"10:20", Ui.cd.getHt(14));
        timeText.minWidth = Ui.cd.getHt(40);
        timeText.img.setColor(0xFFFFFFFF);
        timeText.setText("00:00");
        timeText.setMargin(true, Ui.cd.getHt(24),height - timeText.height);
        timeText.setSqure(true,(height - timeText.height / 2), mainBtnBack.Color0);
        timeText.setX(margin);
        timeText.setClickable(false);
        addView(timeText);

        playBtn = playImg.getFMview(getContext(),false);
        playBtn.setSize(height,height);
        addView(playBtn);

        pauseBtn = pauseImg.getFMview(getContext(),false);
        pauseBtn.setSize(height,height);
        addView(pauseBtn);

        playBtn.setVisibility(GONE);
        pauseBtn.setVisibility(GONE);

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.SONG_CHANGED,playerEvents.PLAYING_FLIP, Ui.ef.Event_onBind, Ui.ef.Event_onPause, Ui.ef.Event_onResume, Ui.ef.Event_onDestroy}){
            @Override
            public void onCall(int eventId) {
                songChanged(eventId);
            }
        });
        caculateSeek();
    }


    boolean btns = false;
    public void setBtns(boolean btns) {
        this.btns = true;
        playBtn.setVisibility(GONE);
        pauseBtn.setVisibility(VISIBLE);
        playBtn.setClickable(false);
        pauseBtn.setClickable(false);
        timeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Moved){
                    if(Ui.ef.MusicPlayer != null &&  Ui.ef.MusicPlayer.handler != null && Ui.ef.MusicPlayer.handler.mediaplayer != null){
                        Ui.ef.clickPlay();
                        Ui.ef.MusicPlayer.handler.flipPlaying();
                    }
                }
            }
        });
    }

    int crtVal;
    float valPer;
    String strTime;
    void caculateSeek(){

         if(!moved && Ui.ef.MusicPlayer != null &&  Ui.ef.MusicPlayer.handler != null && Ui.ef.MusicPlayer.handler.mediaplayer != null){
             crtVal = 1;
             valPer = 0.0f;
             try {
                 if(Ui.ef.MusicPlayer.handler.mediaplayer.getDuration() != -1 && Ui.ef.MusicPlayer.handler.songPrepared){
                     crtVal = Ui.ef.MusicPlayer.handler.mediaplayer.getCurrentPosition();
                     valPer = (100f / Ui.ef.MusicPlayer.handler.mediaplayer.getDuration() * crtVal) * 0.01f;
                 }

             } catch (Exception e){ }

             if(Double.isNaN(valPer)){
                 valPer = 0;
             }

             long Ms = crtVal;
             long second = (Ms / 1000) % 60;
             long minute = (Ms / (1000 * 60)) % 60;
             long hour = (Ms / (1000 * 60 * 60)) % 24;

             if (hour == 0) {
                 strTime = String.format("%02d:%02d", minute, second);
             } else {
                 strTime = String.format("%02d:%02d", hour, minute);
             }

            //Log.i("My","IS PLAYING : " + strTime);
            Ui.ef.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(btns){
                        if(!Ui.ef.MusicPlayer.handler.isPlaying()){
                            playBtn.setVisibility(VISIBLE);
                            pauseBtn.setVisibility(GONE);
                        }else{
                            playBtn.setVisibility(GONE);
                            pauseBtn.setVisibility(VISIBLE);
                        }
                    }

                    setViewSeek((int) (margin + ((songSlider.this.width - ((margin * 2) + timeText.width)) * valPer)),strTime);
                    //Log.i("My","valPer : " + valPer);
                }
            });
        }else  if(moved && Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler.mediaplayer != null){
             try {
                 if(Math.abs(lastPerX - PerX) > 0.01f){
                     lastPerX = PerX;
                     Ui.ef.MusicPlayer.handler.mediaplayer.seekTo((int) (Ui.ef.MusicPlayer.handler.mediaplayer.getDuration() * PerX));
                 }
             }catch (Exception ex){}
        }else{
             Ui.ef.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     setViewSeek(margin,"00:00");
                     if(btns){
                         playBtn.setVisibility(GONE);
                         pauseBtn.setVisibility(VISIBLE);
                     }
                 }
             });
         }
    }

    void setViewSeek(int x,String str){
        if(btns){
            str = "    ";
        }
        if(!str.equals(timeText.img.Text)){
            timeText.setText(str);
            timeText.setMargin(true, Ui.cd.getHt(24),height - timeText.height);
            timeText.setSqure(true,(height - timeText.height / 2), mainBtnBack.Color0);
            timeText.invalidate(timeText.getLeft(),timeText.getTop(),timeText.getRight(),timeText.getBottom());
        }
        if(x < Ui.cd.getHt(10)){
            timeText.setX(Ui.cd.getHt(10));
        }else if(x > width - Ui.cd.getHt(10) - timeText.width){
            timeText.setX(width - Ui.cd.getHt(10) - timeText.width);
        }else{
            timeText.setX(x);
        }
        playBtn.InCenter(timeText);
        pauseBtn.InCenter(timeText);
    }


    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ONDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                ONMove(event);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                //onOut(event);
                break;
            case MotionEvent.ACTION_UP:
                ONUp(event);
                break;
            default:
                //onleave(event);
        }
        return super.dispatchTouchEvent(event);
    }

    int DownX;
    float PerX;
    float lastPerX;

    boolean moved = false;
    boolean Moved = false;

    public void ONDown(MotionEvent event) {
        Ui.ef.MusicPlayer.handler.forScrollDown();
        Moved = false;
        DownX = (int) event.getX();
    }


    public void ONUp(MotionEvent event) {
        Ui.ef.MusicPlayer.handler.forScrollUp();
        if(moved){
            moved = false;
            if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler.mediaplayer != null){
                try {
                    Ui.ef.MusicPlayer.handler.mediaplayer.seekTo((int) (Ui.ef.MusicPlayer.handler.mediaplayer.getDuration() * PerX));
                }catch (Exception ex){

                }
            }
            caculateSeek();
            if(!Ui.ef.MusicPlayer.handler.isPlaying()){
                Ui.ef.MusicPlayer.handler.flipPlaying();
            }
        }
    }

    public void ONMove(MotionEvent event) {
        int val = (int) event.getX();
        if(moved || Math.abs(val - DownX) > Ui.cd.getHt(10)){
            Moved = true;
            moved = true;
            if(val < (margin + timeText.width/2)){
                val = (margin + timeText.width/2) ;
            }else if(val > width - (margin + timeText.width/2)){
                val = width - (margin + timeText.width / 2);
            }
            timeText.setX(val - timeText.width / 2);
            playBtn.InCenter(timeText);
            pauseBtn.InCenter(timeText);
            PerX = ((100f / (width - ((margin * 2f) + timeText.width))) * (val - (margin + timeText.width/2f))) * 0.01f;
        }
    }

    Timer tm;
    private void songChanged(int eventId) {
        if(tm != null){
            tm.cancel();
            tm.purge();
        }
        caculateSeek();
        if(Ui.ef.Event_onPause != eventId && Ui.ef.Event_onDestroy != eventId){
            if(Ui.ef.MusicPlayer != null){
                if(Ui.ef.MusicPlayer.handler!= null){
                    if(Ui.ef.MusicPlayer.handler.isPlaying()){
                        tm = new Timer();
                        tm.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                caculateSeek();
                            }
                        },0,500);
                    }
                }
            }
        }
    }

}
