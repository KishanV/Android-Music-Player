package Views.Home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;

import com.linedeer.player.Ui;

import Views.Home.QuickEq.Main;
import Views.api.FMlyt;
import Views.api.animLis;


public class QuickEqlizer extends FMlyt {

    public QuickEqlizer(Context context, int width, int height) {
        super(context, width, height);
        //setBackgroundColor(backgroundImg.Color0);
        setBackgroundResource(0);
        addView(new Main(context,width,height));
    }

    AnimatorSet Set;
    public boolean isOpen = false;
    public void open(int to, final boolean state){
        int time = (int)(5f * (100f  / width * Math.abs(to - getX() )));
        Log.i("My","TIME : " + time);
        if(Set != null){
            Set.cancel();
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(time);
        if(!state){
            Set.playTogether(
                    ObjectAnimator.ofInt(QuickEqlizer.this, "Openmove",(int)getX(), to)
            );
        }else{
            Set.playTogether(
                    ObjectAnimator.ofInt(QuickEqlizer.this, "Openmove",(int)getX(), to)
            );
        }
        isOpen = state;
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        Set.start();
    }

    void stopAnim(){
        Set.cancel();
        isOpen = false;
    }

    public void setOpenmove(int point) {
        setX(point);
    }
}
