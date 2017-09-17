package Views.Home.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.linedeer.api.EventCall;
import com.linedeer.player.Ui;
import com.player.playerEvents;
import com.shape.home.mainBtnBack;
import com.shape.home.mainBtnPlaying;
import com.shape.home.mainBtnStoped;
import Views.api.FMlyt;

public class playBtn extends FMlyt{

    mainBtnBack PlayBtn;
    mainBtnPlaying playing;
    mainBtnStoped stoped;
    boolean isPlaying = false;

    public playBtn(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00FFFFFF);
        PlayBtn = new mainBtnBack(width,height,0,0);
        playing = new mainBtnPlaying(width,height,0,0);
        stoped = new mainBtnStoped(width,height,0,0);
        setRipple(true);

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{Ui.ef.Event_onBind, playerEvents.PLAYING_FLIP,playerEvents.SONG_CHANGED}){
            @Override
            public void onCall(int eventId) {
                isPlaying = Ui.ef.MusicPlayer.handler.isPlaying();
                playBtn.this.invalidate();
                //Log.i("My","isPlaying : " + isPlaying);
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.ef.MusicPlayer.handler.flipPlaying();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        PlayBtn.draw(canvas);
        super.postDraw(canvas);
        if(isPlaying){
            playing.draw(canvas);
        } else {
            stoped.draw(canvas);
        }
        super.afterDraw(canvas,PlayBtn.S0);
    }
}
