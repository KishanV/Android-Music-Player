package Views.Home.views;

import android.content.Context;
import android.graphics.Canvas;

import com.linedeer.player.Ui;

import Views.Home.Img.playListImg;
import Views.api.FMView;

public class playListBtn extends FMView {

    playListImg playListImg;
    public playListBtn(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00000000);
        playListImg = new playListImg(Ui.cd.getHt(14),height,0,0);
        setRipple(true,0.8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        playListImg.draw(canvas);
        canvas.clipPath(playListImg.S0);
        super.onDraw(canvas);
    }
}
