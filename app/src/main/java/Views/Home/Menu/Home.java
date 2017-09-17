package Views.Home.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.Icon.albumIcon;
import com.shape.Library.Icon.artistIcon;
import com.shape.Library.Icon.folderIcon;
import com.shape.Library.Icon.genreIcon;
import com.shape.Library.Icon.playAllIcon;
import com.shape.Library.Icon.settingIcon;
import com.shape.Library.Icon.songsIcon;
import com.shape.Library.allsong.songIcon;
import com.shape.home.menu.backgroundImg;
import com.shape.home.menu.btmBack;

import Views.ContentHome;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.api.shapeImg;
import Views.radiusSqure;
import Views.textImg;


public class Home extends FMlyt {

    radiusSqure rs;
    radiusSqure btm;
    public Home(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00000000);

        init();

        rs = new radiusSqure(width,this.height - Ui.cd.getHt(70),0,0, Ui.cd.getHt(13), Ui.cd.getHt(13),0,0);
        rs.setColor(backgroundImg.Color0);
        addShape(rs);

        btm = new radiusSqure(width, Ui.cd.getHt(70),0,this.height - Ui.cd.getHt(70),0,0, Ui.cd.getHt(13), Ui.cd.getHt(13));
        btm.setColor(btmBack.Color0);
        addShape(btm);

        settingBtn setting = new settingBtn(getContext(), Ui.cd.getHt(100), Ui.cd.getHt(50));
        setting.setX(rs.width - setting.width - Ui.cd.getHt(10));
        setting.setY(rs.height + Ui.cd.getHt(10));
        setting.onClick(new call(){
            @Override
            public void onCall(boolean bl) {
                Ui.bk.back();
                ContentHome.This.opneNotice();
            }
        });
        addView(setting);
    }

    class settingBtn extends  FMView{
        radiusSqure rs;
        public settingBtn(Context context, int width, int height) {
            super(context, width, height);

            textImg text = new textImg(0,0,0,0, Ui.cd.getHt(14));
            text.setText("SETTING");
            text.InCenter(width,height);
            text.setX(Ui.cd.getHt(10));
            addShape(text);

            shapeImg img = settingIcon.getShape();
            img.setSize(height,height);
            addShape(img);

            setSize((int) (text.width + Ui.cd.getHt(10) + img.width),height);

            img.setX((int) (this.width - img.width ));

              rs = new radiusSqure(this.width,this.height,0,0, Ui.cd.getHt(13));
            //addShape(rs);

            setRipple(true,0.3f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.postDraw(canvas);
            super.drawShape(canvas);
            super.afterDraw(canvas,rs.S0);
        }
    }

    void init(){
        int len = 4;
        int line = 2;
        int spaceWidth = Ui.cd.getHt(15);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)width - Ui.cd.getHt(15 * (len + 1))) / len);
        int itemHeight = (int)(itemWidth * 1.5f);
        int iconSize = (int)(itemWidth * 0.7f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                if((j*len) + i < 6){
                    Item item = new Item(getContext(),itemWidth,itemHeight);

                    //item.setBackgroundColor(0xFFCCCCCC);
                    item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                    item.setY((j * itemHeight) + (spaceWidth * (j + 1)));
                    addView(item);
                    Log.i("My","(i*len) + j = " + ((i*len) + j));
                    switch ((j*len) + i){
                        case 0:
                            item.setData("All SONG", new songsIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(1);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        case 1:
                            item.setData("ALBUMS",new albumIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(2);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        case 2:
                            item.setData("ARTISTS",new artistIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(3);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        case 3:
                            item.setData("GENERS",new genreIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(4);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        case 4:
                            item.setData("FOLDERS",new folderIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(5);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        case 5:
                            item.setData("PLAYLISTS",new playAllIcon(iconSize,iconSize,0,0));
                            item.onClick(new call(){
                                @Override
                                public void onCall(boolean bl) {
                                    ContentHome.This.closelibMenu();
                                    ContentHome.This.MenuHome.openPage(0);
                                    ContentHome.This.openMusucLibrery();
                                }
                            });
                            break;
                        default:
                            item.setData("NONE", songIcon.getShape());
                    }
                }

            }
        }

        setSize(width,(line * itemHeight) + ((line+2) * spaceWidth) + Ui.cd.getHt(60));
    }
}
