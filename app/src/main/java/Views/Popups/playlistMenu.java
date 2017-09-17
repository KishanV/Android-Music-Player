package Views.Popups;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.Icon.songsIcon;
import com.shape.Library.allsong.menucloseBtn;
import com.shape.home.menu.backgroundImg;
import com.shape.home.menu.btmBack;

import Views.Library.Menu.SongsClass.playListsAdapter;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class playlistMenu extends FMlyt {

    FMlyt Menu;
    AnimatorSet Set;
    String val;
    ShapeView MainIcon;
    public static radiusSqure back;

    public playlistMenu(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x99000000);

        Menu = new FMlyt(context, Ui.cd.DPW - Ui.cd.getHt(40), Ui.cd.getHt(500)){
            @Override
            protected void onDraw(Canvas canvas){
                back.draw(canvas);
                canvas.clipPath(back.S0);
                super.onDraw(canvas);
            }
        };
        back = new radiusSqure(Menu.width,Menu.height,0,0, Ui.cd.getHt(13));
        back.setColor(backgroundImg.Color0);
        Menu.InCenter(width,height);
        Menu.setBackgroundColor(0x00000000);
        addView(Menu);
        setAlpha(0);

        MainIcon = songsIcon.getFMview(context,true);
        MainIcon.setSize(Ui.cd.getHt(40), Ui.cd.getHt(40));
        MainIcon.setX(Ui.cd.getHt(5));
        MainIcon.setY(Ui.cd.getHt(5));
        Menu.addView(MainIcon);

        final ShapeView cb = menucloseBtn.getFMview(getContext(),true);
        cb.setRipple(true,0.3f);
        cb.onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                Ui.bk.back();
            }
        });
        cb.setX(Menu.width - cb.width);
        Menu.addView(cb);

        FMText title = textImg.getFMText(getContext(),"All PLAYLIST", Ui.cd.getHt(16));
        title.InCenter(MainIcon);
        title.img.setColor(0x99ffffff);
        title.setX(MainIcon.width + Ui.cd.getHt(20));
        Menu.addView(title);

        FMText forText = textImg.getFMText(getContext(),"CLICK TO PLAY PLAYLIST.", Ui.cd.getHt(12));
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
        setList();
    }


    void setList(){
        ListView listview;
        listview = new ListView(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                //canvas.clipPath(squre.S0);
                //squre.draw(canvas);
            }
        };
        listview.setBackgroundColor(btmBack.Color0);
        listview.setLayoutParams(new LayoutParams(Menu.width,Menu.height - Ui.cd.getHt(90)));
        listview.setY(Ui.cd.getHt(50));
        listview.setDivider(null);
        listview.setX(0);
        listview.setY(Ui.cd.getHt(70));
        listview.setAdapter(new playListsAdapter(Menu){
            @Override
            public void onSelect(String Id) {

                playlistMenu.this.onSelect(Id);
            }
        });
        Menu.addView(listview);
    }




    public void onBtn(String name){

    }

    public void onSelect(String id){
        Ui.bk.back();
        Ui.ef.MusicPlayer.handler.playByPlaylistId(id);
    }

    public boolean onEnter(String str){
        return false;
    }
}
