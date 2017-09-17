package Views.Home.Img;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Path;import Views.api.shapeImg;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class playListImg
        extends shapeImg{



    class Path0 extends Path{
        public Path0() {
            moveTo(5.5f, 0f);
            quadTo(7.8f, 0f, 9.4f, 1.6f);
            quadTo(11f, 3.2f, 11f, 5.5f);
            lineTo(11f, 24f);
            lineTo(14f, 24f);
            lineTo(14f, 26f);
            lineTo(11f, 26f);
            lineTo(11f, 31f);
            lineTo(14f, 31f);
            lineTo(14f, 33f);
            lineTo(11f, 33f);
            lineTo(11f, 38f);
            lineTo(14f, 38f);
            lineTo(14f, 40f);
            lineTo(11f, 40f);
            lineTo(11f, 58.5f);
            quadTo(11f, 60.8f, 9.4f, 62.4f);
            quadTo(7.8f, 64f, 5.5f, 64f);
            lineTo(0f, 64f);
            lineTo(0f, 0f);
            lineTo(5.5f, 0f);
        }
    }

    class Path1 extends Path{
        public Path1() {
            moveTo(5.5f, 0f);
            quadTo(7.8f, 0f, 9.4f, 1.6f);
            quadTo(11f, 3.2f, 11f, 5.5f);
            lineTo(11f, 58.5f);
            quadTo(11f, 60.8f, 9.4f, 62.4f);
            quadTo(7.8f, 64f, 5.5f, 64f);
            lineTo(0f, 64f);
            lineTo(0f, 0f);
            lineTo(5.5f, 0f);
        }
    }

    class Path2 extends Path{
        public Path2() {
            moveTo(14f, 26f);
            lineTo(0f, 26f);
            lineTo(0f, 24f);
            lineTo(14f, 24f);
            lineTo(14f, 26f);
        }
    }

    class Path3 extends Path{
        public Path3() {
            moveTo(14f, 33f);
            lineTo(0f, 33f);
            lineTo(0f, 31f);
            lineTo(14f, 31f);
            lineTo(14f, 33f);
        }
    }

    class Path4 extends Path{
        public Path4() {
            moveTo(14f, 40f);
            lineTo(0f, 40f);
            lineTo(0f, 38f);
            lineTo(14f, 38f);
            lineTo(14f, 40f);
        }
    }

    public Paint P0 = new Paint();
    public Path0 S0 = new Path0();

    public Paint P1 = new Paint();
    public Path1 S1 = new Path1();

    public Paint P2 = new Paint();
    public Path2 S2 = new Path2();

    public Paint P3 = new Paint();
    public Path3 S3 = new Path3();

    public Paint P4 = new Paint();
    public Path4 S4 = new Path4();

    float Ht = 64
            ;
    float Wh = 14
            ;


    public playListImg
            (int width,int height,int x,int y) {
        this.width =width;
        this.height = height;

        init((float)width/(float)Wh, (float)height/(float)Ht, x, y);
        LinearGradient Lg  = null;
        S0.transform(matrix);
        P0.setColor(0xFF000000);
        P0.setAntiAlias(true);

        S1.transform(matrix);
        P1.setColor(0xFF433c62);
        P1.setAntiAlias(true);

        S2.transform(matrix);
        P2.setColor(0xFFd35d69);
        P2.setAntiAlias(true);

        S3.transform(matrix);
        P3.setColor(0xFFd35d69);
        P3.setAntiAlias(true);

        S4.transform(matrix);
        P4.setColor(0xFFd35d69);
        P4.setAntiAlias(true);

    }


    public void draw(Canvas canvas) {
        canvas.drawPath(S0, P0);
        canvas.drawPath(S1, P1);
        canvas.drawPath(S2, P2);
        canvas.drawPath(S3, P3);
        canvas.drawPath(S4, P4);
    }
}