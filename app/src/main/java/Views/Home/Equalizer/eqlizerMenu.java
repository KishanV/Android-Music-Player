package Views.Home.Equalizer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;

import com.linedeer.player.Ui;


import Views.ContentHome;
import Views.api.FMlyt;
import Views.api.animLis;


public class eqlizerMenu extends FMlyt {

    Home home;


    public eqlizerMenu(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundResource(0);
        home = new Home(getContext(),width - Ui.cd.getHt(40),height - Ui.cd.getHt(20));
        home.setX(Ui.cd.getHt(20));
        addView(home);
        setSize(width,home.height + Ui.cd.getHt(20));
    }

    public AnimatorSet Set;
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
                    ObjectAnimator.ofInt(eqlizerMenu.this, "Openmove",(int)getY(), to)
            );
        }else{
            Set.playTogether(
                    ObjectAnimator.ofInt(eqlizerMenu.this, "Openmove",(int)getY(), to)
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

    @Override
    public void setY(float y) {
        super.setY(y);
        float alpha = (float) Math.abs(((100f / (height)) * ((Ui.cd.DPH - height) - Math.abs(y))) * 0.01);

        if(alpha < 0.50f){
            alpha = 0.50f;
        }

        Log.i("My","alpha : " + alpha);
        if(y >= Ui.cd.DPH){

            ContentHome.This.MainHome.setAlpha(1,false);
            ContentHome.This.MainHome.removeCatch();

        }else if(alpha == 0.50f){

            ContentHome.This.MainHome.setAlpha(alpha,true);
            ContentHome.This.MainHome.drawCatch();

        }else{

            ContentHome.This. MainHome.setAlpha(alpha,false);
            ContentHome.This.MainHome.drawCatch();

        }
    }

    public void stopAnim(){
        Set.cancel();
        isOpen = false;
    }

    public void setOpenmove(int point) {
        setY(point);
    }


}
