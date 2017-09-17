package Views.Home.PlayList.PlayListClass;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.shape.home.playlist.popup.Icon;
import com.shape.home.playlist.popup.bodyBackground;
import com.shape.home.playlist.popup.closeButton;
import com.shape.home.playlist.popup.mainBackground;

import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class popupPlaylist extends FMlyt {

    FMlyt Menu;
    radiusSqure rs;
    AnimatorSet Set;
    public ListView listview;

    public popupPlaylist(Context context, int width, int height, FMView menu) {
        super(context, width, height);
        setBackgroundColor(0x66000000);


        Menu = new FMlyt(context, Ui.cd.getHt(210), Ui.cd.getHt(250)){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
               // rs.draw(canvas);
            }
        };

        playListsAdapter adapter = new playListsAdapter(Menu);
        rs = new radiusSqure(Ui.cd.getHt(200), adapter.getCount() * Ui.cd.getHt(44),0,0, Ui.cd.getHt(13));
        rs.setColor(0xFFD35D69);

        Menu.setX((menu.getX() - Menu.width) + menu.width - Ui.cd.getHt(10));
        Menu.setY(Ui.cd.getHt(11));
        Menu.setScaleX(0.8f);
        Menu.setScaleY(0.8f);
        Menu.setBackgroundColor(mainBackground.Color0);
        addView(Menu);
        setAlpha(0);

        ShapeView closeBtn = closeButton.getFMview(getContext(),true);
        closeBtn.setY((Ui.cd.getHt(40) - closeBtn.width) / 2);
        closeBtn.setX(Menu.width - (closeBtn.getY() + closeBtn.width));
        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });
        Menu.addView(closeBtn);
        ShapeView icon = Icon.getFMview(getContext(),true);
        icon.setY((Ui.cd.getHt(40) - icon.width) / 2);
        icon.setX(icon.getY());
        Menu.addView(icon);

        FMText title = textImg.getFMText(getContext(),"ALL PLAYLISTS", Ui.cd.getHt(12));
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
                ObjectAnimator.ofFloat(Menu, "ScaleX", 1.0F),
                ObjectAnimator.ofFloat(Menu, "ScaleY", 1.0F),
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );
        Set.start();


        listview = new ListView(getContext());
        listview.setLayoutParams(new FrameLayout.LayoutParams(Menu.width,Menu.height - Ui.cd.getHt(43)));
        listview.setY( Ui.cd.getHt(40));
        listview.setX(0);
        listview.setDivider(null);
        listview.setBackgroundColor(bodyBackground.Color0);

        listview.setAdapter(adapter);
        Menu.addView(listview);
    }
}
