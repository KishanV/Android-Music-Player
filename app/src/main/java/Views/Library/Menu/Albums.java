package Views.Library.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.shape.Library.Icon.albumIcon;
import com.shape.Library.Icon.searchIcon;
import com.shape.Library.allsong.backcloseBtn;
import com.shape.Library.allsong.iconBack;
import com.shape.Library.allsong.itemBack;
import com.shape.Library.allsong.notFound;
import com.shape.Library.allsong.songIcon;
import com.shape.home.slider.backgroundImg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Views.Library.Menu.AlbumsClass.albumAdapter;
import Views.Library.Menu.SongsClass.ScharView;
import Views.Library.Menu.SongsClass.ScrollCharView;
import Views.api.FMText;
import Views.api.FMView;
import Views.api.FMedittext;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.textImg;

public class Albums extends pagePrototype {

    ShapeView SearchIcon;
    ShapeView MainIcon;
    FMView listBackground;
    ListView listview;
    FMText title;
    float diff;
    int lastDiff = -1;
    ArrayList<String> ar;
    ScharView Sc;

    public Albums(Context context, int width, int height,int id) {
        super(context, width, height,id);

        /*try {
            Class<?> clazz = Class.forName("com.shape.Library.Icon.searchIcon");
            Method method = clazz.getMethod("getFMview", Context.class ,boolean.class );
            SearchIcon = (ShapeView) method.invoke(null,context,true);
            //method.invoke(null,context,true);
            //System.out.println(method);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/


        listBackground = new FMView(context,width,height - Ui.cd.getHt(50));
        //listBackground.setBackgroundColor(backgroundImg.Color0);
        listBackground.setY(Ui.cd.getHt(50));
        //addView(listBackground);

        listview = new ListView(getContext());
        listview.setLayoutParams(new FrameLayout.LayoutParams(width - Ui.cd.getHt(14),height - Ui.cd.getHt(50)));
        listview.setY(Ui.cd.getHt(50));
        listview.setDivider(null);
        listview.setBackgroundColor(backgroundImg.Color0);
        data = new albumAdapter(){
            @Override
            public void onSelect() {
                super.onSelect();
                Selection();
            }
        };
        listview.setAdapter(data);
        addView(listview);


        Sc = new ScharView(getContext());
        Sc.InCenter(Ui.cd.getHt(30),0);
        Sc.setY(Ui.cd.getHt(50));

        final ScrollCharView SChar = new ScrollCharView(getContext());
        SChar.setClickable(false);

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Log.i("My","onScrollStateChanged : " + scrollState + ":" );
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                cItemNo = firstVisibleItem;
                albumAdapter.Item itemOne = (albumAdapter.Item) view.getChildAt(0);
                albumAdapter.Item itemTwo = (albumAdapter.Item) view.getChildAt(1);
                if(itemOne != null){
                    float y  = itemOne.getY();
                    cItemY = (int) y;
                    Sc.setChar(itemOne.Schar);
                    itemOne.setChar(false);
                    //Log.i("My","onScroll : " + itemOne.Schar + " : " + itemTwo.Schar);
                    if(itemTwo != null && !itemOne.Schar.equals(itemTwo.Schar)){
                        itemTwo.setChar(true);
                        if(itemOne.height + y < Sc.height){
                            Sc.setY(Ui.cd.getHt(50 )   + itemOne.getY());
                        }else{
                            Sc.setY(Ui.cd.getHt(50 ));
                        }
                    }else{
                        Sc.setY(Ui.cd.getHt(50 ));
                    }
                }

            }
        });

          vm = new FMlyt(context, Ui.cd.getHt(14),height - Ui.cd.getHt(50)){

            boolean moved = false;
            int DY;
            @Override
            public void onDown(MotionEvent event) {
                DY = (int) event.getY();
                ar = new ArrayList<String>(data.index.keySet());
                Collections.sort(ar, new Comparator<String>() {
                    @Override
                    public int compare(String fruit2, String fruit1)
                    {
                        return  fruit2.compareTo(fruit1);
                    }
                });
                lastDiff = -1;
                if( SChar.getParent() == null){
                    SChar.setAlpha(0);
                    Albums.this.addView(SChar);
                }
                super.onDown(event);
                super.onMove(event);
                moved = false;
            }

            @Override
            public void onMove(MotionEvent event) {
                super.onMove(event);
                int y = (int) event.getY();
                int val = (int) (y / diff);
                if(!moved){
                    if(Math.abs(y - DY) > Ui.cd.getHt(10)){
                        SChar.setAlpha(1);
                        moved = true;
                    }
                }
                y = y - SChar.height / 2;
                if(y < Ui.cd.getHt(5)){
                    y = Ui.cd.getHt(5);
                }else if(y > height- SChar.height - Ui.cd.getHt(5)){
                    y = height- SChar.height - Ui.cd.getHt(5);
                }
                SChar.setY(y + Ui.cd.getHt(50));
                //Log.i("My","Found : " + lastDiff + ":" + val + ":" );

                if(val > -1 && val < ar.size() && val != lastDiff){
                    lastDiff = val;
                    SChar.setChar(ar.get(lastDiff));
                    if(lastDiff < 0){
                        lastDiff = 0;
                    }else if(data.index.size() - 1 < lastDiff){
                        lastDiff = data.index.size() - 1;
                    }
                    if(moved){
                        int no = data.index.get(ar.get(lastDiff));
                        listview.setSelection(no);
                    }
                }
            }

            @Override
            public void onUp(MotionEvent event) {
                super.onUp(event);
                int no = data.index.get(ar.get(lastDiff));
                listview.setSelection(no);
                super.onMove(event);
                SChar.setAlpha(0);
                Albums.this.removeView(SChar);
                lastDiff = -1;
                ar = null;
            }
        };
        vm.setBackgroundColor(itemBack.Color0);
        vm.setX(width - vm.width);
        vm.setY(Ui.cd.getHt(50));
        addView(vm);
        addView(Sc);

        SChar.setY(Ui.cd.getHt(50 + 2));
        SChar.setX(width - SChar.width - vm.width - Ui.cd.getHt(10));
        SChar.setAlpha(0);


        FMView titleBack = new FMView(context, Ui.cd.DPW, Ui.cd.getHt(50));
        titleBack.setBackgroundColor(com.shape.home.backgroundImg.Color0);
        titleBack.setX(width - titleBack.width);
        titleBack.setY(0);
        addView(titleBack);

        SearchIcon = searchIcon.getFMview(context,true);
        SearchIcon.setRipple(true,0.3f);
        SearchIcon.setX(width - (Ui.cd.getHt(5) + SearchIcon.width));
        SearchIcon.setY(Ui.cd.getHt(5));
        SearchIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });
        addView(SearchIcon);

        ShapeView iconback = iconBack.getFMview(context,false);
        addView(iconback);

        MainIcon = albumIcon.getFMview(context,true);
        MainIcon.setSize(Ui.cd.getHt(40), Ui.cd.getHt(40));
        MainIcon.setX(Ui.cd.getHt(5));
        MainIcon.setY(Ui.cd.getHt(5));
        addView(MainIcon);

        title = textImg.getFMText(context,"ALBUMS", Ui.cd.getHt(18));
        title.setY((Ui.cd.getHt(50) - title.height) / 2);
        title.setX(Ui.cd.getHt(60));
        addView(title);

        notfound = notFound.getFMview(getContext(),false);
        notfound.InCenter(width- Ui.cd.getHt(14),height - Ui.cd.getHt(50));
        notfound.setY(Ui.cd.getHt(70));
        notfound.setClickable(false);
        notfound.setAlpha(0);
        addView(notfound);
        changIndex();
        checkData();
    }

    FMlyt vm;
    void changIndex(){
        ar = new ArrayList<String>(data.index.keySet());
        Collections.sort(ar, new Comparator<String>() {
            @Override
            public int compare(String fruit2, String fruit1)
            {
                return  fruit2.compareTo(fruit1);
            }
        });
        diff = (ar.size() == 0 ? 0 : vm.height / ar.size());
        vm.removeAllViews();
        for(int i = 0;i < data.index.size();i++){
            FMText FChar = textImg.getFMText(getContext(),ar.get(i), Ui.cd.getHt(12));
            FChar.setClickable(false);
            FChar.InCenter(vm.width,diff);
            FChar.setY(FChar.getY() + (i*diff));
            FChar.img.setColor(0x66FFFFFF);
            vm.addView(FChar);
        }
    }

    ShapeView notfound;
    albumAdapter data;
    ShapeView SelectBack;
    FMText SelectedText;
    void Selection(){
        if(SelectBack == null){
            SelectBack = com.shape.Library.allsong.selecttitleBack.getFMview(getContext(),false);
            SelectBack.setSize(Ui.cd.DPW, Ui.cd.getHt(50));
            addView(SelectBack);

            SelectedText = textImg.getFMText(getContext(),"( 1 ) SONG SELECTED", Ui.cd.getHt(16));
            SelectedText.InCenter(SelectBack);
            SelectedText.setX(Ui.cd.getHt(50));
            addView(SelectedText);

            final ShapeView cb = backcloseBtn.getFMview(getContext(),false);
            cb.setRipple(true);
            cb.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ui.bk.back();
                }
            });
            cb.setX(width - cb.width);
            addView(cb);

            final ShapeView songicon = songIcon.getFMview(getContext(),false);
            addView(songicon);

            Ui.bk.add(new call() {
                @Override
                public void onCall(boolean bl) {
                    data.removeSelection();
                    listview.invalidateViews();
                    Albums.this.removeView(SelectBack);
                    Albums.this.removeView(SelectedText);
                    Albums.this.removeView(cb);
                    Albums.this.removeView(songicon);
                    SelectBack = null;
                }
            });
        }else{
            SelectedText.setText("( "+data.getSelected()+ " ) "+ " SONGS SELECTED");
            //SelectedText.InCenter(SelectBack);
        }
    }

    void onSearch(){
        SelectBack = com.shape.Library.allsong.selecttitleBack.getFMview(getContext(),false);
        SelectBack.setSize(Ui.cd.DPW, Ui.cd.getHt(50));
        addView(SelectBack);

        final FMedittext text = new FMedittext(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
            }
        };

        text.setTextSize(18);

        //text.setCursorVisible(false);
        text.setLayoutParams(new LayoutParams(Ui.cd.DPW - Ui.cd.getHt(100), Ui.cd.getHt(50)));
        text.setX(Ui.cd.getHt(50));
        text.setY(Ui.cd.getHt(14));
        addView(text);

        final ShapeView cb = backcloseBtn.getFMview(getContext(),false);
        cb.setRipple(true);
        cb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });
        cb.setX(width - cb.width);
        addView(cb);

        final ShapeView songicon = songIcon.getFMview(getContext(),false);
        addView(songicon);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery(text.getText().toString());
                changIndex();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        text.setFocusableInTouchMode(true);
        text.requestFocus();
        text.setTextColor(0xFFD35D69);
        text.getPaint().setTypeface(Ui.cd.cuprumFont);

        InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);

        Ui.bk.add(new call() {
            @Override
            public void onCall(boolean bl) {
                removeView(SelectBack);
                SelectBack = null;
                removeView(text);
                removeView(cb);
                removeView(songicon);
                listview.setAdapter(null);
                data.closeSearch();
                listview.setAdapter(data);
                changIndex();
                checkData();
            }
        });
    }

    void checkData(){
        if(data.getCount() == 0){
            Sc.setAlpha(0);
            notfound.setAlpha(1);
        }else{
            Sc.setAlpha(1);
            notfound.setAlpha(0);
        }
    }

    void searchQuery(String s){
        listview.setAdapter(null);
        data.searchData(s);
        listview.setAdapter(data);
        checkData();
    }

    int cItemNo;
    int cItemY;
    @Override
    public void onClose(boolean isOpen) {
        if(SelectBack != null && isOpen){
            Ui.bk.back();
        }
        listview.setSelectionFromTop(cItemNo,cItemY);
    }

    @Override
    public void onRemove() {
        if(SelectBack != null){
            Ui.bk.back();
        }
    }

}

