package Views.Home.Img;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Path;import Views.api.shapeImg;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class songSliderImg
        extends shapeImg{



    class Path0 extends Path{
        public Path0() {
            moveTo(80f, 8f);
            lineTo(80f, 72f);
            quadTo(80f, 80f, 72f, 80f);
            lineTo(8f, 80f);
            quadTo(0f, 80f, 0f, 72f);
            lineTo(0f, 8f);
            quadTo(0f, 0f, 8f, 0f);
            lineTo(72f, 0f);
            quadTo(80f, 0f, 80f, 8f);
        }
    }

    public Paint P0 = new Paint();
    public Path0 S0 = new Path0();

    float Ht = 80
            ;
    float Wh = 80
            ;


    public songSliderImg
            (int width,int height,int x,int y) {
        this.width =width;
        this.height = height;

        init((float)width/(float)Wh, (float)height/(float)Ht, x, y);
        LinearGradient Lg  = null;
        S0.transform(matrix);
        P0.setColor(0x55ffffff);
        P0.setAntiAlias(true);

    }


    public void draw(Canvas canvas) {
        canvas.drawPath(S0, P0);
    }
}