package com.example.spaceshiphunter;




import java.lang.reflect.Array;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	public int healthPoints = 50;
	private double xSpeed;
	private double ySpeed;
	private double maxSpeed = 4;
	private double accel = .2;
	private float rotation;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	private long previousTime;
	private long fireCooldown = 100;
	private boolean onCD = false;
	private boolean firingSide = false;
	private float recoil = 5;
	private float xRecoilHolder = 0;
	private float yRecoilHolder = 0;
	private double angle;

	

	
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

	
	
	public double getXSpeed() {
		return xSpeed;
	}
	
	public double getYSpeed() {
		return ySpeed;
	}

	public void setXSpeed(float x){
		xSpeed = x;
	}
	
	public void setYSpeed(float y){
		ySpeed = y;
	}
	
	public void draw(Canvas canvas) {

			for ( int i = 0; i < lasers.size(); i++ ) {
				lasers.get(i).draw(canvas);
				
		}
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
		
			if (onCD && previousTime + fireCooldown <= System.currentTimeMillis()){
				onCD = false;
			}
	
			
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
			
			xSpeed = xSpeed - (xRecoilHolder * Math.cos(angle));
			x += xSpeed ;
			xRecoilHolder = 0;
			ySpeed =  ySpeed - (yRecoilHolder * Math.sin(angle));
			y += ySpeed;
			yRecoilHolder = 0;

			angle = Math.atan2(accelY,accelX);
			rotation = (float) Math.toDegrees(angle);
			
			rotatedbitmap = RotateBitmap(bitmap,rotation + 90);
			
				
				for ( int i = 0; i < lasers.size(); i++ ) {

					lasers.get(i).update();
					
				
			}
			
	}

	
	public void fireLaser(Bitmap bitmapL){
		Laser laser;
		if (onCD == false){
			if (firingSide == false){
				laser = new Laser(bitmapL, x, y, 25, 0);
				firingSide = true;
			}else{
				laser = new Laser(bitmapL, x, y, -25, 0);
				firingSide = false;
			}
			laser.accelX = accelX;
			laser.accelY = accelY;
			laser.setRotation();
			lasers.add(laser);
			xRecoilHolder = recoil;
			yRecoilHolder = recoil;
			onCD = true;
			previousTime = System.currentTimeMillis();
			}
		}
	
	public void removeLaser(int index){
			lasers.remove(index);
		}
	}

	
	

	


