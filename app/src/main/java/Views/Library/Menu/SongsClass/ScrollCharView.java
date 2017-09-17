package Views.Library.Menu.SongsClass;

import android.content.Context;

import com.linedeer.player.Ui;
import com.shape.Library.Icon.selectBack;

import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.textImg;

public class ScrollCharView extends FMlyt{
    FMText txt;
    ShapeView stick;
    public ScrollCharView(Context context ) {
        super(context);

        stick = selectBack.getFMview(getContext(),false);
        addView(stick);

        txt = textImg.getFMText(getContext(),"A", Ui.cd.getHt(22));
        txt.InCenter(stick);
        txt.img.P0.setColor(0xFFFFFFFF);
        addView(txt);

        setSize(stick.width,stick.height);
        setClickable(false);
    }

    String aChar = "";
    public void setChar(String aChar) {
        if(!this.aChar.equals(aChar)){
            this.aChar = aChar;
            txt.setText(aChar);
            txt.InCenter(stick);
            txt.invalidate();
        }
    }
}
