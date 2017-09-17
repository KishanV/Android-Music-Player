package Views.Home.PlayList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;

import com.linedeer.player.Ui;
import com.shape.home.backgroundImg;

import Views.Home.PlayList.PlayListClass.Screen;
import Views.api.FMlyt;
import Views.api.animLis;

public class playLIstHome extends FMlyt{

    AnimatorSet Set;
    Screen screen;
    public playLIstHome(Context context, int width, int height) {
        super(context, width, height);
        setEnableCatch();
        setBackgroundColor(backgroundImg.Color0);
        screen = new Screen(this);
    }

    public boolean isOpen = false;
    public void open(int to, final boolean state){
        int time = (int)(5f * (100f  / width * Math.abs(to - getX() )));
        //Log.i("My","TIME : " + time);
        if(Set != null){
            Set.cancel();
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(time);
        if(!state){
            Set.playTogether(
                    ObjectAnimator.ofInt(playLIstHome.this, "Openmove",(int)getX(), to)
            );
        }else{
            Set.playTogether(
                    ObjectAnimator.ofInt(playLIstHome.this, "Openmove",(int)getX(), to)
            );
        }
        isOpen = state;
        screen.isAction(state);
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                screen.animEnd(state);
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
