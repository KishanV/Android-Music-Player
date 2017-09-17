package Views.Home.PlayList.PlayListClass;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.home.playlist.songDrager;
import com.shape.home.playlist.songMenu;

import java.util.ArrayList;
import java.util.List;

import Views.ContentHome;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class songAdapter extends BaseAdapter {

    public List<String[]> data = new ArrayList<String[]>();
    public List<String[]> catchData;
    FMlyt layout;
    public songAdapter(FMlyt layout){
        this.layout = layout;
        reLoad();
    }

    public void reLoad(){
        if(Ui.ef.MusicPlayer.handler.list != null){
            data = Ui.ef.MusicPlayer.handler.list;
            catchData = data;
        }
    }


    void search(String strStr){
        if(strStr == null){
            data = catchData;
        }else{
            strStr = (""+strStr).toUpperCase();
            List<String[]> newData = new ArrayList<String[]>();
            int length  = catchData.size();

            for (int i = 0; i < length; i++) {
                String str = catchData.get(i)[0];
                if(str.toUpperCase().contains(strStr)){
                    newData.add(catchData.get(i));
                }
            }
            data = newData;
        }

    }

    void closeSearch(String str){
        reLoad();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null){
            Item item  = (Item) convertView;
            item.setPosition(position);
        }else{
            convertView = new Item(parent.getContext(),position);;
        }
        return convertView;
    }


    class Item extends FMlyt {
        public squre btnLine;
        public radiusSqure rSqure;
        FMText no;
        FMText name;
        FMText artist;
        ShapeView drager;
        ShapeView menu;
        int Id;

        public void setPosition(int position) {
            Id  = position;

            no.setText(position + ".");
            name.setText(data.get(position)[0]);
            name.setX(no.width + Ui.cd.getHt(15+30));
            name.setY(Ui.cd.getHt(15));
            if(layout.width - Ui.cd.getHt(150) < name.width){
                name.setSize((int) (layout.width - Ui.cd.getHt(130)),name.height);
                if(Ui.ef.MusicPlayer.handler.PID == position){
                    name.img.setEfects(new int[]{0xFFEA816E,0xFFEA816E,0x00EA816E});
                }else{
                    name.img.setEfects(new int[]{0x88FFFFFF,0x88FFFFFF,0x00FFFFFF});
                }
            }else{
                if(Ui.ef.MusicPlayer.handler.PID == position){
                    name.img.setColor(0xFFEA816E);
                }else{
                    name.img.setColor(0x88FFFFFF);
                }
            }
            name.invalidate();
            no.invalidate();
        }

        public Item(Context context, final int position) {

            super(context,layout.width, Ui.cd.getHt(44));
            Id  = position;
            if(data.size()-1 == position){
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(42)));
            }else{
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(44)));
            }

            setRipple(true,0.3f);
            setRippleDown(false);

            no = textImg.getFMText(context,position + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(6+30));
            no.setY(Ui.cd.getHt(15));
            no.img.setColor(0x33FFFFFF);
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0] , Ui.cd.getHt(16));
            name.setX(no.width + Ui.cd.getHt(15+30));
            name.setY(Ui.cd.getHt(15));
            if(layout.width - Ui.cd.getHt(150) < name.width){
                name.setSize((int) (layout.width - Ui.cd.getHt(130)),name.height);
                if(Ui.ef.MusicPlayer.handler.PID == position){
                    name.img.setEfects(new int[]{0xFFEA816E,0xFFEA816E,0x00EA816E});
                }else{
                    name.img.setEfects(new int[]{0x88FFFFFF,0x88FFFFFF,0x00FFFFFF});
                }
            }else{
                if(Ui.ef.MusicPlayer.handler.PID == position){
                    name.img.setColor(0xFFEA816E);
                }else{
                    name.img.setColor(0x88FFFFFF);
                }
            }
            name.setClickable(false);
            addView(name);
            setBackgroundColor(0xFF302B4A);
            //setBackgroundColor(0xFF312C4B);

            btnLine = new squre(layout.width, Ui.cd.getHt(2),0, Ui.cd.getHt(44-2));
            btnLine.setColor(0x11000000);
            drager = songDrager.getFMview(getContext(),false);
            addView(drager);

            menu = songMenu.getFMview(getContext(),true);
            menu.setX(width - menu.width);
            menu.setRippleDown(false);
            menu.setRipple(true,0.3f);
            addView(menu);


            final call menuCall = new call(){
                @Override
                public void onCall(boolean bl) {
                    ContentHome.This.MenuHome.drawCatch();
                    final songsBtns mn = new songsBtns(getContext(), Ui.cd.DPW, Ui.cd.DPH,data.get(position)){
                        @Override
                        public void onBtn(String name) {
                            if(name.equals("PLAY")){
                                Ui.ef.MusicPlayer.handler.playByNumber(Id);
                                Ui.bk.back();
                            }else{
                                super.onBtn(name);
                            }

                        }
                    };
                    //mn.fromHtml = true;
                    ContentHome.This.addPopup(mn);
                    mn.setClickable(true);
                    Ui.bk.add(new call() {
                        @Override
                        public void onCall(boolean bl) {
                            ContentHome.This.MenuHome.removeCatch();
                            ContentHome.This.removePopup(mn);
                        }
                    });
                }
            };
            menu.onClick(menuCall);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ui.ef.clickPlay();
                    Ui.ef.MusicPlayer.handler.playByNumber(Id);
                }
            });

        }

        private void normal() {
            if (name.img.hasEffect) {
                name.img.setEfects(new int[]{0x88FFFFFF,0x88FFFFFF,0x00FFFFFF});
            } else {
                name.img.removeEfects();
                name.img.setColor(0x88FFFFFF);
            }
            name.invalidate();
        }

        private void select() {
            if (name.img.hasEffect) {
                name.img.setEfects(new int[]{0xFFEA816E,0xFFEA816E,0x00EA816E});
            } else {
                name.img.removeEfects();
                name.img.setColor(0xFFEA816E);
            }
            name.invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            btnLine.draw(canvas);
        }
    }

}
