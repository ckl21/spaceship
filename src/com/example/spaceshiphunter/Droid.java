package com.example.spaceshiphunter;




import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;


public class Droid {

	private Bitmap bitmap;	// the actual bitmap
	private Bitmap rotatedbitmap;
	private float x;			// the X coordinate
	private float y;			// the Y coordinate
	public float newX;
	public float newY;
	public float accelX;
	public float accelY;
	private float xSpeed;
	private float ySpeed;
	private float maxSpeed = 4;
	private double accel = .2;
	private float rotation;

	

	
	public Droid(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;

	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public float getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	
	
	public float getXSpeed() {
		return xSpeed;
	}
	
	public float getYSpeed() {
		return ySpeed;
	}

	public void setXSpeed(float x){
		xSpeed = x;
	}
	
	public void setYSpeed(float y){
		ySpeed = y;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(rotatedbitmap, x - (rotatedbitmap.getWidth() / 2), y - (rotatedbitmap.getHeight() / 2), null);
	}

	public static Bitmap RotateBitmap(Bitmap source, float angle)
	{
		 Matrix matrix = new Matrix(); 
		 matrix.postRotate(angle); 
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
			
			if(xSpeed <= maxSpeed && xSpeed >= -maxSpeed){
				xSpeed += newX*accel;
			}else if(xSpeed > maxSpeed){
				xSpeed = maxSpeed;
			}else if(xSpeed < -maxSpeed){
				xSpeed = -maxSpeed;
			}
			if(ySpeed <= maxSpeed && ySpeed >= -maxSpeed){
				ySpeed += newY*accel;
			}else if(ySpeed > maxSpeed){
				ySpeed = maxSpeed;
			}else if(ySpeed < -maxSpeed){
				ySpeed = -maxSpeed;
			}
			x += xSpeed;
			y += ySpeed;
			

		
			rotation = (float) Math.toDegrees(Math.atan2(accelY,accelX) + 90);
			
			rotatedbitmap = RotateBitmap(bitmap,rotation);
	
	}

	
}
