package com.example.spaceshiphunter;




import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;


public class Marker {

	private Bitmap bitmap;	// the actual bitmap
	private Bitmap rotatedbitmap;
	private float x;			// the X coordinate
	private float y;			// the Y coordinate
	public float newX;
	public float newY;
	public float accelX;
	public float accelY;
	private int side;
	private int screenWidth;
	private int screenHeight;
	

	


	

	
	public Marker(Bitmap bitmap, int x, int y, int side) {
		if(side == 1){
			this.bitmap = RotateBitmap(bitmap, 180);
		}else if(side == 2){
			this.bitmap = RotateBitmap(bitmap, -90);
		}else if(side == 3){
			this.bitmap = bitmap;
		}else if(side == 4){
			this.bitmap = RotateBitmap(bitmap, 90);
		}
		this.x = x;
		this.y = y;
		this.side = side;

		
		

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

	
	
	
	
	public void draw(Canvas canvas) {

			
		
			canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
			
	
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
	public void update(float height, float width) {
		
			if (side == 3 || side == 1){
				if (accelX > 5){
					x = width-bitmap.getWidth()/2;
				}else if (accelX < -5){
					x = 0+bitmap.getWidth()/2;
				}else{
					x = accelX * (width/10-bitmap.getWidth()) +  width/2;
				}

			}
			
			if (side == 2 || side == 4){
				if (accelY > 5){
					y = height-bitmap.getWidth()/2;
				}else if (accelY < -5){
					y = 0+bitmap.getWidth()/2;
				}else{
					y = accelY * (height/10-bitmap.getWidth()) +  height/2;
				}
			}
			
			if (side==3){
				y = height-bitmap.getHeight()/2;
			}
			if (side ==1){
				y = bitmap.getHeight()/2;
			}
			if (side == 2){
				x = width-bitmap.getWidth()/2;
			}
			if (side == 4){
				x = bitmap.getWidth()/2;
			}
			
			
	}

	
	}
	
	

	


