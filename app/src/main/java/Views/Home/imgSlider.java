package Views.Home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ImageView;

import com.linedeer.api.EventCall;
import com.linedeer.api.ITask;
import com.linedeer.player.Ui;
import com.player.audioHandler;
import com.player.playerEvents;
import com.shape.Library.allsong.itemBack;

import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.animLis;

public class imgSlider extends FMlyt{

    ImageView top;
    ImageView btm;
    FMView FMTop;
    public VelocityTracker Vx;
    public imgSlider(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(itemBack.Color0);
        Vx = VelocityTracker.obtain();

        btm = creatImg();
        addView(btm);

        top = creatImg();
        addView(top);

        FMTop = new FMView(getContext(),width,height);
        FMTop.setBackgroundColor(0x66000000);
        FMTop.setClickable(false);
        addView(FMTop);

        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.SONG_CHANGED,playerEvents.PLAYLIST_CHANGED, Ui.ef.Event_onBind}){
            @Override
            public void onCall(int eventId) {
                if(eventId == playerEvents.SONG_CHANGED){
                    songChanged();
                }

                if(eventId == Ui.ef.Event_onBind || playerEvents.PLAYLIST_CHANGED == eventId){
                    if(Ui.ef.MusicPlayer.handler.list!= null){
                        songChanged();
                    }
                }
            }
        });
    }

    ImageView creatImg(){
        ImageView top = new ImageView(getContext());
        top.setLayoutParams(new LayoutParams(width,width));
        top.setY(height - width);
        top.setBackgroundColor(0xFF999999);
        top.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return top;
    }

    Bitmap BM;
    public void songChanged(){
        ITask task = new ITask(){
            Bitmap bm;
            @Override
            public void Go() {
                int AID = Ui.ef.MusicPlayer.handler.getAIDfrom(0);
                BM = bm = audioHandler.getAlubumArtBitmapById(getContext().getContentResolver(),AID);
            }

            @Override
            public void than() {
                Ui.ef.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        top.setImageBitmap(bm);
                        btm.setImageBitmap(bm);
                    }
                });
            }
        };
        task.execute();
        ///btm.setImageBitmap();
    }

    int DX;
    int DY;

    @Override
    public void onDown(MotionEvent event) {
        super.onMove(event);
        Vx.clear();
        Vx.addMovement(event);
        DX = (int) event.getX();
        DY = (int) event.getY();
    }

    @Override
    public void onMove(MotionEvent event) {
        super.onMove(event);
        Vx.addMovement(event);
        top.setX(event.getX() - DX);
    }

    @Override
    public void onUp(MotionEvent event) {
        super.onMove(event);
        Vx.computeCurrentVelocity(100);
        float valx = Vx.getXVelocity(0);
        Vx.clear();
        if((int)Math.abs(valx) < 12){
            //top.setX(0);
            Play(0);
        }else if(valx > 0){
            Play(width);
            //top.setX(width - 200);
        }else{
            Play(-width);
            //top.setX(-(width - 200));
        }
    }

    AnimatorSet Set;
    void Play(int val){
        final ImageView iv = top;
        top = btm;
        btm = creatImg();
        btm.setImageBitmap(BM);
        addView(btm,0);
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(ObjectAnimator.ofFloat(iv, "X",val));
        Set.setDuration(500).start();
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                imgSlider.this.removeView(iv);
            }
        });
    }
}
