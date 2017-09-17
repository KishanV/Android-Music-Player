package Views.Home.Equalizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.linedeer.player.Ui;
import com.shape.home.equlizer.EqDot;
import com.shape.home.equlizer.EqStick;
import com.shape.home.equlizer.bassBack;
import com.shape.home.equlizer.bassDot;
import com.shape.home.equlizer.bassTop;
import com.shape.home.equlizer.mainBackground;
import com.shape.home.equlizer.subtitleBackground;
import com.shape.home.equlizer.subtitleDot;
import com.shape.home.equlizer.titleBackground;

import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.shapeImg;
import Views.textImg;


public class eqlizerMain extends FMlyt {

    AnimatorSet Set;
    public eqlizerMain(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(mainBackground.Color0);
        setPivotY(height*0.7f);
        setPivotX(width*0.5f);
        Ui.ef.clickPlay();
        setAlpha(0);
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(200);
        Set.playTogether(
                ObjectAnimator.ofFloat(this, "Y",height * 0.5f, 0),
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );
        Set.start();
        init();
    }



    boolean isScrollable = false;
    ScrollView sc;
    FMlyt main;
    private void init() {

        sc = new ScrollView(getContext());
        sc.setLayoutParams(new FrameLayout.LayoutParams(Ui.cd.DPW+ Ui.cd.DPW, Ui.cd.DPH));
        sc.setBackgroundColor(0x00FFFFFF);
        addView(sc);

        main = new FMlyt(getContext(), Ui.cd.DPW, Ui.cd.DPH);
        main.setBackgroundColor(0x00000000);
        sc.addView(main,new ScrollView.LayoutParams(Ui.cd.DPW, Ui.cd.DPH));

        FMView back = new FMView(getContext(), Ui.cd.DPW, Ui.cd.DPH+ Ui.cd.DPH);
        back.setLayerType(LAYER_TYPE_SOFTWARE,null);
        main.addView(back);

        ShapeView titleback = titleBackground.getFMview(getContext(),false);
        titleback.setSize(Ui.cd.DPW, Ui.cd.getHt(60));
        main.addView(titleback);



        FMText titleText = textImg.getFMText(getContext() ,"AUDIO EQULIZERS", Ui.cd.getHt(18));
        titleText.img.setColor(0x66FFFFFF);
        titleText.InCenter(Ui.cd.DPW, Ui.cd.getHt(60));
        main.addView(titleText);

        int top = Ui.cd.getHt(60);
        top = bassFn(top);
        top = verchulizerFn(top);
        top = eqlizerFn(top);
        back.setSize(back.width,top);
    }

    float XX = 0;
    float YY = 0;
    int bassFn(int top){

        ShapeView bassTitile = subtitleBackground.getFMview(getContext(),false);
        bassTitile.setSize(Ui.cd.DPW, Ui.cd.getHt(50));
        bassTitile.setY(top);
        main.addView(bassTitile);

        ShapeView Dot = subtitleDot.getFMview(getContext(),false);
        Dot.InCenter(Ui.cd.getHt(25), Ui.cd.getHt(50));
        Dot.setY(Dot.getY() + top);
        main.addView(Dot);

        FMText titleText = textImg.getFMText(getContext() ,"BOOSTERS", Ui.cd.getHt(16));
        titleText.InCenter(0, Ui.cd.getHt(50));
        titleText.img.setColor(0x99FFFFFF);
        titleText.setY(titleText.getY() + top);
        titleText.setX(Ui.cd.getHt(25));
        main.addView(titleText);

        final shapeImg bass = bassBack.getFMview(getContext(),false).img;
        final shapeImg basstop = bassTop.getFMview(getContext(),false).img;
        final shapeImg bassdot = bassDot.getFMview(getContext(),false).img;
        //addView(bass);

        XX = (int)(bass.width + Ui.cd.getHt(12)) / 2;
        YY = (int)(bass.width + Ui.cd.getHt(12)) / 2;

        FMView bassFm = new FMView(getContext(),(int)bass.width + Ui.cd.getHt(12),(int)bass.height + Ui.cd.getHt(12)){
            int val = 0;

            float X2 = 0;
            float Y2 = 0;
            int Dis = 0;
            int angle = 0;
            int lastAngle = 0;
            int StopAngle = 220;
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.save();
                canvas.translate(Ui.cd.getHt(6), Ui.cd.getHt(6));
                bass.draw(canvas);
                float radius = 10;
                final RectF oval = new RectF();
                oval.set(0, 0, bass.width, bass.height);
                Path ph = new Path();
                ph.setFillType(Path.FillType.WINDING);
                ph.moveTo(bass.width/2, bass.width/2);
                ph.addArc(oval,-(200),angle);
                ph.lineTo(bass.width/2,bass.width/2);

                canvas.clipPath(ph);
                basstop.draw(canvas);
                canvas.restore();
                canvas.rotate(-(90+20),((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                canvas.rotate(angle,((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                bassdot.draw(canvas);
                //canvas.drawPath(ph,bass.img.maskPaint);
            }


            @Override
            public void onDown(MotionEvent event) {
                super.onDown(event);
                sc.requestDisallowInterceptTouchEvent(true);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                Dis = val;
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                sc.requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                lastAngle = angle;

                angle += val - Dis;
                if (angle < 0) {
                    angle += 360;
                }
                if (angle > 360) {
                    angle -= (int) (angle / 360f) * 360;
                }
                if(angle > StopAngle && angle < 360){
                    if(lastAngle > StopAngle/2f){
                        angle = StopAngle;
                    }else{
                        angle = 0;
                    }
                }
                Dis = val;
                Log.i("My","Rotation : " + angle);
                invalidate();
            }
        };
        bassFm.setX(100);
        bassFm.InCenter(main.width / 2f,0);
        bassFm.setY(Ui.cd.getHt(50 + 30) + top);
        bassFm.setX(bassFm.getX() + (main.width * 0.03f));
        main.addView(bassFm);

        FMText bassText = textImg.getFMText(getContext(),"BASS", Ui.cd.getHt(14));
        bassText.InCenter(bassFm);
        bassText.img.setColor(0x99FFFFFF);
        main.addView(bassText);



        FMView trebale = new FMView(getContext(),(int)bass.width + Ui.cd.getHt(12),(int)bass.height + Ui.cd.getHt(12)){

            int val = 0;
            float X2 = 0;
            float Y2 = 0;
            int Dis = 0;
            int angle = 0;
            int lastAngle = 0;
            int StopAngle = 220;
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.save();
                canvas.translate(Ui.cd.getHt(6), Ui.cd.getHt(6));
                bass.draw(canvas);
                float radius = 10;
                final RectF oval = new RectF();
                oval.set(0, 0, bass.width, bass.height);
                Path ph = new Path();
                ph.setFillType(Path.FillType.WINDING);
                ph.moveTo(bass.width/2, bass.width/2);
                ph.addArc(oval,-(200),angle);
                ph.lineTo(bass.width/2,bass.width/2);

                canvas.clipPath(ph);
                basstop.draw(canvas);
                canvas.restore();
                canvas.rotate(-(90+20),((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                canvas.rotate(angle,((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                bassdot.draw(canvas);
                //canvas.drawPath(ph,bass.img.maskPaint);
            }


            @Override
            public void onDown(MotionEvent event) {
                super.onDown(event);
                sc.requestDisallowInterceptTouchEvent(true);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                Dis = val;
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                sc.requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                lastAngle = angle;

                angle += val - Dis;
                if (angle < 0) {
                    angle += 360;
                }
                if (angle > 360) {
                    angle -= (int) (angle / 360f) * 360;
                }
                if(angle > StopAngle && angle < 360){
                    if(lastAngle > StopAngle/2f){
                        angle = StopAngle;
                    }else{
                        angle = 0;
                    }
                }
                Dis = val;
                Log.i("My","Rotation : " + angle);
                invalidate();
            }
        };
        trebale.InCenter(main.width / 2f,0);
        trebale.setX((trebale.getX() + main.width / 2f) - (main.width * 0.03f));
        trebale.setY(Ui.cd.getHt(50 + 30) + top);
        main.addView(trebale);

        FMText trebleText = textImg.getFMText(getContext(),"TREBLE", Ui.cd.getHt(14));
        trebleText.InCenter(trebale);
        trebleText.img.setColor(0x99FFFFFF);
        main.addView(trebleText);

        top = (int)(trebale.getY() + trebale.height + Ui.cd.getHt(33));
        return top;
    }
    int eqlizerFn(int top){

        ShapeView bassTitile = subtitleBackground.getFMview(getContext(),false);
        bassTitile.setSize(Ui.cd.DPW, Ui.cd.getHt(50));
        bassTitile.setY(top);
        main.addView(bassTitile);

        ShapeView Dot = subtitleDot.getFMview(getContext(),false);
        Dot.InCenter(Ui.cd.getHt(25), Ui.cd.getHt(50));
        Dot.setY(Dot.getY() + top);
        main.addView(Dot);

        FMText titleText = textImg.getFMText(getContext() ,"10 BAND EQUALIZER", Ui.cd.getHt(16));
        titleText.InCenter(0, Ui.cd.getHt(50));
        titleText.img.setColor(0x99FFFFFF);
        titleText.setY(titleText.getY() + top);
        titleText.setX(Ui.cd.getHt(25));
        main.addView(titleText);

        final shapeImg bass = bassBack.getFMview(getContext(),false).img;
        final shapeImg basstop = bassTop.getFMview(getContext(),false).img;
        final shapeImg bassdot = bassDot.getFMview(getContext(),false).img;
        //addView(bass);


        int x = 0;
        float space = (Ui.cd.DPW / 11f);
        String[] Bands = new String[]{"31","62","125","250","500","1K","2K","4K","8K","16K"};
        //31 62 125 250 500 1k 2k 4k 8k 16k

        FMText text = null;
        for(int i = 0;i < 10;i++){
            ShapeView stick = EqStick.getFMview(getContext(),false);
            ShapeView btn = EqDot.getFMview(getContext(),false);
            btn.InCenter(0,stick.height);
            main.addView(stick);
            main.addView(btn);
            stick.setX((i * space) + space - 1);
            btn.setX(((i * space) + space - 1) - (Ui.cd.getHt(5)));
            stick.setY(top + Ui.cd.getHt(50 + 30));
            btn.setY(btn.getY() + top + Ui.cd.getHt(50 + 30));
            x += stick.width;

              text = textImg.getFMText(getContext(),Bands[i], Ui.cd.getHt(14));
            text.InCenter(stick.width,0);
            text.img.setColor(0x99FFFFFF);
            text.setY(stick.getY() + stick.height + Ui.cd.getHt(15));
            text.setX(stick.getX() - ((text.width) / 2f) + (stick.width / 2f));
            main.addView(text);
        }
        return   (int)text.getY() + text.height + Ui.cd.getHt(30);
    }
    int verchulizerFn(int top){

        ShapeView bassTitile = subtitleBackground.getFMview(getContext(),false);
        bassTitile.setSize(Ui.cd.DPW, Ui.cd.getHt(50));
        bassTitile.setY(top);
        main.addView(bassTitile);

        ShapeView Dot = subtitleDot.getFMview(getContext(),false);
        Dot.InCenter(Ui.cd.getHt(25), Ui.cd.getHt(50));
        Dot.setY(Dot.getY() + top);
        main.addView(Dot);

        FMText titleText = textImg.getFMText(getContext() ,"MUSIC ENHANCER", Ui.cd.getHt(16));
        titleText.InCenter(0, Ui.cd.getHt(50));
        titleText.img.setColor(0x99FFFFFF);
        titleText.setY(titleText.getY() + top);
        titleText.setX(Ui.cd.getHt(25));
        main.addView(titleText);

        final shapeImg bass = bassBack.getFMview(getContext(),false).img;
        final shapeImg basstop = bassTop.getFMview(getContext(),false).img;
        final shapeImg bassdot = bassDot.getFMview(getContext(),false).img;
        //addView(bass);

        XX = (int)(bass.width + Ui.cd.getHt(12)) / 2;
        YY = (int)(bass.width + Ui.cd.getHt(12)) / 2;

        FMView bassFm = new FMView(getContext(),(int)bass.width + Ui.cd.getHt(12),(int)bass.height + Ui.cd.getHt(12)){
            int val = 0;

            float X2 = 0;
            float Y2 = 0;
            int Dis = 0;
            int angle = 0;
            int lastAngle = 0;
            int StopAngle = 220;
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.save();
                canvas.translate(Ui.cd.getHt(6), Ui.cd.getHt(6));
                bass.draw(canvas);
                float radius = 10;
                final RectF oval = new RectF();
                oval.set(0, 0, bass.width, bass.height);
                Path ph = new Path();
                ph.setFillType(Path.FillType.WINDING);
                ph.moveTo(bass.width/2, bass.width/2);
                ph.addArc(oval,-(200),angle);
                ph.lineTo(bass.width/2,bass.width/2);

                canvas.clipPath(ph);
                basstop.draw(canvas);
                canvas.restore();
                canvas.rotate(-(90+20),((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                canvas.rotate(angle,((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                bassdot.draw(canvas);
                //canvas.drawPath(ph,bass.img.maskPaint);
            }


            @Override
            public void onDown(MotionEvent event) {
                super.onDown(event);
                sc.requestDisallowInterceptTouchEvent(true);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                Dis = val;
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                sc.requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                lastAngle = angle;

                angle += val - Dis;
                if (angle < 0) {
                    angle += 360;
                }
                if (angle > 360) {
                    angle -= (int) (angle / 360f) * 360;
                }
                if(angle > StopAngle && angle < 360){
                    if(lastAngle > StopAngle/2f){
                        angle = StopAngle;
                    }else{
                        angle = 0;
                    }
                }
                Dis = val;
                Log.i("My","Rotation : " + angle);
                invalidate();
            }
        };
        bassFm.setX(100);
        bassFm.InCenter(main.width / 3f,0);
        bassFm.setY(Ui.cd.getHt(50 + 30) + top);
        bassFm.setX(bassFm.getX() + (main.width * 0.035f));
        bassFm.setScaleX(0.7f);
        bassFm.setScaleY(0.7f);
        main.addView(bassFm);

        FMText bassText = textImg.getFMText(getContext(),"VIRTUALIZER", Ui.cd.getHt(12));
        bassText.InCenter(bassFm);
        bassText.img.setColor(0x99FFFFFF);
        main.addView(bassText);

        FMView trebale = new FMView(getContext(),(int)bass.width + Ui.cd.getHt(12),(int)bass.height + Ui.cd.getHt(12)){

            int val = 0;
            float X2 = 0;
            float Y2 = 0;
            int Dis = 0;
            int angle = 0;
            int lastAngle = 0;
            int StopAngle = 220;
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.save();
                canvas.translate(Ui.cd.getHt(6), Ui.cd.getHt(6));
                bass.draw(canvas);
                float radius = 10;
                final RectF oval = new RectF();
                oval.set(0, 0, bass.width, bass.height);
                Path ph = new Path();
                ph.setFillType(Path.FillType.WINDING);
                ph.moveTo(bass.width/2, bass.width/2);
                ph.addArc(oval,-(200),angle);
                ph.lineTo(bass.width/2,bass.width/2);

                canvas.clipPath(ph);
                basstop.draw(canvas);
                canvas.restore();
                canvas.rotate(-(90+20),((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                canvas.rotate(angle,((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                bassdot.draw(canvas);
                //canvas.drawPath(ph,bass.img.maskPaint);
            }


            @Override
            public void onDown(MotionEvent event) {
                super.onDown(event);
                sc.requestDisallowInterceptTouchEvent(true);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                Dis = val;
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                sc.requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                lastAngle = angle;

                angle += val - Dis;
                if (angle < 0) {
                    angle += 360;
                }
                if (angle > 360) {
                    angle -= (int) (angle / 360f) * 360;
                }
                if(angle > StopAngle && angle < 360){
                    if(lastAngle > StopAngle/2f){
                        angle = StopAngle;
                    }else{
                        angle = 0;
                    }
                }
                Dis = val;
                Log.i("My","Rotation : " + angle);
                invalidate();
            }
        };
        trebale.InCenter(main.width ,0);
       // trebale.setX((trebale.getX() + main.width / 3f));
        trebale.setY(Ui.cd.getHt(50 + 30) + top);
        trebale.setScaleX(0.7f);
        trebale.setScaleY(0.7f);
        main.addView(trebale);

        FMText trebleText = textImg.getFMText(getContext(),"LOUDNESS", Ui.cd.getHt(12));
        trebleText.InCenter(trebale);
        trebleText.img.setColor(0x99FFFFFF);
        main.addView(trebleText);
        
        FMView preamp = new FMView(getContext(),(int)bass.width + Ui.cd.getHt(12),(int)bass.height + Ui.cd.getHt(12)){

            int val = 0;
            float X2 = 0;
            float Y2 = 0;
            int Dis = 0;
            int angle = 0;
            int lastAngle = 0;
            int StopAngle = 220;
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.save();
                canvas.translate(Ui.cd.getHt(6), Ui.cd.getHt(6));
                bass.draw(canvas);
                float radius = 10;
                final RectF oval = new RectF();
                oval.set(0, 0, bass.width, bass.height);
                Path ph = new Path();
                ph.setFillType(Path.FillType.WINDING);
                ph.moveTo(bass.width/2, bass.width/2);
                ph.addArc(oval,-(200),angle);
                ph.lineTo(bass.width/2,bass.width/2);

                canvas.clipPath(ph);
                basstop.draw(canvas);
                canvas.restore();
                canvas.rotate(-(90+20),((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                canvas.rotate(angle,((bass.width + Ui.cd.getHt(12)) / 2f),((bass.width + Ui.cd.getHt(12)) / 2f));
                bassdot.draw(canvas);
                //canvas.drawPath(ph,bass.img.maskPaint);
            }


            @Override
            public void onDown(MotionEvent event) {
                super.onDown(event);
                sc.requestDisallowInterceptTouchEvent(true);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                Dis = val;
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                sc.requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                X2 = event.getX();
                Y2 = event.getY();
                float deltaY = Y2 - YY;
                float deltaX = X2 - XX;
                val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
                lastAngle = angle;

                angle += val - Dis;
                if (angle < 0) {
                    angle += 360;
                }
                if (angle > 360) {
                    angle -= (int) (angle / 360f) * 360;
                }
                if(angle > StopAngle && angle < 360){
                    if(lastAngle > StopAngle/2f){
                        angle = StopAngle;
                    }else{
                        angle = 0;
                    }
                }
                Dis = val;
                Log.i("My","Rotation : " + angle);
                invalidate();
            }
        };
        preamp.InCenter(main.width / 3f,0);
        preamp.setX((preamp.getX() + (main.width / 3f * 2) ) - (main.width * 0.035f));
        preamp.setY(Ui.cd.getHt(50 + 30) + top);
        preamp.setScaleX(0.7f);
        preamp.setScaleY(0.7f);
        main.addView(preamp);

        FMText preampText = textImg.getFMText(getContext(),"PREAMP", Ui.cd.getHt(12));
        preampText.InCenter(preamp);
        preampText.img.setColor(0x99FFFFFF);
        main.addView(preampText);

        top = (int)(preamp.getY() + preamp.height + Ui.cd.getHt(33));
        return top;
    }

}
