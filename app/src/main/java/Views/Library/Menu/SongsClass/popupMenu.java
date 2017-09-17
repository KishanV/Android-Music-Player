package Views.Library.Menu.SongsClass;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.shape.Library.popup.closeBtn;
import com.shape.Library.popup.listviewBackground;
import com.shape.Library.popup.mainBackground;
import com.shape.Library.popup.playAllIcon;
import com.shape.Library.popup.ringtoneIcon;
import com.shape.Library.popup.shareIcon;
import com.shape.home.playlist.popup.Icon;
import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class popupMenu extends FMlyt {

    FMlyt Menu;
    radiusSqure rs;
    AnimatorSet Set;
    public ListView listview;

    public popupMenu(Context context, int width, int height, FMView menu) {
        super(context, width, height);
        setBackgroundColor(0x66000000);


        Menu = new FMlyt(context, Ui.cd.getHt((int) mainBackground.Wh), Ui.cd.getHt((int) mainBackground.Ht)){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
               // rs.draw(canvas);
            }
        };

        playListsAdapter adapter = new playListsAdapter(Menu){
            @Override
            public void onSelect(String Id) {
                popupMenu.this.onAdd(Id);
            }
        };
        rs = new radiusSqure(Ui.cd.getHt(200), adapter.getCount() * Ui.cd.getHt(44),0,0, Ui.cd.getHt(13));
        rs.setColor(0xFFD35D69);

        Menu.setX((menu.getX()) - (Menu.width  + Ui.cd.getHt(10)));
        Rect rc = new Rect();
        menu.getGlobalVisibleRect(rc);
        int y = rc.top - Ui.cd.getHt(10);
        if(y > height - Menu.height - Ui.cd.getHt(10)){
            y = height - Menu.height - Ui.cd.getHt(10);
        }
        Menu.setY(y);
       // Menu.setScaleX(0.8f);
        //Menu.setScaleY(0.8f);
        Menu.setBackgroundColor(0x00000000);
        addView(Menu);

        Menu.addView(mainBackground.getFMview(getContext(),false));
        setAlpha(0);

        ShapeView closebtn = closeBtn.getFMview(getContext(),false);
        closebtn.setY((Ui.cd.getHt(40) - closebtn.width) / 2);
        closebtn.setX(Menu.width - (closebtn.getY() + closebtn.width));
        closebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.ef.clickPlay();
                Ui.bk.back();
            }
        });
        Menu.addView(closebtn);

        ShapeView icon = Icon.getFMview(getContext(),true);
        icon.setY((Ui.cd.getHt(40) - icon.width) / 2);
        icon.setX(icon.getY());


        FMText title = textImg.getFMText(getContext(),"ADD PLAYLISTS", Ui.cd.getHt(12));
        title.setX(icon.getY() + icon.width + icon.getY());
        title.setY((Ui.cd.getHt(40) - title.height) / 2);
        Menu.addView(title);

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
                //ObjectAnimator.ofFloat(Menu, "ScaleX", 1.0F),
                //ObjectAnimator.ofFloat(Menu, "ScaleY", 1.0F),
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );
        Set.start();


        listview = new ListView(getContext());
        listview.setLayoutParams(new LayoutParams(Menu.width - Ui.cd.getHt(4), Ui.cd.getHt(200)));
        listview.setY( Ui.cd.getHt(40));
        listview.setX(Ui.cd.getHt(2));
        listview.setDivider(null);
        listview.setBackgroundColor(listviewBackground.Color0);

        listview.setAdapter(adapter);
        Menu.addView(listview);

        ShapeView delete = ringtoneIcon.getFMview(context,true);
        int threeWh = (int) (((Menu.width / 3f)));
        int intiX = (int) (((Menu.width / 3f) - delete.width) / 2f);
        delete.setRipple(true,0.5f);
        delete.setY(Ui.cd.getHt(250));
        delete.setX(intiX + Ui.cd.getHt(5));
        Menu.addView(delete);

        ShapeView playAll = playAllIcon.getFMview(context,true);
        playAll.setRipple(true,0.5f);
        playAll.setY(Ui.cd.getHt(250));
        playAll.setX(threeWh + (intiX));
        Menu.addView(playAll);

        ShapeView share = shareIcon.getFMview(context,true);
        share.setRipple(true,0.5f);
        share.setY(Ui.cd.getHt(250));
        share.setX((threeWh*2) + (intiX) - Ui.cd.getHt(5));
        Menu.addView(share);


       /* ShapeView ringtone = ringtoneIcon.getFMview(context,true);
        ringtone.setRipple(true,0.5f);
        ringtone.setY(Ui.cd.getHt(250) + ringtone.height + Ui.cd.getHt(10));
        ringtone.setX(intiX + Ui.cd.getHt(5));
        Menu.addView(ringtone);*/

        Menu.addView(icon);
    }
    public  void onAdd(String id) {

    }
}
