package Views.Home.PlayList.PlayListClass;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.home.playlist.popup.bodyBackground;
import com.shape.home.playlist.popup.selectedBtn;

import java.util.ArrayList;
import java.util.List;

import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.squre;
import Views.textImg;

public class playListsAdapter extends BaseAdapter {

    public List<String[]> data = new ArrayList<String[]>();
    FMlyt layout;
    public playListsAdapter(FMlyt layout){
        this.layout = layout;
        data = playlistHandler.getALlPlalists(Ui.ef.getContentResolver());

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
        return new Item(parent.getContext(),position);
    }

    Item selItem;
    class Item extends FMlyt{
        public squre btnLine;
        FMText no;
        FMText name;


        public Item(Context context, final int position) {
            super(context,layout.width, Ui.cd.getHt(44));
            boolean sel = false;
            if(data.get(position)[0].equals(Ui.ef.MusicPlayer.handler.playlist.listName)){
                setBackgroundColor(0xFFFFFFFF);
                sel = true;
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(60)));
            }else{
                setBackgroundColor(0x00D35D69);
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(44)));
            }


            setRipple(true,0.3f);
            setRippleDown(false);

            if(sel){
                ShapeView sh = selectedBtn.getFMview(getContext(),true);
                sh.setX(width - sh.width - Ui.cd.getHt(10));
                sh.setY((Ui.cd.getHt(60) - sh.width) / 2);
                addView(sh);
            }

            no = textImg.getFMText(context,position + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(12));
            no.setY(Ui.cd.getHt(15));
            if(sel){
                no.img.P0.setColor(bodyBackground.Color0);
            }else{
                no.img.P0.setColor(0x66FFFFFF);
            }
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0].toUpperCase(), Ui.cd.getHt(18));
            name.setX(no.width + Ui.cd.getHt(12 + 5));
            name.setY((Ui.cd.getHt(44) - name.height) / 2);
            if(layout.width - Ui.cd.getHt(80) < name.width){
                name.setSize((int) (layout.width - Ui.cd.getHt(80)),name.height);
                if(sel){
                    name.img.setEfects(new int[]{bodyBackground.Color0, bodyBackground.Color0, 0x00FFFFFF});
                }else {
                    name.img.setEfects(new int[]{0xFFFFFFFF, 0xFFFFFFFF, 0x00FFFFFF});
                }
            }else{
                if(sel){
                    name.img.P0.setColor(bodyBackground.Color0);
                }else{
                    name.img.P0.setColor(0xFFFFFFFF);
                }
            }
            name.setClickable(false);
            addView(name);
            if(sel){
                FMText nowText = textImg.getFMText(getContext(),"PLAYING NOW", Ui.cd.getHt(12));
                nowText.setX(name.getX());
                nowText.setY(name.getY() + name.height + Ui.cd.getHt(8));
                nowText.setAlpha(0.5f);
                nowText.img.P0.setColor(bodyBackground.Color0);
                addView(nowText);
            }
            btnLine = new squre(layout.width, Ui.cd.getHt(2),0, Ui.cd.getHt(44-2));
            btnLine.setColor(0x12000000);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ui.ef.clickPlay();
                    Ui.bk.back();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Ui.ef.MusicPlayer.handler.playByPlaylistId(data.get(position)[1]);

                            Ui.ef.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(selItem != null){
                                        selItem.normal();
                                        selItem.invalidate();
                                    }
                                    selItem = null;
                                    selItem = Item.this;
                                    selItem.select();
                                    selItem.invalidate();
                                }
                            });

                        }
                    }).start();
                }
            });
            if(Ui.ef.MusicPlayer.handler.PID == position){
                selItem = this;
            }

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
            //btnLine.draw(canvas);
        }

    }

}
