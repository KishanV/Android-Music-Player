package Views.Library.Menu;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;

import com.linedeer.api.call;
import com.linedeer.player.Ui;

import Views.api.FMlyt;
import Views.api.animLis;

public class pagePrototype extends FMlyt {

    AnimatorSet Set;
    public int Id;

    public pagePrototype(Context context, int width, int height,int Id) {
       super(context, width, height);
       setEnableCatch();
        this.Id = Id;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //text.draw(canvas);
    }

    public void open(final call Call, boolean from){
        int to = width;
        if(from){
            to = -width;
        }
        Set = new AnimatorSet();
        Set.setInterpolator(Ui.cd.TH);
        Set.playTogether(
                ObjectAnimator.ofFloat(this, "X", to, 0),
                ObjectAnimator.ofFloat(this, "Alpha", 100, 100)
        );
        Set.setDuration(0).start();
        Set.addListener(new animLis(){
            @Override
            public void onAnimationEnd(Animator animation) {
               Call.onCall(true);
                removeCatch();
            }
        });
    }

    public void  onRemove(){

    }

    public void onClose(boolean isOpen) {

    }

    public boolean hasBack() {
        return false;
    }


}
