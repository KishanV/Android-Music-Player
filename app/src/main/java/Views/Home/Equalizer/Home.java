package Views.Home.Equalizer;

import android.content.Context;

import com.linedeer.player.Ui;
import com.shape.home.menu.backgroundImg;
import com.shape.home.menu.btmBack;

import Views.Home.Equalizer.eqlizerClasse.equalizer;
import Views.Home.Equalizer.eqlizerClasse.tunner;
import Views.api.FMlyt;
import Views.radiusSqure;

public class Home extends FMlyt {

    public static radiusSqure rs;
    radiusSqure btm;

    public Home(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00000000);

        rs = new radiusSqure(width,this.height - Ui.cd.getHt(60),0,0, Ui.cd.getHt(13), Ui.cd.getHt(13),0,0);
        rs.setColor(backgroundImg.Color0);
        addShape(rs);

        btm = new radiusSqure(width, Ui.cd.getHt(60),0,this.height - Ui.cd.getHt(60),0,0, Ui.cd.getHt(13), Ui.cd.getHt(13));
        btm.setColor(btmBack.Color0);
        addShape(btm);

        btmView BtmView;
        BtmView = new btmView(context,width, Ui.cd.getHt(60)){
            @Override
            int openMenu(float scolled) {
                int No = super.openMenu(scolled);
                Home.this.openMenu(No);
                return No;
            }
        };
        BtmView.setBackgroundResource(0);
        BtmView.setY(height - BtmView.height);
        addView(BtmView);

        Sp = new equalizer(context,width, (int) rs.height);
        addView(Sp);
    }

    FMlyt Sp;
    int no = 0;
    void openMenu(int No){
        if(no == No){
            return;
        }
        if(Sp != null){
            removeView(Sp);
        }
        no = No;
        switch (No){
            case 0:
                Sp = new equalizer(getContext(),width, (int) rs.height);
                addView(Sp);
                break;
            default:
                Sp = new tunner(getContext(),width, (int) rs.height);
                addView(Sp);
        }
    }

}
