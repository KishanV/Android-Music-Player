package Views.Popups;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.linedeer.player.Ui;
import com.shape.Library.Icon.songsIcon;
import com.shape.Library.allsong.clearBtn;
import com.shape.Library.allsong.deleteBtn;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.allsong.menucloseBtn;
import com.shape.Library.allsong.renameBtn;

import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class menuBtnsOLD extends FMlyt {

    FMlyt Menu;
    AnimatorSet Set;
    String val;
    ShapeView MainIcon;
    public static radiusSqure squre;
    public static radiusSqure back;

    public menuBtnsOLD(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x99000000);


        Menu = new FMlyt(context, Ui.cd.DPW - Ui.cd.getHt(60), Ui.cd.getHt(190)){
            @Override
            protected void onDraw(Canvas canvas) {

                back.draw(canvas);
                canvas.clipPath(back.S0);
                super.onDraw(canvas);
            }
        };
        back = new radiusSqure(Menu.width, Menu.height,0,0, Ui.cd.getHt(13));
        back.setColor(itemRect.Color0);
        Menu.InCenter(width,height);
        Menu.setBackgroundColor(0x00000000);
        addView(Menu);
        setAlpha(0);

        MainIcon = songsIcon.getFMview(context,true);
        MainIcon.setSize(Ui.cd.getHt(40), Ui.cd.getHt(40));
        MainIcon.setX(Ui.cd.getHt(5));
        MainIcon.setY(Ui.cd.getHt(5));
        Menu.addView(MainIcon);

        final ShapeView cb = menucloseBtn.getFMview(getContext(),false);
        cb.setRipple(true);
        cb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });
        cb.setX(Menu.width - cb.width);
        Menu.addView(cb);

        FMText title = textImg.getFMText(getContext(),"SONG OPTIONS", Ui.cd.getHt(16));
        title.InCenter(MainIcon);
        title.img.setColor(0x99ffffff);
        title.setX(MainIcon.width + Ui.cd.getHt(20));
        Menu.addView(title);

        FMText forText = textImg.getFMText(getContext(),"ADD TO PLAYLIST BY CLICK", Ui.cd.getHt(12));
        forText.img.setColor(0x66ffffff);
        forText.setX(MainIcon.width + Ui.cd.getHt(20));
        forText.setY(title.getY() + title.height + Ui.cd.getHt(10));
        Menu.addView(forText);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });

        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(200);
        Set.playTogether(
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );
        Set.start();

        setMenus();
    }


    void setMenus(){


        ShapeView rename = renameBtn.getFMview(getContext(),true);
        int leftX = (int) ((Menu.width - (rename.width * 3 + Ui.cd.getHt(20))) / 2f);
        rename.setRipple(true,0.3f);
        rename.setX(leftX);
        rename.setY(Menu.height - rename.height - Ui.cd.getHt(20));
        Menu.addView(rename);

        ShapeView delete = deleteBtn.getFMview(getContext(),true);
        delete.setRipple(true,0.3f);
        delete.setX(leftX  + (rename.width * 1f) + Ui.cd.getHt(10));
        delete.setY(Menu.height - rename.height - Ui.cd.getHt(20));
        Menu.addView(delete);

        ShapeView clear = clearBtn.getFMview(getContext(),true);
        clear.setRipple(true,0.3f);
        clear.setX(leftX  + (rename.width * 2f) + Ui.cd.getHt(20));
        clear.setY(Menu.height - rename.height - Ui.cd.getHt(20));
        Menu.addView(clear);


    }

    public boolean onEnter(String str){
        return false;
    }
}
