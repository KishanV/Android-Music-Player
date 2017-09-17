package Views.Home.Equalizer.eqlizerClasse;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.data.EqlizerSettings;
import com.player.data.catchBase;
import com.shape.home.equalizer.bandText;
import com.shape.home.equalizer.menuIcon;
import com.shape.home.equalizer.radioBtnOff;
import com.shape.home.equalizer.radioBtnOn;
import com.shape.home.equalizer.resetIcon;
import com.shape.home.equalizer.savebtnRingColor;
import com.shape.home.equalizer.stickBack;
import com.shape.home.equalizer.stickBtnBack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Views.ContentHome;
import Views.Home.Equalizer.Home;
import Views.Home.QuickEq.WheelItem;
import Views.Popups.getText;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.radiusBorder;
import Views.radiusSqure;
import Views.textImg;


public class equalizer extends FMlyt {

    public equalizer(Context context, final int width, final int height) {
        super(context, width, height);
        setBackgroundColor(0x00CCCCCC);

        textImg title = new textImg(0, Ui.cd.getHt(50), Ui.cd.getHt(20),0, Ui.cd.getHt(16));
        title.setText("EQUALIZER");
        addShape(title);

        presetBtn pBtn = new presetBtn(getContext(),0, Ui.cd.getHt(35));
        pBtn.setY(height - pBtn.height - Ui.cd.getHt(10));
        pBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final popupList fl = new popupList(getContext(),width,height){
                    @Override
                    public void onReload() {
                        equalizer.this.loadBandLavel();
                    }
                };
                addView(fl);
                Ui.bk.add(new call(){
                    @Override
                    public void onCall(boolean bl) {
                        removeView(fl);
                    }
                });
            }
        });
        addView(pBtn);

        resetBtn RBtn = new resetBtn(getContext(),0, Ui.cd.getHt(35));
        RBtn.setY(height - pBtn.height - Ui.cd.getHt(10));
        RBtn.setX(Ui.cd.getHt(10));
        addView(RBtn);

        saveBtn sBtn = new saveBtn(getContext(),0, Ui.cd.getHt(35));
        sBtn.setX(width - sBtn.width - Ui.cd.getHt(10));
        sBtn.setY(height - pBtn.height - Ui.cd.getHt(10));
        sBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final getText gt = new getText(getContext(), Ui.cd.DPW, Ui.cd.DPH,"Enter Preset Name."){
                    @Override
                    public boolean onEnter(String str) {
                       if(!str.startsWith(" ") && str.length() != 0){
                           EqlizerSettings Es = Ui.ef.MusicPlayer.handler.EQs;
                           customPreset.add(new String[]{str,  Es.BAND_01 + " " + Es.BAND_02 + " " + Es.BAND_03 + " " + Es.BAND_04 + " " + Es.BAND_05});
                           catchData.saveCatch();
                           return true;
                       }
                        return super.onEnter(str);
                    }
                };
                ContentHome.This.addPopup(gt);
                Ui.bk.add(new call(){
                    @Override
                    public void onCall(boolean bl) {
                        super.onCall(bl);
                        ContentHome.This.removePopup(gt);
                        InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(ContentHome.This.getWindowToken(), 0);
                    }
                });
            }
        });
        addView(sBtn);

        pBtn.setX(width - sBtn.width - pBtn.width - Ui.cd.getHt(20));
        init(Ui.cd.getHt(60),height - Ui.cd.getHt(120));
        loadBandLavel();

        final radioBtn rBtn = new radioBtn(getContext(), Ui.cd.getHt(95), Ui.cd.getHt(40));
        rBtn.InCenter(width, Ui.cd.getHt(50));
        rBtn.setX(width - rBtn.width - Ui.cd.getHt(5));
        addView(rBtn);

        RBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.ef.MusicPlayer.handler.resetEQs();
                loadBandLavel();
                rBtn.switchBtn(Ui.ef.MusicPlayer.handler.EQs.isOn);
            }
        });

        catchData = new catchBase(Ui.ef.getBaseContext(),"EQpreset","3"){
            @Override
            public void onRead(DataInputStream din) throws IOException {
                custom = din.readUTF();
                customPreset = readArrayList(din);
                if(customPreset == null){
                    customPreset = new ArrayList<String[]>();
                }
            }

            @Override
            public void onWrite(DataOutputStream dout) throws IOException {
                EqlizerSettings Es = Ui.ef.MusicPlayer.handler.EQs;
                custom = Es.BAND_01 + " " + Es.BAND_02 + " " + Es.BAND_03 + " " + Es.BAND_04 + " " + Es.BAND_05;
                dout.writeUTF(custom);
                writeArrayList(dout,customPreset);
            }
        };
        catchData.readCatch();
    }

    catchBase catchData;
    ArrayList<String[]> customPreset = new ArrayList<String[]>();
    public  String custom = "0 0 0 0 0";

    ArrayList<bandStick> bands = new ArrayList<bandStick>();

    void loadBandLavel(){
        for(int i = 0;i < bands.size();i++){
            bands.get(i).setVal(-Ui.ef.MusicPlayer.handler.getBandLavel(i));
            bands.get(i).invalidate();
        }
    }

    private void init(int top , int btmHt) {
        int len = 5;
        int line = 1;
        int spaceWidth = 0;
        int paddingWidth = Ui.cd.getHt(30);
        int itemWidth = (int)(((float)width - (Ui.cd.getHt(spaceWidth) * (len + 1)) - (paddingWidth * 2f)) / len);
        int itemHeight = btmHt;
        int iconSize = (int)(itemWidth * 0.7f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                bandStick item = null;



                switch(i){
                    case 0:
                        item = new bandStick(getContext(),itemWidth,itemHeight,"60HZ") ;
                        break;
                    case 1:
                        item = new bandStick(getContext(),itemWidth,itemHeight,"230HZ") ;
                        break;
                    case 2:
                        item = new bandStick(getContext(),itemWidth,itemHeight,"910HZ") ;
                        break;
                    case 3:
                        item = new bandStick(getContext(),itemWidth,itemHeight,"3kHZ") ;
                        break;
                    case 4:
                        item = new bandStick(getContext(),itemWidth,itemHeight,"14KHZ") ;
                        break;
                }
                item.setX(paddingWidth + (i * itemWidth) + (i * spaceWidth) + spaceWidth);
                item.setY(top);
                addView(item);
                item.setNO(i);
                bands.add(item);
            }
        }
    }

    class bandStick extends  FMView {

        radiusSqure btn;
        radiusBorder border;

        public bandStick(Context context, int width, int height,String khz) {
            super(context, width, height);
            //setBackgroundColor(0xFFcccccc);

            radiusSqure rs = new radiusSqure(Ui.cd.getHt(3),height - Ui.cd.getHt(20),0,0, Ui.cd.getHt(1));
            rs.InCenter(width,height);
            rs.setY(0);
            rs.setColor(stickBack.Color0);
            addShape(rs);

            btn = new radiusSqure(width - Ui.cd.getHt(24), Ui.cd.getHt(50), Ui.cd.getHt(12),0, Ui.cd.getHt(13));
            btn.setColor(stickBtnBack.Color0);
            btn.setX((int) ((width - btn.width) / 2f));
            btn.setY((int) -(btn.height/2f));
            //addShape(btn);

            border = new radiusBorder(width - Ui.cd.getHt(24), Ui.cd.getHt(50), Ui.cd.getHt(12),0, Ui.cd.getHt(2), Ui.cd.getHt(13));
            border.setColor(savebtnRingColor.Color0);
            border.setX((int) ((width - border.width) / 2f));
            border.setY((int) -(border.height/2f));
            //addShape(border);

            textImg text = new textImg(0,0,0,0 , Ui.cd.getHt(14));
            text.setText(khz);
            text.setColor(bandText.Color0);
            text.InCenter(width,0);
            text.setY((int) (height - text.height));
            addShape(text);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.translate(0,((height- Ui.cd.getHt(20)) / 2f) + (top));
            btn.draw(canvas);
            border.draw(canvas);
        }

        int top = 0;
        void setVal(int val){
            top = (int)((((height- Ui.cd.getHt(20 + 50)) / 2f) / 1500f) * (val));
            //Log.i("My","Fre : " + val);
        }

        @Override
        public void onUp(MotionEvent event) {
            super.onUp(event);
            Ui.ef.MusicPlayer.handler.EQs.EQ_PRESETS = 10;
            Ui.ef.MusicPlayer.handler.EQs.save();
            catchData.saveCatch();
        }

        @Override
        public void onMove(MotionEvent event){
            super.onMove(event);
            int Y = (int) event.getY();
            if(Y < Ui.cd.getHt(25)){
                Y = Ui.cd.getHt(25);
            }else if(Y > height - Ui.cd.getHt(20 + 25)){
                Y = height - Ui.cd.getHt(20 + 25);
            }
            Y -= ((height- Ui.cd.getHt(20)) / 2f);
            top = Y;
            setVal((int)((1500f / ((height- Ui.cd.getHt(20 + 50)) / 2f)) * Y));
            Ui.ef.MusicPlayer.handler.setBandLavel((int)NO,-(int)((1500f / ((height- Ui.cd.getHt(20 + 50)) / 2f)) * Y));
            invalidate();
        }

        int NO;
        public void setNO(int NO) {
            this.NO = NO;
        }
    }

    private void addWHeel(int top , int btmHt) {
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
                            item = new WheelItem(getContext(),itemWidth,itemHeight) ;
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("BASS");
                            break;
                        case 1:
                            item = new WheelItem(getContext(),itemWidth,itemHeight) ;
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("VOISE");
                            break;
                        case 2:
                            item = new WheelItem(getContext(),itemWidth,itemHeight) ;
                            item.setX((i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("TRABLE");
                            break;
                        default:
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(Home.rs.S0);
        super.drawShape(canvas);
    }

    class presetBtn extends FMView {
        radiusBorder rs;
        radiusSqure rsBack;
        public presetBtn(Context context, int width, int height) {
            super(context, width, height);

            menuIcon mi = new menuIcon(Ui.cd.getHt(30),height, Ui.cd.getHt(10),0);
            addShape(mi);

            textImg lable = new textImg(0,0,0,0, Ui.cd.getHt(14));
            lable.setText("PRESET");
            lable.InCenter(0,height);
            lable.setX((int) (mi.x + mi.width));
            addShape(lable);

            setSize((int) (lable.width + lable.x +  Ui.cd.getHt(20)),height);


            rs = new radiusBorder((int)this.width,(int)this.height,0,0, Ui.cd.getHt(2), Ui.cd.getHt(13));
            rs.setColor(0x33FFFFFF);
            addShape(rs);

            rsBack = new radiusSqure((int)this.width,(int)this.height,0,0, Ui.cd.getHt(13));
            rsBack.setColor(0x33FFFFFF);

            setRipple(true,0.5f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.postDraw(canvas);
            super.drawShape(canvas);
            super.afterDraw(canvas,rsBack.S0);
        }
    }

    class resetBtn extends FMView {
        radiusBorder rs;
        radiusSqure rsBack;
        public resetBtn(Context context, int width, int height) {
            super(context, width, height);

            resetIcon mi = new resetIcon(height,height, Ui.cd.getHt(2),0);
            addShape(mi);

            textImg lable = new textImg(0,0,0,0, Ui.cd.getHt(14));
            lable.setText("RESET");
            lable.setColor(resetIcon.Color1);
            lable.InCenter(0,height);
            lable.setX((int) (mi.x + mi.width));
            addShape(lable);

            setSize((int) (lable.width + lable.x +  Ui.cd.getHt(10)),height);


            rsBack = new radiusSqure((int)this.width,(int)this.height,0,0, Ui.cd.getHt(13));
            rsBack.setColor(0x33FFFFFF);

            setRipple(true,0.5f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.postDraw(canvas);
            super.drawShape(canvas);
            super.afterDraw(canvas,rsBack.S0);
        }
    }

    class saveBtn extends FMView {
        radiusBorder rs;
        radiusSqure rsBack;
        public saveBtn(Context context, int width, int height) {
            super(context, width, height);

            textImg lable = new textImg(0,0,0,0, Ui.cd.getHt(14));
            lable.setText("SAVE");
            lable.InCenter(0,height);
            lable.setX(Ui.cd.getHt(20));
            addShape(lable);
            setSize((int) (lable.width + Ui.cd.getHt(40)),height);

            rs = new radiusBorder((int)this.width,(int)this.height,0,0, Ui.cd.getHt(2), Ui.cd.getHt(13));
            rs.setColor(savebtnRingColor.Color0);
            addShape(rs);



            rsBack = new radiusSqure((int)this.width,(int)this.height,0,0, Ui.cd.getHt(13));
            rsBack.setColor(0x33FFFFFF);

            setRipple(true,0.5f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.postDraw(canvas);
            super.drawShape(canvas);
            super.afterDraw(canvas,rsBack.S0);
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
            //setText("OFF");

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchBtn(rOff.drawing);
                    Ui.ef.MusicPlayer.handler.setEQs(rOn.drawing);
                    Ui.ef.MusicPlayer.handler.EQs.save();
                    loadBandLavel();

                }
            });
            switchBtn(Ui.ef.MusicPlayer.handler.EQs.isOn);
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
            txt.draw(canvas);
        }
    }

}
