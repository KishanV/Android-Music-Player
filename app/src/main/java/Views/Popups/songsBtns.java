package Views.Popups;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.Icon.addNextIcon;
import com.shape.Library.Icon.fileInfoIcon;
import com.shape.Library.Icon.folderIcon;
import com.shape.Library.Icon.ringToneIcon;
import com.shape.Library.Icon.shareFileIcon;
import com.shape.Library.Icon.songsIcon;
import com.shape.Library.allsong.menucloseBtn;
import com.shape.home.menu.backgroundImg;
import com.shape.home.menu.btmBack;

import Views.ContentHome;
import Views.Home.Menu.Item;
import Views.Library.Menu.SongsClass.playListsAdapter;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;


public class songsBtns extends FMlyt {

    FMlyt Menu;
    AnimatorSet Set;
    String val;
    ShapeView MainIcon;
    public static radiusSqure back;

    public songsBtns(Context context, int width, int height) {
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
        listview.setLayoutParams(new FrameLayout.LayoutParams(Menu.width,Menu.height - Ui.cd.getHt(70+230)));
        listview.setY(Ui.cd.getHt(50));
        listview.setDivider(null);
        listview.setX(0);
        listview.setY(Ui.cd.getHt(70));
        listview.setAdapter(new playListsAdapter(Menu){
            @Override
            public void onSelect(String Id) {
                songsBtns.this.onSelect(Id);
            }
        });
        Menu.addView(listview);
        init((int) (Menu.height - Ui.cd.getHt(70+230) + listview.getY()));
    }

    void init(int top){
        int len = 4;
        int line = 2;
        int spaceWidth = Ui.cd.getHt(15);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)Menu.width - Ui.cd.getHt(15 * (len + 1))) / len);
        int itemHeight = (int)(itemWidth * 1.5f);
        int iconSize = (int)(itemWidth * 0.7f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                if((j*len) + i < 6){
                    final Item item = new Item(getContext(),itemWidth,itemHeight);


                    //item.setBackgroundColor(0xFFCCCCCC);
                    item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                    item.setY(top + (j * itemHeight) + (spaceWidth * (j + 1)));
                    Menu.addView(item);
                    Log.i("My","(i*len) + j = " + ((i*len) + j));
                    switch ((j*len) + i){
                        case 0:
                            item.setData("PLAY ALL", new com.shape.Library.Icon.playAllIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    onBtn(item.name.Text);
                                }
                            });
                            break;
                        case 1:
                            item.setData("ADD NEXT",new addNextIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    onBtn(item.name.Text);
                                }
                            });
                            break;
                        case 2:
                            item.setData("SHOW FILE",new folderIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    onBtn(item.name.Text);
                                }
                            });
                            break;
                        case 3:
                            item.setData("SHARE",new shareFileIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    onBtn(item.name.Text);
                                }
                            });
                            break;
                        case 4:
                            item.setData("RINGTONE",new ringToneIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    Ui.bk.back();
                                    ContentHome.This.opneNotice();
                                }
                            });
                            break;
                        case 5:
                            item.setData("INFO",new fileInfoIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    Ui.bk.back();
                                    ContentHome.This.opneNotice();
                                }
                            });
                            break;
                    }
                }

            }
        }
    }


    public void onBtn(String name){

    }

    public void onSelect(String id){

    }

    public boolean onEnter(String str){
        return false;
    }
}
