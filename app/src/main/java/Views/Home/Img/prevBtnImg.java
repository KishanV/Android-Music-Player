package Views.Home.Img;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Path;import Views.api.shapeImg;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class prevBtnImg
        extends shapeImg{



    class Path0 extends Path{
        public Path0() {
            moveTo(0f, 13f);
            quadTo(0f, 0f, 13f, 0f);
            lineTo(93.5f, 0f);
            lineTo(93.5f, 50f);
            lineTo(13f, 50f);
            quadTo(0f, 50f, 0f, 37f);
            lineTo(0f, 13f);
        }
    }

    class Path1 extends Path{
        public Path1() {
            moveTo(32.25f, 32.35f);
            lineTo(19.55f, 25.05f);
            lineTo(32.25f, 17.7f);
            lineTo(32.25f, 32.35f);
        }
    }

    class Path2 extends Path{
        public Path2() {
            moveTo(39.6f, 17.75f);
            lineTo(39.6f, 32.4f);
            lineTo(26.9f, 25.1f);
            lineTo(39.6f, 17.75f);
        }
    }

    public Paint P0 = new Paint();
    public Path0 S0 = new Path0();

    public Paint P1 = new Paint();
    public Path1 S1 = new Path1();

    public Paint P2 = new Paint();
    public Path2 S2 = new Path2();

    float Ht = 50
            ;
    float Wh = 94
            ;


    public prevBtnImg
            (int width,int height,int x,int y) {
        this.width =width;
        this.height = height;

        init((float)width/(float)Wh, (float)height/(float)Ht, x, y);
        LinearGradient Lg  = null;
        S0.transform(matrix);
        P0.setColor(0xFF292440);
        P0.setAntiAlias(true);

        S1.transform(matrix);
        P1.setColor(0x32ffffff);
        P1.setAntiAlias(true);

        S2.transform(matrix);
        P2.setColor(0xFFffffff);
        P2.setAntiAlias(true);

    }


    public void draw(Canvas canvas) {
        canvas.drawPath(S0, P0);
        canvas.drawPath(S1, P1);
        canvas.drawPath(S2, P2);
    }
}