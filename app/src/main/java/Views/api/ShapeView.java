package Views.api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by linedeer on 9/16/2016.
 */

public class ShapeView extends  FMView {
    public shapeImg img;
    public boolean mask = false;

    public ShapeView(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00000000);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if(img != null){
            img.setSize(width,height);
        }
    }

    public void setSize(int width, int height,boolean imgSize) {
        super.setSize(width, height);
        if(img != null && imgSize){
            img.setSize(width,height);
        }
    }

    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.clipRect(0,0,width,height);
        //canvas.quickReject(0,0,width,height, Canvas.EdgeType.BW);

        if(img != null){
            if(mask){
                canvas.drawPath(img.mask,img.maskPaint);
                super.postDraw(canvas);
                img.draw(canvas);
                super.afterDraw(canvas,img.mask);
            }else{
                img.draw(canvas);
            }
        }
    }

    public void setInCenter(int width, int height) {
        setX((width - this.width) / 2f);
        setY((height - this.height) / 2f);
    }
}
