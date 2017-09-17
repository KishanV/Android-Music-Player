package Views.Library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.home.playlist.backgroundImg;

import Views.ContentHome;
import Views.Home.songSlider;
import Views.Library.Menu.Albums;
import Views.Library.Menu.Artist;
import Views.Library.Menu.Folder;
import Views.Library.Menu.Geners;
import Views.Library.Menu.Librery;
import Views.Library.Menu.Songs;
import Views.Library.Menu.pagePrototype;
import Views.api.FMlyt;
import Views.api.animLis;

public class libraryHome extends FMlyt{

    AnimatorSet Set;
    btmView BtmView;
    public static libraryHome This;
    songSlider SongSlider;

    public libraryHome(Context context, int width, int height) {
        super(context, width, height);
        setEnableCatch();
        This = this;
        //setBackgroundColor(com.shape.home.backgroundImg.Color0);


        //TopStick = new FMView(context,width,Ui.cd.getHt(2));
        //TopStick.setBackgroundColor(backgroundImg.Color0);
        //addView(TopStick);

       /* TopView = new topView(context,Ui.cd.getHt(25),Ui.cd.getHt(50));
        TopView.setBackgroundColor(0x00FFFFFF);
        TopView.setX((width - TopView.width) / 2);
        addView(TopView);*/

        BtmView = new btmView(context,width, Ui.cd.getHt(60)){
            @Override
            int openMenu(float scolled) {
                int no = super.openMenu(scolled);
                libraryHome.this.openMenu(no);
                return no;
            }
        };
        BtmView.setBackgroundColor(backgroundImg.Color0);
        BtmView.setY(height - BtmView.height);
        addView(BtmView);

        //drawCatch();
        LibreryPage = new Librery(getContext(),width,height - BtmView.height,0);
        SongsPage = new Songs(getContext(),width,height - BtmView.height,1);
        AlbumsPage = new Albums(getContext(),width,height - BtmView.height,2);
        ArtistPage = new Artist(getContext(),width,height - BtmView.height,3);
        GenersPage = new Geners(getContext(),width,height - BtmView.height,4);
        FolderPage = new Folder(getContext(),width,height - BtmView.height,5);

        Sp = SongsPage;
        addView(Sp,indexOfChild(BtmView));


        //openMenu(0);
        SongSlider = new songSlider(context,width, Ui.cd.getHt(22));
        SongSlider.setY(height - BtmView.height - (SongSlider.height / 2f));
        SongSlider.setBtns(true);
        addView(SongSlider);

        Vx = VelocityTracker.obtain();

        pages = new pagePrototype[]{LibreryPage,SongsPage,AlbumsPage,ArtistPage,GenersPage,FolderPage};

        //addView(top,indexOfChild(Sp)+1);
        //addView(LibreryPage);

    }

    public VelocityTracker Vx;
    int DX;
    int DY;



    public void ONDown(MotionEvent event) {
        super.onMove(event);
        Vx.addMovement(event);
        //if(!busy){
            baned = false;
            moved = false;
            DX = (int) event.getX();
            DY = (int) event.getY();
      //  }
    }

    boolean moved = false;
    boolean busy = false;
    boolean baned = false;
    pagePrototype top;
    int xFraction;
    int val;

    public boolean ONMove(MotionEvent event) {
        super.onMove(event);
        Vx.addMovement(event);
        if(!busy && !ContentHome.This.TargetD){
            val = (int) (event.getX() - DX);
            if(!moved && !baned){
                if(Math.abs(event.getY() - DY) > Ui.cd.getHt(25)){
                    baned = true;
                }else if(!moved && Math.abs(event.getX() - DX) > Ui.cd.getHt(25) && DY < height - BtmView.height - SongSlider.height){
                    moved = true;
                    DX = (int) event.getX();
                    ///val = (int) (event.getX() - DX);

                    if(val > 0){
                        xFraction = -width;
                        if(Sp.Id != 0){
                            top = pages[Sp.Id-1];
                            addView(top,indexOfChild(Sp)+1);
                            top.setX(xFraction);
                        }else{
                            top = null;
                        }
                    }else{
                        xFraction = width;
                        if(Sp.Id != pages.length - 1){
                            top = pages[Sp.Id+1];
                            addView(top,indexOfChild(Sp));
                        }else{
                            top = null;
                        }
                    }
                    if(top == null){
                        baned = true;
                    }else{
                        Sp.drawCatch();
                        top.setAlpha(1);
                        Sp.setAlpha(1);
                    }
                }
            }else if(!baned){
                if(xFraction < 0){
                    float alpha =  ((top.width - ((xFraction + val) + top.width)) * (1f / top.width));
                    top.setX(xFraction + val);
                    top.drawCatch();
                    Sp.setAlpha(alpha);
                }else{
                    float alpha =  ((top.width - ((val) + top.width)) * (1f / top.width));
                    Sp.setX(val);
                    top.drawCatch();
                    top.setAlpha(alpha);
                }
            }
        }
        return false;
    }

    public void ONUp(MotionEvent event) {
        super.onMove(event);
        if(!busy){
            Vx.computeCurrentVelocity(100);
            float valx = Vx.getXVelocity(0);
            Vx.clear();

            if(moved && !baned){
                if(xFraction < 0){
                    if((int)Math.abs(valx) < 12){
                        if(top.getX() < -(width / 2f)){
                            asItis(top,Sp,-width,1);
                        }else{
                            Play(top,Sp,0,0f);
                        }
                    }else if(valx < 0){
                        asItis(top,Sp,-width,1);
                    }else{
                        Play(top,Sp,0,0f);
                    }
                }else{
                    if((int)Math.abs(valx) < 12){
                        if(Sp.getX() > -(width / 2f)){
                            asItis2(Sp,top,0,0);
                        }else{
                            Play(Sp,top,-width,1f);
                        }
                    }else if(valx > 0){
                        asItis2(Sp,top,0,0);
                    }else{
                        Play(Sp,top,-width,1f);
                    }


                }
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ONDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                 ONMove(event);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                //onOut(event);
                break;
            case MotionEvent.ACTION_UP:
                ONUp(event);
                break;
            default:
                //onleave(event);
        }
        if(moved){
            return  false;
        }
        return super.dispatchTouchEvent(event);
    }

    public void openPage(int no){
        openMenu(no);
        BtmView.setPos(no);
    }

    public boolean fromLocation = false ;
    public void openFolder(String path){
        openPage(FolderPage.Id);
        ((Folder)FolderPage).setFile(path);
        fromLocation = true;
    }

    public void goingBack() {
        fromLocation = false;
    }

    void removeBack(){
        if(fromLocation){
            Log.i("My","removeBack : fromLocation");
            Ui.bk.dropLast();
        }
        fromLocation = false;
    }

    void asItis2(final pagePrototype movingPage, final pagePrototype stayPage, int val, float alpha){
        busy = true;
        AnimatorSet Set;
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(
                ObjectAnimator.ofFloat(movingPage, "X",val),
                ObjectAnimator.ofFloat(stayPage, "Alpha",alpha)
        );
        Set.setDuration(300).start();
        BtmView.setPos(movingPage.Id);
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(stayPage);
                busy = false;
            }
        });
        removeBack();
    }

    void asItis(final pagePrototype movingPage, pagePrototype stayPage, int val, float alpha){
        busy = true;
        AnimatorSet Set;
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(
                ObjectAnimator.ofFloat(movingPage, "X",val),
                ObjectAnimator.ofFloat(stayPage, "Alpha",alpha)
        );
        Set.setDuration(300).start();
        BtmView.setPos(stayPage.Id);
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(movingPage);
                busy = false;
            }
        });
        removeBack();
    }

    void Play(pagePrototype movingPage,pagePrototype stayPage,int val,float alpha){
        busy = true;
        AnimatorSet Set;
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(
                ObjectAnimator.ofFloat(movingPage, "X",val),
                ObjectAnimator.ofFloat(stayPage, "Alpha",alpha)
        );
        Set.setDuration(300).start();
        BtmView.setPos(top.Id);
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(Sp);
                Sp = top;
                Sp.removeCatch();
                Sp.invalidate();
                busy = false;
            }
        });
        removeBack();
    }

    pagePrototype LibreryPage;
    pagePrototype SongsPage;
    pagePrototype AlbumsPage;
    pagePrototype ArtistPage;
    pagePrototype GenersPage;
    pagePrototype FolderPage;

    pagePrototype[] pages;

    public boolean isOpen = false;
    public  boolean isPlaying = false;

    pagePrototype Sp;
    public  void openMenu(int no){
        removeBack();
        if(Sp.Id != no){
            boolean from =  Sp.Id > no ? true : false ;
            pagePrototype oldSp = Sp;
            pagePrototype nowSP;

            nowSP = pages[no];
            nowSP.Id = no;
            addView(nowSP,indexOfChild(Sp)+1);
            Sp = nowSP;

            nowSP.setAlpha(0);
            nowSP.setY(0);
            call Call = new call() {
                @Override
                public void onCall(boolean bl) {
                    libraryHome.this.removeView((pagePrototype)dataOBJ);
                    ((pagePrototype)dataOBJ).onRemove();
                    dataOBJ = null;
                }
            };
            Call.dataOBJ = oldSp;
            nowSP.open(Call,from);
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if(y == 0){
            ContentHome.This.MainHome.drawCatch();
            ContentHome.This.MainHome.setVisibility(GONE);
            ContentHome.This.AlphaBack.setVisibility(GONE);
            removeCatch();
            //setVisibility(VISIBLE);
        }else if(y == height){
            ContentHome.This.MainHome.removeCatch();
            ContentHome.This.MainHome.setVisibility(VISIBLE);
            removeCatch();
            //setVisibility(GONE);
        } else{
            ContentHome.This.AlphaBack.setVisibility(VISIBLE);
            ContentHome.This.MainHome.setVisibility(VISIBLE);
            ContentHome.This.MainHome.drawCatch();
            //setVisibility(VISIBLE);
            drawCatch();
        }
        ContentHome.This.MainHome.setSize(width, (int) (y));
    }

    void onClose(boolean isOpen){
       if(!isOpen){
           removeBack();
       }
        Sp.onClose(isOpen);
    }

    @Override
    public void onDrawCatch() {
        //Sp.onClose(true);
    }

    @Override
    public void onRemoveCatch() {
        //Sp.onClose(false);
    }

    public void open(int to, final boolean state){
        onClose(!state);
        isPlaying = true;
        int time = (int)(5f * (100f  / height * Math.abs(to - getY() )));
        if(Set != null){
            Set.cancel();
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(time);
        if(!state){
            Set.playTogether(
                    ObjectAnimator.ofInt(libraryHome.this, "Openmove",(int)getY(), to)
            );
        }else{
            Set.playTogether(
                    ObjectAnimator.ofInt(libraryHome.this, "Openmove",(int)getY(), to)
            );
        }
        isOpen = state;
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                isPlaying = false;
                if(state){

                }
                //libraryHome.this.removeCatch();
            }
        });
        Set.start();
    }

    void stopAnim(){
        Set.cancel();
        isOpen = false;
    }

    public void setOpenmove(int point) {
        setY(point);
    }

    public boolean hasBack() {
        boolean val =  Sp.hasBack();
        if(!val){
            if(fromLocation){
                Ui.bk.back();
                val = true;
            }
        }
        return val;
    }


}
