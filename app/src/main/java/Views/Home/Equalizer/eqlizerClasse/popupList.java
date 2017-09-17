package Views.Home.Equalizer.eqlizerClasse;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.shape.home.equalizer.popupBack;
import com.shape.home.equalizer.savebtnRingColor;

import Views.Home.QuickEq.presetAdapter;
import Views.api.FMlyt;
import Views.radiusBorder;
import Views.radiusSqure;

public class popupList extends FMlyt {

    ListView listview;
    radiusSqure rs;
    radiusBorder rb;
    FMlyt sl;

    public popupList(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x66000000);

        sl = new FMlyt(getContext(), Ui.cd.getHt(200), Ui.cd.getHt(300)){};
        sl.setBackgroundColor(0x00000000);
        sl.InCenter(width,height);

        rs = new radiusSqure(sl.width  - Ui.cd.getHt(4),sl.height - Ui.cd.getHt(4),0,0, Ui.cd.getHt(13));
        rs.setColor(popupBack.Color0);
       // addShape(rs);

        rb = new radiusBorder(sl.width,sl.height,0,0, Ui.cd.getHt(2), Ui.cd.getHt(13));
        rb.setColor(savebtnRingColor.Color0);
        sl.addShape(rb);

        presetAdapter data;
        listview = new ListView(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                canvas.clipPath(rs.S0);
                super.onDraw(canvas);
            }
        };
        listview.setLayoutParams(new FrameLayout.LayoutParams( sl.width - Ui.cd.getHt(4), sl.height - Ui.cd.getHt(4)));

        listview.setDivider(null);
        listview.setX(Ui.cd.getHt(2));
        listview.setY(Ui.cd.getHt(2));
        listview.setBackgroundResource(0);
        //listview.setBackgroundColor(com.shape.home.slider.backgroundImg.Color0);
        data = new presetAdapter(){

            @Override
            public int getHeigtht() {
                return sl.width - Ui.cd.getHt(4);
            }

            @Override
            public void onReload(){
                if(listview != null){
                    Ui.bk.back();
                    popupList.this.onReload();
                }
            }
        };
        listview.setAdapter(data);
        sl.addView(listview);
        addView(sl);
    }

    public void onReload(){

    }

}
