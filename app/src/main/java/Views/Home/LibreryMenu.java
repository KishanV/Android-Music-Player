package Views.Home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;

import com.linedeer.player.Ui;

import Views.Home.Menu.Home;
import Views.api.FMlyt;
import Views.api.animLis;


public class LibreryMenu extends FMlyt {

    Home home;
    public LibreryMenu(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundResource(0);
        home = new Home(getContext(),width - Ui.cd.getHt(40),height - Ui.cd.getHt(20));
        home.setX(Ui.cd.getHt(20));
        addView(home);
        setSize(width,home.height + Ui.cd.getHt(20));
    }

    AnimatorSet Set;
    public boolean isOpen = false;
    public void open(int to, final boolean state){
        int time = (int)(5f * (100f  / height * Math.abs(to - getY() )));
        //Log.i("My","TIME : " + time);
        if(Set != null){
            Set.cancel();
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(time);
        if(!state){
            Set.playTogether(
                    ObjectAnimator.ofInt(LibreryMenu.this, "Openmove",(int)getY(), to)
            );
        }else{
            Set.playTogether(
                    ObjectAnimator.ofInt(LibreryMenu.this, "Openmove",(int)getY(), to)
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

    public void close(){
        setY(Ui.cd.DPH);
        isOpen = false;
    }

    void stopAnim(){
        Set.cancel();
        isOpen = false;
    }

    public void setOpenmove(int point) {
        setY(point);
    }
}
