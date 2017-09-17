package Views.Library.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.allsong.backcloseBtn;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.itemRect;

import Views.ContentHome;
import Views.Library.Menu.LibreryClass.libreryAdapter;
import Views.Library.Menu.LibreryClass.topView;
import Views.Popups.getText;
import Views.api.FMView;
import Views.radiusSqure;
import Views.textImg;

public class Librery extends pagePrototype {

    topView TopView;
    radiusSqure squreTop;

    FMView addBtn;
    radiusSqure btnSqure;
    textImg fmText;
    ListView listview;

    public Librery(final Context context, int width, int height,int id) {
        super(context, width, height,id);
        setBackgroundColor(com.shape.home.backgroundImg.Color0);
        TopView = new topView(context, Ui.cd.getHt(25), Ui.cd.getHt(60));
        TopView.setBackgroundColor(0x00FFFFFF);
        TopView.setX((width - TopView.width) / 2);
        addView(TopView);

        addBtn = new FMView(getContext(), (int) (width * 0.35f), Ui.cd.getHt(50)){
            @Override
            protected void onDraw(Canvas canvas) {
                btnSqure.draw(canvas);
                super.postDraw(canvas);
                squreTop.draw(canvas);
                canvas.clipPath(btnSqure.S0);
                fmText.draw(canvas);
                super.afterDraw(canvas,btnSqure.S0);
                //super.onDraw(canvas);
            }
        };
        addBtn.setBackgroundColor(0x00cccccc);
        addBtn.InCenter(width,0);
        addBtn.setY(height - Ui.cd.getHt(70));
        addBtn.setRipple(true,0.3f);

        btnSqure = new radiusSqure(addBtn.width,addBtn.height,0,0, Ui.cd.getHt(13));
        squreTop = new radiusSqure(addBtn.width - Ui.cd.getHt(4),addBtn.height - Ui.cd.getHt(4), Ui.cd.getHt(2), Ui.cd.getHt(2), Ui.cd.getHt(11));
        fmText = new textImg(addBtn.width,addBtn.height,0,0, Ui.cd.getHt(14));
        fmText.setText("NEW PLAYLIST",true);

        btnSqure.setColor(backcloseBtn.Color0);
        squreTop.setColor(itemRect.Color0);

        listview = new ListView(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
            }
        };
        listview.setLayoutParams(new FrameLayout.LayoutParams(width, height - Ui.cd.getHt(60)));
        listview.setY(Ui.cd.getHt(60));
        listview.setDivider(null);
        listview.setBackgroundColor(itemBack.Color0);
        data = new libreryAdapter(){
            @Override
            public void onRerload() {
               if(listview != null){
                   listview.invalidateViews();
               }
            }
        };
        listview.setAdapter(data);
        //listview.setBackgroundColor(backgroundImg.Color0);
        addView(listview);
        addView(addBtn);

        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final getText gt = new getText(context, Ui.cd.DPW, Ui.cd.DPH,"ENTER TEXT FOR PLAYLIST"){
                    @Override
                    public boolean onEnter(String str) {
                        if(!data.isExist(str)){
                            playlistHandler.addNewPlaylist(Ui.ef.getContentResolver(),str);
                            data.reloadData();
                            return  true;
                        }
                        return super.onEnter(str);
                    }
                };
                ContentHome.This.addPopup(gt);
                gt.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Ui.bk.back();
                    }
                });
                Ui.bk.add(new call() {
                    @Override
                    public void onCall(boolean bl) {
                        InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(ContentHome.This.getWindowToken(), 0);
                        ContentHome.This.removePopup(gt);
                    }
                });
            }
        });
    }

    libreryAdapter data;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
