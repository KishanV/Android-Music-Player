package Views.Popups;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.linedeer.player.Ui;
import com.shape.Library.Icon.inputIcon;
import com.shape.Library.allsong.itemRect;
import com.shape.Library.popup.closeBtn;
import com.shape.Library.popup.rightBtn;

import Views.api.FMText;
import Views.api.FMedittext;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.api.animLis;
import Views.radiusSqure;
import Views.textImg;


public class getText extends FMlyt {

    FMlyt Menu;
    AnimatorSet Set;
    String val;
    radiusSqure rs;

    public getText(Context context, int width, int height,String str) {
        super(context, width, height);
        setBackgroundColor(0x66000000);


        Menu = new FMlyt(context, Ui.cd.DPW, Ui.cd.getHt(170)){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                // rs.draw(canvas);
            }
        };
        Menu.setBackgroundColor(itemRect.Color0);
        addView(Menu);
        setAlpha(0);

        ShapeView closebtn =  closeBtn.getFMview(getContext(),true);
        closebtn.setY((Ui.cd.getHt(40) - closebtn.width) / 2);
        closebtn.setX(Menu.width - (closebtn.getY() + closebtn.width));
        closebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });
        Menu.addView(closebtn);
        ShapeView icon = inputIcon.getFMview(getContext(),true);
        icon.setSize(Ui.cd.getHt(40), Ui.cd.getHt(40));
        icon.setY((Ui.cd.getHt(40) - icon.width) / 2);
        icon.setX(icon.getY());
        Menu.addView(icon);

        //FMText title = textImg.getFMText(getContext(),"ENTER TEXT FOR PLAYLIST",Ui.cd.getHt(14));
        FMText title = textImg.getFMText(getContext(),str, Ui.cd.getHt(14));
        title.setX(icon.getY() + icon.width + Ui.cd.getHt(5) + icon.getY());
        title.setY((Ui.cd.getHt(40) - title.height) / 2);
        Menu.addView(title);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Ui.bk.back();
            }
        });

        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.setDuration(200);
        Set.playTogether(
                ObjectAnimator.ofFloat(this, "Alpha", 1.0F)
        );

        FMText dt = textImg.getFMText(getContext(),"DEMO", Ui.cd.getHt(18));

        final FMedittext text = new FMedittext(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                rs.draw(canvas);
                super.onDraw(canvas);
            }
        };
        //text.setTextSize(18);
        text.setPadding((Ui.cd.getHt((50) - dt.height) / 2),(Ui.cd.getHt((50) - dt.height) / 2),0,0);
        text.setSingleLine();
        //text.setCursorVisible(false);
        text.setLayoutParams(new LayoutParams(Ui.cd.DPW - Ui.cd.getHt(100), Ui.cd.getHt(50)));
        text.setX(Ui.cd.getHt(50));
        text.setY(Ui.cd.getHt(50));
        rs = new radiusSqure(Ui.cd.DPW - Ui.cd.getHt(100), Ui.cd.getHt(50),0,0, Ui.cd.getHt(13));
        rs.setColor(0x33000000);
        addView(text);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 val = text.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event == null || KeyEvent.KEYCODE_ENTER == event.getKeyCode()){
                    if(onEnter(text.getText().toString())){
                        Ui.bk.back();
                        return false;
                    }
                }
                //Log.i("My","KeyEvent : " + event);
                return true;
            }
        });
        text.setFocusableInTouchMode(true);
        text.requestFocus();
        text.setTextColor(0xFFFFFFFF);

        text.getPaint().setTypeface(Ui.cd.cuprumFont);
        text.getPaint().setTextSize(Ui.cd.getHt(18));



        ShapeView right = rightBtn.getFMview(getContext(),true);
        right.setRipple(true,0.5f);
        right.InCenter(width,height);
        right.setY(Menu.height - right.height - Ui.cd.getHt(13));
        addView(right);

        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
                InputMethodManager imm = (InputMethodManager) Ui.ef.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        Set.start();

        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onEnter(text.getText().toString())){
                    Ui.bk.back();
                }
            }
        });
    }

    public boolean onEnter(String str){
        return false;
    }
}
