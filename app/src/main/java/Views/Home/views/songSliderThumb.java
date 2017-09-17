package Views.Home.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.linedeer.api.ITask;
import com.linedeer.player.Ui;
import com.player.audioHandler;
import com.shape.home.slider.thumbBack;
import com.shape.home.slider.thumbRing;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.radiusSqure;
import Views.textImg;

public class songSliderThumb extends FMlyt {


    radiusSqure RaidiusSqure;
    FMText name;
    public ImageView Iv;
    ShapeView back;
    public ShapeView ring;
    int ID;

    public songSliderThumb(Context context, int width, int height,int ID) {
        super(context, width, height);
        this.ID = ID;
        setBackgroundColor(0x0000000);
        //SongSliderImg = new songSliderImg(width,height,0,0);
        RaidiusSqure = new radiusSqure(width,height,0,0,width*0.1f);
        RaidiusSqure.setColor(0xFFFFFFFF);

        name = textImg.getFMText(getContext(),"DEMO", Ui.cd.getHt(16));

        back = thumbBack.getFMview(getContext(),false);
        back.setSize(width - Ui.cd.getHt(2),height - Ui.cd.getHt(2));
        back.setX(Ui.cd.getHt(1));
        back.setY(Ui.cd.getHt(1));
        back.setClickable(false);
        addView(back);

        Iv = new ImageView(context){
            @Override
            protected void onDraw(Canvas canvas) {
                canvas.clipPath(back.img.mask);
                super.onDraw(canvas);
            }
        };
        Iv.setLayoutParams(new FrameLayout.LayoutParams(width - Ui.cd.getHt(2),height - Ui.cd.getHt(2)));
         addView(Iv);
        Iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Iv.setBackgroundColor(0x00FFFFFF);
        Iv.setX(Ui.cd.getHt(1));
        Iv.setY(Ui.cd.getHt(1));
        //setImg("3.png");

        ring = thumbRing.getFMview(getContext(),false);
        ring.setSize(width,height);
        ring.setClickable(false);
        addView(ring);
        if(android.os.Build.VERSION.SDK_INT <= 18){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

       // addView(name);
    }

    public void setImg(Bitmap preBitmap,int color){
        Iv.setImageDrawable(null);
        Iv.setImageBitmap(null);
        if(preBitmap != null){
            notBitmap = true;
            ring.img.maskPaint.setColor(color);
            Iv.setImageBitmap(preBitmap);
        }else{
            ring.img.maskPaint.setColor(thumbRing.Color0);
        }
        ring.invalidate();
        setAlpha(1);
    }

    int lastADI = -101;
    public static HashMap<Integer, data> catchData = new HashMap<Integer, data>();
    public static void  removeExtra(int aid){
        if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler != null){
            data Data = catchData.get(aid);
            catchData.remove(aid);
            if(Data != null){
                Data.Bm.recycle();
            }
            HashMap<Integer, data> nowData = new HashMap<Integer, data>();

            int A = Ui.ef.MusicPlayer.handler.getAID(aid-2);
            if(catchData.containsKey(A)){
                data nData =  catchData.get(A);
                nowData.put(A,nData);
                catchData.remove(A);
            }

            A = Ui.ef.MusicPlayer.handler.getAID(aid-1);
            if(catchData.containsKey(A)){
                data nData =  catchData.get(A);
                nowData.put(A,nData);
                catchData.remove(A);
            }

            A = Ui.ef.MusicPlayer.handler.getAID(aid);
            if(catchData.containsKey(A)){
                data nData =  catchData.get(A);
                nowData.put(A,nData);
                catchData.remove(A);
            }

            A = Ui.ef.MusicPlayer.handler.getAID(aid+1);
            if(catchData.containsKey(A)){
                data nData =  catchData.get(A);
                nowData.put(A,nData);
                catchData.remove(A);
            }

            A = Ui.ef.MusicPlayer.handler.getAID(aid+2);
            if(catchData.containsKey(A)){
                data nData =  catchData.get(A);
                nowData.put(A,nData);
                catchData.remove(A);
            }

            Iterator it = catchData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                data nDt = (data) pair.getValue();
                nDt.Bm.recycle();
                it.remove();
            }
            catchData = nowData;
        }
    }

    public void saveADI(final int ADI) {
    }

    boolean hasImg = false;

    public int aid = -1;
    public void setADI(final int ADI) {

        hasImg = false;


       // name.setText(ADI + " BY");
        //name.InCenter(width,height);
        //invalidate();
        if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler != null){
            if(ADI < 0 || ADI > Ui.ef.MusicPlayer.handler.list.size()-1){
                setAlpha(0);
            }else{
                setAlpha(1);
            }
        }else{
            setAlpha(1);
        }

        if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler != null){
            int nowAid = Ui.ef.MusicPlayer.handler.getAID(ADI);
            aid = nowAid;
            data dt = catchData.get(nowAid);
            if(dt != null){
                hasImg = true;
                ring.img.maskPaint.setColor(dt.color);
                ring.invalidate();
                Iv.setImageBitmap(dt.Bm);
            }else{
                ring.img.maskPaint.setColor(thumbRing.Color0);
                ring.invalidate();
                Iv.setImageBitmap(null);
            }
        }

        if(!hasImg){
            if(Ui.ef.MusicPlayer != null && Ui.ef.MusicPlayer.handler != null){
                if(!catchData.containsKey(Ui.ef.MusicPlayer.handler.getAID(ADI))){
                    if(task != null){
                        task.stop = true;
                        task.cancel(false);
                    }
                    task = new ITask(){
                        Bitmap bm;
                        int newColor;
                        @Override
                        public void Go() {
                            bm = audioHandler.getAlubumArtBitmapById(Ui.ef.getContentResolver(), Ui.ef.MusicPlayer.handler.getAID(ADI));
                            if(bm != null){
                                Palette palette = Palette.from(bm).generate();
                                int color = thumbRing.Color0;
                                newColor = palette.getMutedColor(color);
                                if(newColor == color){
                                    newColor = palette.getVibrantColor(color);
                                }
                                if(newColor == color){
                                    newColor = palette.getLightVibrantColor(color);
                                }
                            }
                        }

                        @Override
                        public void than() {
                            if(bm != null){
                                data vars = new data();
                                vars.Bm = bm;
                                vars.color = newColor;
                                int aid = Ui.ef.MusicPlayer.handler.getAID(ADI);
                                catchData.put(aid,vars);
                                ring.img.maskPaint.setColor(newColor);
                                Iv.setImageBitmap(bm);
                                ring.invalidate();
                            }else{
                                ring.img.maskPaint.setColor(thumbRing.Color0);
                                Iv.setImageBitmap(null);
                                ring.invalidate();
                            }

                        }
                    };
                    task.execute();
                }
            }
        }
    }


    class data {
        Bitmap Bm;
        int color;
    }

    ITask task;

    public Bitmap setImg(String str){
        if(str != null){
            AssetManager assetManager = getContext().getAssets();
            InputStream in;
            byte[] bt = null;
            try {
                in = assetManager.open("imgs/"+str);
                bt = new byte[in.available()];
                in.read(bt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            notBitmap = true;
            Bitmap myBitmap = BitmapFactory.decodeByteArray(bt,0,bt.length);
            Iv.setImageBitmap(myBitmap);
            //Iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return myBitmap;
        }else{
            Iv.setImageBitmap(null);
            return null;
        }
    }

    boolean notBitmap = false;

    public void removeImg() {
        notBitmap = false;
        Iv.setImageDrawable(null);
        Iv.setImageBitmap(null);
        setAlpha(0);
    }


}
