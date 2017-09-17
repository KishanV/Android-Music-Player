package Views.Library.Menu.LibreryClass;

import android.content.Context;
import android.graphics.Canvas;

import com.linedeer.player.Ui;
import com.shape.Library.playlistIcon;

import Views.api.FMView;
import Views.textImg;

public class topView extends FMView{

    textImg title;
    playlistIcon PlaylistIcon;
    public topView(Context context, int width, int height) {
        super(context, width, height);

        title = new textImg(0,height,0,0,(int) Ui.cd.getHt(14));
        title.setText("PLAYLIST & LIBRERY");
        title.setX(Ui.cd.getHt(35));

        PlaylistIcon = new playlistIcon(Ui.cd.getHt(25), Ui.cd.getHt(26),0,0);
        //PlaylistIcon.setX((int)((width - Ui.cd.getHt(25)) / 2f));
        PlaylistIcon.setY((height - Ui.cd.getHt(26)) / 2);
        super.setSize((int)(width+title.width+ Ui.cd.getHt(10)),height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        PlaylistIcon.draw(canvas);
        title.draw(canvas);
    }
}
