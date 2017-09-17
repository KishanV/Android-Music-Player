package Views.Home.Equalizer.eqlizerClasse;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.linedeer.player.Ui;
import com.shape.home.equalizer.bandText;
import com.shape.home.equalizer.menuIcon;
import com.shape.home.equalizer.savebtnRingColor;
import com.shape.home.equalizer.stickBack;
import com.shape.home.equalizer.stickBtnBack;

import java.util.ArrayList;

import Views.Home.Equalizer.Home;
import Views.Home.QuickEq.WheelItem;
import Views.api.FMView;
import Views.api.FMlyt;
import Views.radiusBorder;
import Views.radiusSqure;
import Views.textImg;


public class tunner extends FMlyt {

    public tunner(Context context, final int width, final int height) {
        super(context, width, height);
        setBackgroundColor(0x00CCCCCC);



        textImg title = new textImg(width, Ui.cd.getHt(50),0,0, Ui.cd.getHt(16));
        title.setText("TONE",true);
        addShape(title);


       /* presetBtn pBtn = new presetBtn(getContext(),0,Ui.cd.getHt(35));
        pBtn.setX(Ui.cd.getHt(10));
        pBtn.setY(height - pBtn.height - Ui.cd.getHt(10));
        pBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final popupList fl = new popupList(getContext(),width,height){
                    @Override
                    public void onReload() {
                        //tunner.this.loadBandLavel();
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

        saveBtn sBtn = new saveBtn(getContext(),0,Ui.cd.getHt(35));
        sBtn.setX(width - sBtn.width - Ui.cd.getHt(10));
        sBtn.setY(height - pBtn.height - Ui.cd.getHt(10));
        addView(sBtn);
*/
        addWHeel(Ui.cd.getHt(50), Ui.cd.getHt(100));
        addReverb(Ui.cd.getHt(50+130), Ui.cd.getHt(100));

        setState();
    }
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
                bandStick item;
                item = new bandStick(getContext(),itemWidth,itemHeight) ;
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

        public bandStick(Context context, int width, int height) {
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
            text.setText("24KH");
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
        public void onMove(MotionEvent event) {
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

    WheelItem bass;
    WheelItem trable;
    WheelItem voice;
    WheelItem loudness;
    WheelItem virchulizer;

    private void setState() {
        bass.setVal(Ui.ef.MusicPlayer.handler.EQs.BASS);
        trable.setVal(Ui.ef.MusicPlayer.handler.EQs.TRABLE);
        voice.setVal(Ui.ef.MusicPlayer.handler.EQs.VOICE);
        loudness.setVal(Ui.ef.MusicPlayer.handler.EQs.LOUDNESS);
        virchulizer.setVal(Ui.ef.MusicPlayer.handler.EQs.VIRCHULIZER);
    }

    private void addWHeel(int top , int btmHt) {
        int len = 3;
        int line = 1;
        int spaceWidth = Ui.cd.getHt(0);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)width - Ui.cd.getHt(spaceWidth * (len + 1))) / len);
        int itemHeight = btmHt;
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
                                    super.onUp(event);
                                    Ui.ef.MusicPlayer.handler.EQs.save();
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
                                    super.onUp(event);
                                    Ui.ef.MusicPlayer.handler.EQs.save();
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
                                    super.onUp(event);
                                    Ui.ef.MusicPlayer.handler.EQs.save();
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
    private void addReverb(int top , int btmHt) {
        int len = 3;
        int line = 1;
        int spaceWidth = Ui.cd.getHt(0);
        int paddingWidth = Ui.cd.getHt(15);
        int itemWidth = (int)(((float)width - Ui.cd.getHt(spaceWidth * (len + 1))) / len);
        int itemHeight = btmHt;
        int iconSize = (int)(itemWidth * 0.7f);
        int left = (int) (itemWidth/2f);

        for(int i = 0;i < len;i++){
            for(int j = 0;j < line;j++){
                if((j*len) + i < 2){
                    WheelItem item;
                    switch (i){
                        case 0:
                            item = new WheelItem(getContext(),itemWidth,itemHeight){
                                @Override
                                public void onVal(int rval) {
                                    super.onVal(rval);
                                    Ui.ef.MusicPlayer.handler.setLoudnessEnhancer(rval);
                                }

                                @Override
                                public void onUp(MotionEvent event) {
                                    super.onUp(event);
                                    Ui.ef.MusicPlayer.handler.EQs.save();
                                }
                            };
                            item.setX(left + (i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("LOUDNESS");
                            loudness = item;
                            break;
                        case 1:
                            item = new WheelItem(getContext(),itemWidth,itemHeight){
                                @Override
                                public void onVal(int rval) {
                                    super.onVal(rval);
                                    Ui.ef.MusicPlayer.handler.setVirtualizer(rval);
                                }

                                @Override
                                public void onUp(MotionEvent event) {
                                    super.onUp(event);
                                    Ui.ef.MusicPlayer.handler.EQs.save();
                                }
                            };
                            item.setX(left + (i * itemWidth) + (i * spaceWidth) + spaceWidth);
                            item.setY(top + Ui.cd.getHt(10));
                            addView(item);
                            item.setName("3D AUDIO");
                            virchulizer = item;
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
}
