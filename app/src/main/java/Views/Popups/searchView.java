package Views.Popups;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.linedeer.player.Ui;
import com.shape.Library.allsong.backcloseBtn;
import com.shape.Library.allsong.songIcon;

import Views.api.FMedittext;
import Views.api.FMlyt;
import Views.api.ShapeView;


public class searchView extends FMlyt{
    public searchView(Context context, int width, int height) {
        super(context, width, height);

        final ShapeView songicon = songIcon.getFMview(getContext(),false);
        addView(songicon);

        final ShapeView cb = backcloseBtn.getFMview(getContext(),false);
        cb.setRipple(true);
        cb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
            }
        });
        cb.setX(width - cb.width);
        addView(cb);

        final FMedittext text = new FMedittext(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
            }
        };

        text.setTextSize(18);

        //text.setCursorVisible(false);
        text.setLayoutParams(new LayoutParams(width - Ui.cd.getHt(100), Ui.cd.getHt(50)));
        text.setX(Ui.cd.getHt(50));
        text.setY(Ui.cd.getHt(14));
        addView(text);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onType(text.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        text.setFocusableInTouchMode(true);
        text.requestFocus();
        text.setTextColor(0xFFD35D69);
        text.getPaint().setTypeface(Ui.cd.cuprumFont);
    }

    public void onType(String str){

    }

    public void onClose(){

    }
}
