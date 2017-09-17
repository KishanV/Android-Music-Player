package Views.Home.QuickEq;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.linedeer.player.Ui;
import com.shape.home.QEq.textColor;
import com.shape.home.menu.bassBack;
import com.shape.home.menu.bassDot;
import com.shape.home.menu.bassTop;

import Views.api.FMView;
import Views.api.shapeImg;
import Views.textImg;

public class WheelItem  extends FMView {

    shapeImg bass  ;
    shapeImg basstop  ;
    shapeImg bassdot ;
    float XX = 0;
    float YY = 0;
    textImg text;
    textImg levelText;
    public WheelItem(Context context, int width, int height) {
        super(context, width, height);
        //setBackgroundColor(0x33000000);
        bass = new bassBack(width,width,0,0);
        basstop = new bassTop(width,width,0,0);
        bassdot = new bassDot(width,width,0,0);

        XX = (int)(bass.width) / 2;
        YY = (int)(bass.width) / 2;

        text = textImg.getText("BASS", Ui.cd.getHt(14));
        text.InCenter(width,height);
        text.setY((int) (height - text.height));
        text.setColor(textColor.Color0);
        addShape(text);

        levelText = new textImg(width,width,0,0, Ui.cd.getHt(14));
        levelText.setColor(textColor.Color0);
        levelText.setText("0",true);
        //addShape(levelText);
    }

    int val = 0;

    float X2 = 0;
    float Y2 = 0;
    int Dis = 0;
    int angle = 110;
    int lastAngle = 0;
    int StopAngle = 220;
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.save();
        canvas.translate(0,-Ui.cd.getHt(10));

        canvas.save();
        bass.draw(canvas);
        float radius = 10;
        final RectF oval = new RectF();
        oval.set(0, 0, bass.width, bass.height);
        Path ph = new Path();
        ph.setFillType(Path.FillType.WINDING);
        ph.moveTo(bass.width/2, bass.width/2);
        if(angle > 110 - 1){
            ph.addArc(oval,-(200) + 110,angle - 110);
        }else{
            ph.addArc(oval,-(90) - (110 - angle),(110 - angle));
        }
        ph.lineTo(bass.width/2,bass.width/2);

        canvas.clipPath(ph);
        basstop.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.rotate(-(90+20),XX,YY);
        canvas.rotate(angle,XX,YY);
        bassdot.draw(canvas);
        canvas.restore();

        int val = (angle - 110);
        val = (int) ((100f / 110) * val);
        levelText.setText(val+"",true);
        levelText.draw(canvas);
        canvas.restore();
        super.drawShape(canvas);
        //canvas.drawPath(ph,bass.img.maskPaint);
    }


    @Override
    public void onDown(MotionEvent event) {
        super.onDown(event);

        X2 = event.getX();
        Y2 = event.getY();
        float deltaY = Y2 - YY;
        float deltaX = X2 - XX;
        val = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
        Dis = val;
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
        int rval ;
        rval = (angle - 110);
        rval = (int) ((100f / 110) * rval);
        onVal(rval);
        invalidate();
    }

    public void onVal(int rval){
        Log.i("My","onVal BASS : " + rval);
    }

    public void setVal(int rval){
        Log.i("My","setVal BASS : " + rval);

        rval = (int) ((110 / 100f) * (rval));
        rval += 110;
        angle = rval;
    }

    public void setName(String name) {
        text.setSize(0,0);
        text.setText(name);
        text.InCenter(width,height);
        text.setY((int) (height - text.height));
    }
}
