package Views.api;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

public class exPath extends Path {

	/*public exPath() {
		moveTo(46.65f, 31.3f);
		lineTo(5.6f, 56.85f);
		quadTo(3.3f, 58.4f, 1.65f, 57.4f);
		quadTo(0f, 56.45f, 0f, 53.55f);
		lineTo(0f, 4.25f);
		quadTo(0f, 1.35f, 1.65f, 0.4f);
		quadTo(3.3f, -0.6f, 5.6f, 0.95f);
		lineTo(46.65f, 26.5f);
		quadTo(48f, 27.45f, 48f, 29f);
		quadTo(48f, 30.35f, 46.65f, 31.3f);
	}*/
	
	public void init(int width,int height){
		computeBounds(rectF, true);
		this.width = width;
		this.height = height;
	}
	
	public float width;
	public float height;
	
	
	public float scalex = 1;
	public float scaley = 1;
	
	public float x = 0;
	public float y = 0;
	
	
	
	public Matrix matrix = new Matrix(); 
	static RectF rectF = new RectF();
	
	public float getHeight(){
	
		return (scalex*height);
	}
	
	public float getWidth(){
		return (scaley*width);
	}
	
	public int getX(){
		return (int)(x);
	}
	
	public int getY(){
		return (int)(y);
	}
	
	public int getVal(float val){
		return (int)(scaley*val);
	}
	
	public void resizeScale(float scale,float x,float y) {
		scalex = scale;
		scaley = scale;
		this.x = x;
		this.y = y;
		matrix.postScale(scale,scale);
		matrix.postTranslate(x,y);
		transform(matrix);
	} 
	
	public void resizeScale(float width,float height,float x,float y) {
		scalex = width/this.height;
		scaley = height/this.width;
		this.x = x;
		this.y = y;
		matrix.postScale(scalex,scaley);
		matrix.postTranslate(x,y);
		transform(matrix);
	} 
	
	public void setPos(float x,float y){
		this.x = x;
		this.y = y;
		matrix.postTranslate(x,y);
		transform(matrix);
	}
	

	
	public void resizeWidth(float width,float x,float y) {
		
		matrix.postTranslate(x,y);
		matrix.postScale(width/this.width,width/this.width);
		transform(matrix);
	}
	
	public void heightWidth(float width,float height) {
		matrix.postTranslate(x,y);
		matrix.postScale(width/this.width,height/this.height);
		transform(matrix);
	}
	
	public void setCenter(float scale,float width,float height) {
		scalex = scale;
		scaley = scale;
		matrix.postScale(scale,scale);
		matrix.postTranslate((width-(scale*this.width))/2,(height-(scale*this.height))/2);
		transform(matrix);
	}
	
	public void setCenterHeight(float Height,float refWidth,float refHeight) {
		scalex = Height/this.height;
		scaley = scalex;
		this.x = (refHeight-(scalex*this.width))/2;
		this.y = (refWidth-(scalex*this.height))/2;
		matrix.postScale(scalex,scaley);
		matrix.postTranslate(x,y);
		transform(matrix);
	}
	
	public void rotat(int digree,int x,int y) {
		matrix.postRotate(digree, x, y);
		transform(matrix); 
	}

}
