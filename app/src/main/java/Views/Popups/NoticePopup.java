package Views.Popups;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.linedeer.player.Ui;
import com.shape.home.menu.backgroundImg;
import com.shape.home.notice;

import Views.api.FMView;
import Views.api.FMlyt;
import Views.radiusSqure;


public class NoticePopup extends FMlyt {

    FMView Menu;
    AnimatorSet Set;
    public static radiusSqure back;

    public NoticePopup(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x99000000);

        Menu = notice.getFMview(getContext(),false);
        back = new radiusSqure(Menu.width,Menu.height,0,0, Ui.cd.getHt(13));
        back.setColor(backgroundImg.Color0);
        Menu.InCenter(width,height);
        Menu.setBackgroundColor(0x00000000);
        addView(Menu);
        setAlpha(0);

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
    }



    public void onBtn(String name){

    }

    public void onSelect(String id){

    }

    public boolean onEnter(String str){
        return false;
    }
}
