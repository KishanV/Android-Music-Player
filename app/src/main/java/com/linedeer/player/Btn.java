package com.linedeer.player;

import android.content.Context;
import android.graphics.Canvas;
import Views.radiusSqure;
import Views.textImg;
import Views.api.FMView;

public class Btn extends FMView{
	radiusSqure Rs;
	radiusSqure Rt;
	
	textImg ti;
	public Btn(Context context, int width, int height) {
		super(context, width, height);
		setBackgroundColor(0x00000000);
		
		Rs = new radiusSqure(width, height, 0, 0, Ui.cd.DPIX[5]);
		Rs.setColor(0xFF000000);
		
		Rt = new radiusSqure(width - Ui.cd.DPIX[2], height  - Ui.cd.DPIX[2], Ui.cd.DPIX[1], Ui.cd.DPIX[1], Ui.cd.DPIX[4]);
		Rt.setColor(0xFF292E34);
		
		ti = new textImg(width, height, 0, 0, Ui.cd.DPIX[16]);
		ti.setText("Ok",true);
		ti.setColor(0xFFFFFFFF);
		
		
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		Rs.draw(canvas);
		Rt.draw(canvas);
		ti.draw(canvas);
	}
}
