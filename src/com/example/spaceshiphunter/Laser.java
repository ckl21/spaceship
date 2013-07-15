package com.example.spaceshiphunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Laser {


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
	private double maxSpeed = 10;
	private double speed = 5;
	private double accel = 1.1;
	private float rotation;
	private double angle;
	private double offset = 0;

	

	
	

	
	public Laser(Bitmap bitmap, float x, float y) {
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
		canvas.drawBitmap(rotatedbitmap, x - (rotatedbitmap.getWidth() / 2) + (float) (offset * Math.cos(angle+1.57)), y - (rotatedbitmap.getHeight() / 2) + (float) (offset * Math.sin(angle+1.57)), null);
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
			
		if (speed < maxSpeed){
			speed = speed*accel;
		}
		 x += speed * Math.cos(angle);
		 
		 y += speed * Math.sin(angle);


			
	}

	
	public void setRotation(){
		angle = Math.atan2(accelY,accelX);
		rotation = (float)Math.toDegrees(angle);
		rotatedbitmap = RotateBitmap(bitmap,rotation + 90);
	}
	
	public void setSide(int side){
		if (side == 0){
			offset = -25;
		}else if (side == 1){
			offset = 25;
		}
	}

	
	}
	

	

	

