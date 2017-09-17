package Views.Library.Menu.SongsClass;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.linedeer.player.Ui;
import com.player.playlistHandler;
import com.shape.Library.popup.listItemSelected;
import com.shape.Library.popup.listItemTop;
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
        if(Ui.ef.MusicPlayer.handler.playlist.id == -1){
            data.add(0,new String[]{Ui.ef.MusicPlayer.handler.playlist.listName + " ( UNSAVED )","-1"});
        }
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

    public void onSelect(String Id){

    }

    Item selItem;
    class Item extends FMlyt{
        public squre btnLine;
        FMText no;
        FMText name;
        FMText artist;
        squre back;
        public Item(Context context, final int position) {
            super(context,layout.width, Ui.cd.getHt(44));
            boolean sel = false;

            if(data.get(position)[1].equals(Ui.ef.MusicPlayer.handler.playlist.id+"")){
                sel = true;
                setSize(layout.width, Ui.cd.getHt(60));
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(60)));
                back = new squre(width,height - Ui.cd.getHt(2),0, Ui.cd.getHt(0));
                back.setColor(listItemSelected.Color0);
            }else{
                setSize(layout.width, Ui.cd.getHt(44));
                setLayoutParams(new ListView.LayoutParams(layout.width, Ui.cd.getHt(44)));
                back = new squre(width,height - Ui.cd.getHt(2),0, Ui.cd.getHt(0));
                back.setColor(listItemTop.Color0);
            }


            addShape(back);
            setBackgroundColor(0x00FFFFFF);

            setRipple(true,0.3f);
            setRippleDown(false);

            if(sel){
                ShapeView sh = selectedBtn.getFMview(getContext(),true);
                sh.setX(width - sh.width - Ui.cd.getHt(10));
                sh.setY((Ui.cd.getHt(60) - sh.width) / 2);
                addView(sh);
            }

            no = textImg.getFMText(context,(position + 1) + ".", Ui.cd.getHt(14));
            no.setX(Ui.cd.getHt(12));
            no.setY(Ui.cd.getHt(15));
            if(sel){
                no.img.P0.setColor(0x66FFFFFF);
            }else{
                no.img.P0.setColor(0x66FFFFFF);
            }
            no.setClickable(false);
            addView(no);

            name = textImg.getFMText(context,data.get(position)[0].toUpperCase(), Ui.cd.getHt(16));
            name.setX(no.width + Ui.cd.getHt(12 + 5));
            name.setY((Ui.cd.getHt(44) - name.height) / 2);
            if(layout.width - Ui.cd.getHt(80) < name.width){
                name.setSize((int) (layout.width - Ui.cd.getHt(80)),name.height);
                if(sel){
                    name.img.setEfects(new int[]{0xFFFFFFFF, 0xFFFFFFFF, 0x00FFFFFF});
                }else {
                    name.img.setEfects(new int[]{0xFFFFFFFF, 0xFFFFFFFF, 0x00FFFFFF});
                }
            }else{
                if(sel){
                    name.img.P0.setColor(0xFFFFFFFF);
                }else{
                    name.img.P0.setColor(0xFFFFFFFF);
                }
            }
            name.setClickable(false);
            addView(name);
            if(sel){
                FMText nowText;
                if((Ui.ef.MusicPlayer.handler.playlist.id+"").equals("-1")){
                    nowText = textImg.getFMText(getContext(),"NOW PLAYING ( AUTO GENRATED PLAYLIST )", Ui.cd.getHt(12));
                }else{
                    nowText = textImg.getFMText(getContext(),"NOW PLAYING", Ui.cd.getHt(12));
                }

                nowText.setX(name.getX());
                nowText.setY(name.getY() + name.height + Ui.cd.getHt(8));
                nowText.setAlpha(0.5f);
                nowText.img.P0.setColor(0xFFFFFFFF);
                addView(nowText);
            }
            btnLine = new squre(layout.width, Ui.cd.getHt(2),0, Ui.cd.getHt(44-2));
            btnLine.setColor(0x12000000);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!data.get(position)[1].equals("-1")){
                        Ui.ef.clickPlay();
                        onSelect(data.get(position)[1]);
                    }

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
