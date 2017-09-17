package Views.Home.Equalizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.linedeer.player.Ui;
import com.shape.Library.Icon.bassIcon;
import com.shape.Library.Icon.eqlizerIcon;
import com.shape.Library.Icon.normalBack;
import com.shape.Library.Icon.selectBack;
import com.shape.Library.allsong.itemRect;

import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.shapeImg;

public class btmView extends FMlyt{

    AnimatorSet Set;
    class iconView extends FMView {
        normalBack normalback;
        selectBack selback;
        public int Id = 0;
        shapeImg ShapeImg;
        public  boolean selected = false;

        public iconView(Context context, int width, int height , shapeImg img) {
            super(context, width, height);
            normalback = new normalBack(width,height,0,0);
            selback = new selectBack(width,height,0,0);
            setClickable(true);
            setRipple(true,0.4f);
            setRippleDown(false);
            ShapeImg = img;

        }

        @Override
        public void onUp(MotionEvent event) {
            super.onUp(event);
        }



        @Override
        public void setSize(int width, int height) {
            if(width != this.width || height != this.height){
                top = (height - width) / 2;
                if(normalback != null){
                    normalback.setSize(width,width);
                }
                if(selback != null){
                    selback.setSize(width,width);
                }
                if(ShapeImg != null){
                    ShapeImg.setSize(width,width);
                }
                super.setSize(width, height);
            }
        }

        int top;
        @Override
        protected void onDraw(Canvas canvas) {

                canvas.translate(0,top);
                //Log.i("My","TOP : " + top + ": " + ((height * (1 - maxScal)) / 4f));
                if(top < ((height * (1 - maxScal)) / 4f) || top == 0){
                    //selback.draw(canvas);
                }
                super.postDraw(canvas);
                if(ShapeImg != null){
                    ShapeImg.draw(canvas);
                }
                super.afterDraw(canvas,selback.S0);
                canvas.translate(0,-top);

        }
    }

    iconView sel;
    iconView[] icons = new iconView[2];
    int iconSize;
    int minPoint;

    int openMenu(float scolled){
        int No = (int)(scolled / iconSize);
        No = Math.abs(No);
        Ui.ef.clickPlay();
        return No; //libraryHome.This.openMenu(No);
    }

    public btmView(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(itemRect.Color0);

        iconSize = height - Ui.cd.getHt(10);
        minPoint = width / 2;
        for(int i = 0;i < icons.length;i++){
            shapeImg img;

            if(i == 0){
                img = new eqlizerIcon(iconSize,iconSize,0,0);
            } else {
                img = new bassIcon(iconSize,iconSize,0,0);
            }

            icons[i] = new iconView(getContext(),iconSize,height,img);
            icons[i].setY(0);
            icons[i].Id = i;
            icons[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Moved){
                        iconView bv = (iconView)v;
                        float from = scolled;
                        scolled = -(bv.Id * iconSize);
                        if(Set != null){
                            Set.cancel();
                        }
                        Set = new AnimatorSet();
                        Set.setInterpolator(Ui.cd.TH);
                        Set.playTogether(ObjectAnimator.ofFloat(btmView.this, "Scroll", from, scolled));
                        Set.setDuration(300).start();
                        openMenu(scolled);
                    }
                }
            });
            addView(icons[i]);
        }


        scolled = 0;
        setScroll(scolled);
    }

    void setPos(int Id){
        Ui.ef.clickPlay();
        float from = scolled;
        scolled = -(Id * iconSize);
        if(Set != null){
            Set.cancel();
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(ObjectAnimator.ofFloat(btmView.this, "Scroll", from, scolled));
        Set.setDuration(300).start();
    }

    float maxScal = 0.7f;
    void setScroll(float val){
        float x = 0;
        float scale = 1;
        //Log.i("My","X :" + (Math.abs(val)/iconSize));
        int Xpluse = 10;
        int nowWh = 0;
        float alpha = 0.0f;

        for(int i = 0;i < icons.length;i++){
            x = (int)(((minPoint + (i * iconSize)) + val) -(iconSize / 2f));
            scale = Math.abs((minPoint - x) - (iconSize / 2f));
            if(scale > iconSize){
                scale = iconSize;
            }
            scale = ((((100f - (((100f / iconSize) * scale))) * (1f - maxScal)) * 0.01f));
            nowWh = (int)((maxScal + scale) * iconSize);
            icons[i].setSize(nowWh,height);

            scale = Math.abs(Math.abs((minPoint - x) - (iconSize / 2f)) * (2f / width));
            if(scale > 0.8f){
                scale = 0.8f;
            }
            icons[i].setBackgroundColor(Color.argb((int) (255f*(0.8f - scale)),0xD3,0x5D,0x69));
            //Log.i("My","Alpha : " + scale);
            //0xFFD35D69
            //0xFFC53644
        }

        x = (width - iconSize) / 2f;
        x -= ((Math.abs(val) / iconSize) * ((maxScal * (iconSize + 0f))));
        for(int i = 0;i < icons.length;i++){
            icons[i].setX(x);
            x += icons[i].width + 0;
        }

       // Log.i("My","----------------------------------");
    }
    float DownX = 0;
    float DownY = 0;

    //@Override
    public void nowDown(MotionEvent event) {
        //super.onDown(event);
        DownX = event.getX();
        DownY = event.getY();
        nowScolled = 0;
        Moved = false;
    }

    float scolled;
    float nowScolled;

    //@Override
    public void nowUp(MotionEvent event) {
        //super.onUp(event);

        float from  = scolled + nowScolled;;
        if(Math.abs(nowScolled) > Ui.cd.getHt(10)){
            from = scolled + nowScolled;
            if(nowScolled > 0){
                scolled += iconSize;
            }else{
                scolled -= iconSize;
            }
            openMenu(scolled);
        }


        if(scolled > 0){
            scolled = 0;
        }else if(scolled  < -(((icons.length - 1f) * iconSize))){
            scolled = -(((icons.length - 1f) * iconSize));
        }
        int time = 0;

        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(ObjectAnimator.ofFloat(btmView.this, "Scroll", from, scolled));
        Set.setDuration(150).start();
    }


    //@Override
    boolean Moved = false;
    public void nowMove(MotionEvent event) {
        //super.onMove(event);
        if(Math.abs((event.getX() - DownX)) > 10){
            Moved = true;
        }
        nowScolled = (event.getX() - DownX) * 0.5f;
        if(Math.abs(nowScolled) >= iconSize){
            if(nowScolled < 0){
                nowScolled = -iconSize;
            }else{
                nowScolled = iconSize;
            }
        }
        if(scolled + nowScolled > 0){
            nowScolled = 0;
            setScroll(0);
        }else if(scolled + nowScolled < -(((icons.length - 1f) * iconSize))){
            nowScolled = 0;
            setScroll(-(((icons.length - 1f) * iconSize)));
        }else{
            setScroll(scolled + nowScolled);
        }
    }



    public boolean dispatchTouchEvent(MotionEvent event) {
        //Log.i("My","event.ACTION : " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                nowMove(event);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                //onOut(event);
                break;
            case MotionEvent.ACTION_UP:
                nowUp(event);
                if(Moved){
                    return  false;
                }
                break;
            default:

                //onleave(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
