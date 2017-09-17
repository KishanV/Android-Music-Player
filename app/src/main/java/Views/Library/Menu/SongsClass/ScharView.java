package Views.Library.Menu.SongsClass;

import android.content.Context;

import com.linedeer.player.Ui;
import com.shape.Library.allsong.itemStick;

import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.textImg;

/**
 * Created by linedeer on 9/24/2016.
 */

public class ScharView  extends FMlyt{
    FMText txt;
    ShapeView stick;
    public ScharView(Context context ) {
        super(context);

        stick = itemStick.getFMview(getContext(),false);
        addView(stick);

        txt = textImg.getFMText(getContext(),"-", Ui.cd.getHt(18));
        txt.InCenter(stick.width,stick.width);
        txt.img.P0.setColor(0x88FFFFFF);

        addView(txt);

        setSize(stick.width,stick.height);

        setClickable(false);

    }

    String aChar = "";
    public void setChar(String aChar) {
        if(!this.aChar.equals(aChar)){
            this.aChar = aChar;
            txt.setText(aChar);
            txt.InCenter(stick.width,stick.width);
            txt.invalidate();
        }
    }
}
