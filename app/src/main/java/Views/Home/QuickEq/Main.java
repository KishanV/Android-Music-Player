package Views.Home.QuickEq;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.api.EventCall;
import com.linedeer.player.Ui;
import com.player.playerEvents;
import com.shape.home.QEq.backgroundImg;
import com.shape.home.QEq.btmBack;
import com.shape.home.QEq.textColor;
import com.shape.home.QEq.titleBack;
import com.shape.home.equalizer.bandText;
import com.shape.home.equalizer.radioBtnOff;
import com.shape.home.equalizer.radioBtnOn;

import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.radiusSqure;
import Views.squre;
import Views.textImg;

public class Main extends FMlyt{

    public static Main  This;
    public Main(Context context, int width, int height) {
        super(context, width, height);
        setBackgroundColor(0x00000000);
        This = this;

        squre sqTitle = new squre(width, Ui.cd.getHt(50),0,0);
        sqTitle.setColor(titleBack.Color0);
        addShape(sqTitle);

        squre btmSq = new squre(width, Ui.cd.getHt(120),0,height  - Ui.cd.getHt(120 + 60));
        btmSq.setColor(btmBack.Color0);
        addShape(btmSq);

        squre sq = new squre(width,height - Ui.cd.getHt(50) - btmSq.height + - Ui.cd.getHt(60),0, Ui.cd.getHt(50));
        sq.setColor(backgroundImg.Color0);
        addShape(sq);

        squre bmLine = new squre(width, Ui.cd.getHt(2) ,0,btmSq.y+ btmSq.height);
        bmLine.setColor(backgroundImg.Color0);
        addShape(bmLine);

        squre volumeBack = new squre(width, Ui.cd.getHt(60-2) ,0,bmLine.y+ bmLine.height);
        volumeBack.setColor(btmBack.Color0);
        addShape(volumeBack);

        VolumeRocker Vr = new VolumeRocker(getContext(),width,(int)volumeBack.height);
        Vr.setBackgroundResource(0);
        Vr.setY(volumeBack.y);

        addView(Vr);

        FMText title = textImg.getFMText(context,"Equalizer Presets", Ui.cd.getHt(18));
        title.img.setColor(textColor.Color0);
        title.InCenter(width, Ui.cd.getHt(50));
        title.setX(Ui.cd.getHt(10));
        addView(title);

        addWhells((int) btmSq.y, (int) btmSq.height);

        presetAdapter data;
        listview = new ListView(getContext());
        listview.setLayoutParams(new FrameLayout.LayoutParams((int)sq.width,(int)sq.height));
        listview.setY(sq.y);
        listview.setDivider(null);
        listview.setBackgroundResource(0);
        //listview.setBackgroundColor(com.shape.home.slider.backgroundImg.Color0);
        data = new presetAdapter(){
            @Override
            public void onReload(){
                if(listview != null){
                    refreshData();
                }
            }
        };
        listview.setAdapter(data);
        addView(listview);

        final radioBtn rBtn = new radioBtn(getContext(), Ui.cd.getHt(95), Ui.cd.getHt(40));
        rBtn.InCenter(width, Ui.cd.getHt(50));
        rBtn.setX(width - rBtn.width - Ui.cd.getHt(5));
        addView(rBtn);


        Ui.ef.playerEvent.addEvent(new EventCall(new int[]{playerEvents.EQ_CHANGED, Ui.ef.Event_onBind}){
            @Override
            public void onCall(int eventId) {
                refreshData();
                rBtn.switchBtn(Ui.ef.MusicPlayer.handler.EQs.isOn);
            }
        });

    }

    ListView listview;
    WheelItem bass;
    WheelItem voice;
    WheelItem trable;


    void refreshData(){
        listview.invalidateViews();
        listview.invalidate();

        bass.setVal(Ui.ef.MusicPlayer.handler.EQs.BASS);
        bass.invalidate();

        voice.setVal(Ui.ef.MusicPlayer.handler.EQs.VOICE);
        voice.invalidate();

        trable.setVal(Ui.ef.MusicPlayer.handler.EQs.TRABLE);
        trable.invalidate();
    }

    private void addWhells(int top , int btmHt) {
        int len = 3;
        int line = 1;
        int spaceWidth = Ui.cd.getHt(0);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)width - Ui.cd.getHt(spaceWidth * (len + 1))) / len);
        int itemHeight = btmHt - Ui.cd.getHt(20);
        int iconSize = (int)(itemWidth * 0.7f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                if((j*len) + i < 6){
                    WheelItem item;
                    switch (i){
                        case 0:
                            item = new WheelItem(getContext(),itemWidth,itemHeight){
                                @Override
                                public void onVal(int rval) {
                                    super.onVal(rval);
                                    Ui.ef.MusicPlayer.handler.setBass(rval);
                                }

                                @Override
                                public void onUp(MotionEvent event) {
                                    Ui.ef.MusicPlayer.handler.EQs.save();
                                    super.onUp(event);
                                }
                            };
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("BASS");
                            bass = item;
                            break;
                        case 1:
                            item = new WheelItem(getContext(),itemWidth,itemHeight){
                                @Override
                                public void onVal(int rval) {
                                    super.onVal(rval);
                                    Ui.ef.MusicPlayer.handler.setVoice(rval);
                                }

                                @Override
                                public void onUp(MotionEvent event) {
                                    Ui.ef.MusicPlayer.handler.EQs.save();
                                    super.onUp(event);
                                }
                            };
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("SOFTNESS");
                            voice = item;
                            break;
                        case 2:
                            item = new WheelItem(getContext(),itemWidth,itemHeight){
                                @Override
                                public void onVal(int rval) {
                                    super.onVal(rval);
                                    Ui.ef.MusicPlayer.handler.setTrable(rval);
                                }

                                @Override
                                public void onUp(MotionEvent event) {
                                    Ui.ef.MusicPlayer.handler.EQs.save();
                                    super.onUp(event);
                                }
                            };
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("TRABLE");
                            trable = item;
                            break;
                        default:
                    }
                }
            }
        }
    }

    class radioBtn extends FMView {

        radioBtnOn rOn;
        radioBtnOff rOff;
        radiusSqure rs;
        textImg txt;

        public radioBtn(Context context, int width, int height) {
            super(context, width, height);
            rOn = new radioBtnOn(Ui.cd.getHt(50), Ui.cd.getHt(25), 0, 0);
            rOn.InCenter(width,height);
            rOn.setX((int) (width - rOn.width - Ui.cd.getHt(5)));
            addShape(rOn);

            rOff = new radioBtnOff(Ui.cd.getHt(50), Ui.cd.getHt(25), 0, 0);
            addShape(rOff);
            rOff.InCenter(width,height);
            rOff.setX((int) (width - rOff.width - Ui.cd.getHt(5)));
            setRipple(true, 0.5f);

            rs = new radiusSqure(width, height, 0, 0, Ui.cd.getHt(14));
            setText("OFF");

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchBtn(rOff.drawing);
                    Ui.ef.MusicPlayer.handler.setEQs(rOn.drawing);
                    Ui.ef.MusicPlayer.handler.EQs.save();

                }
            });

        }

        public void switchBtn(boolean val){
            if(!val){
                rOn.drawing = false;
                rOff.drawing = true;
                setText("OFF");
            }else{
                rOn.drawing = true;
                rOff.drawing = false;
                setText("ON");
            }
            invalidate();
        }

        void setText(String name){
            txt = new textImg(0,0,0,0, Ui.cd.getHt(14));
            txt.setColor(bandText.Color0);
            txt.setText(name);
            txt.InCenter(0,height);
            txt.setX((int) (width - Ui.cd.getHt(50 + 15) - txt.width));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.clipPath(rs.S0);
            super.onDraw(canvas);
            if(txt != null){
                txt.draw(canvas);
            }
        }
    }
}
