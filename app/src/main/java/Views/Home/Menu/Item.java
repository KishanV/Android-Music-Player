package Views.Home.Menu;

import android.content.Context;
import android.graphics.Canvas;

import com.linedeer.player.Ui;
import com.shape.home.menu.btmBack;
import com.shape.home.menu.itemBack;



import Views.api.FMView;
import Views.api.shapeImg;
import Views.radiusBorder;
import Views.radiusSqure;
import Views.textImg;


public class Item extends FMView{

    radiusBorder sq;
    radiusSqure back;
    public textImg name;
    shapeImg icon;

    public Item(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundResource(0);
      //setBackgroundColor(0xFFCCCCC);
        sq = new radiusBorder(width*0.85f,height*0.7f,0,0, Ui.cd.getHt(2), Ui.cd.getHt(13));
        sq.InCenter(width,height);
        sq.setY((int) (sq.x/2f));
        sq.setColor(itemBack.Color0);
        addShape(sq);

        back = new radiusSqure(sq.width - Ui.cd.getHt(4),sq.height - Ui.cd.getHt(4),0,0, Ui.cd.getHt(11));
        back.InCenter(sq);
       // back.setY((int) (sq.x/2f));
        back.setColor(btmBack.Color0);
        addShape(back);

        /*listName = textImg.getText("ALL SONG",Ui.cd.getHt(14));
        listName.InCenter(width,height);
        listName.setY((int) (height - listName.height));
        addShape(listName);

        icon = artistIcon.getShape();
        icon.InCenter(sq.width,sq.height);
        icon.InCenter(sq);
        addShape(icon);*/
        setRipple(true,0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.postDraw(canvas);
        super.drawShape(canvas);
        super.afterDraw(canvas,back.S0);
    }

    public void setData(String text, shapeImg icon){
        name = textImg.getText(text, Ui.cd.getHt(14));
        name.InCenter(width,height);
        name.setY((int) (height - name.height));
        addShape(name);

        this.icon = icon;
        //icon = artistIcon.getShape();
        icon.InCenter(sq.width,sq.height);
        icon.InCenter(sq);
        addShape(icon);
    }

}
