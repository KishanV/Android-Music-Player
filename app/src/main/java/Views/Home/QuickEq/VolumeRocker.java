package Views.Home.QuickEq;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.view.MotionEvent;

import com.linedeer.api.EventCall;
import com.linedeer.player.Ui;
import com.player.playerEvents;
import com.shape.home.QEq.titleBack;
import com.shape.home.QEq.volumRockerBack;
import com.shape.home.equalizer.volumeIcon;
import com.shape.home.equalizer.volumeIconBack;

import Views.api.FMView;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class VolumeRocker extends FMView {
    radiusSqure rs;
    radiusSqure volumback;
    volumeIcon vIcon;
    textImg text;

    public VolumeRocker(Context context, int width, int height) {
        super(context, width, height);
        squre sq = new squre(width, Ui.cd.getHt(2), 0, 0);
        sq.setColor(titleBack.Color0);
        sq.InCenter(width, height);
        addShape(sq);

        rs = new radiusSqure(Ui.cd.getHt(60), height * 0.5f, 0, 0, Ui.cd.getHt(13));
        rs.setColor(volumRockerBack.Color0);
        //addShape(rs);

        volumback = new radiusSqure((rs.height) - Ui.cd.getHt(4), (rs.height) - Ui.cd.getHt(4), 0, 0, Ui.cd.getHt(11));
        volumback.setColor(volumeIconBack.Color0);
        volumback.setX((int) rs.x + Ui.cd.getHt(2));
        volumback.setY((int) rs.x + Ui.cd.getHt(2));
        // addShape(volumback);

        vIcon = new volumeIcon((int) volumback.width, (int) volumback.width, 0, 0);
        vIcon.InCenter(volumback);

        text = new textImg((int) (rs.width - volumback.width), (int) rs.height, (int) vIcon.height, 0, Ui.cd.getHt(14));
        text.setColor(volumeIcon.Color0);
        text.setText("8", true);

        updateVoulume();

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{Ui.ef.Event_onBind, playerEvents.VOLUME_CHANGE}){
            @Override
            public void onCall(int eventId) {
                if(!isDown){
                    updateVoulume();
                }
            }
        });
    }

    int left = Ui.cd.getHt(10);
    boolean isDown = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(left , (height - rs.height) / 2f);
        //text.setText(left + "",true);
        rs.draw(canvas);
        volumback.draw(canvas);
        vIcon.draw(canvas);
        text.draw(canvas);
    }

    @Override
    public void onDown(MotionEvent event) {
        isDown = true;
    }

    @Override
    public void onUp(MotionEvent event) {
        isDown = false;
    }

    @Override
    public void onMove(MotionEvent event) {
        super.onMove(event);
        int x = (int) event.getX() - Ui.cd.getHt(30);
        setPoint(x);
        setVolume(x);
    }

    private void setVolume(int x) {
        AudioManager audio = (AudioManager) Ui.ef.getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        int vol = (int) (audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / ((float)width - Ui.cd.getHt(80))  * (x - Ui.cd.getHt(10)));
        audio.setStreamVolume( AudioManager.STREAM_MUSIC, vol, 0);
        if(vol < 0){
            vol = 0;
        }else if(vol > audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)){
            vol = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        text.setText(vol+"",true);

    }


    void setPoint(int x){
        if(x < Ui.cd.getHt(10)){
            left = Ui.cd.getHt(10);
        }else if(x > width - Ui.cd.getHt(10 + 60)){
            left = width - Ui.cd.getHt(10 + 60);
        }else{
            left = x;
        }
        invalidate();
    }

    void updateVoulume(){
        AudioManager audio = (AudioManager) Ui.ef.getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVol =  (width - Ui.cd.getHt(80)) / audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)  * currentVolume;
        text.setText(currentVolume+"",true);
        setPoint(maxVol);
    }
}
