package com.example.spaceshiphunter;




import java.lang.reflect.Array;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;


public class EDroid {

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
	private double maxSpeed = 10;
	private double accel = .2;
	private float rotation;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	private long previousTime;
	private long fireCooldown = 800;
	private boolean onCD = false;
	private boolean firingSide = false;
	private float recoil = 1;
	private float xRecoilHolder = 0;
	private float yRecoilHolder = 0;
	private double angle;
	public int state = 0;
	private float destinationX = 800;
	private float destinationY = 600;
	private float destinationArea = 50;
	private double deceleration = 0.8;

	

	
	public EDroid(Bitmap bitmap, int x, int y) {
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
	
			if (state == 0){
				if (destinationX > x+50 || destinationX < x-50){
					if(xSpeed <= maxSpeed && xSpeed >= -maxSpeed){
						xSpeed += (destinationX-x)*accel;
					}if(xSpeed > maxSpeed){
						xSpeed = maxSpeed;
					}else if(xSpeed < -maxSpeed){
						xSpeed = -maxSpeed;
					}
				}
				if (destinationY > y+50 || destinationY < y-50){
					if(ySpeed <= maxSpeed && ySpeed >= -maxSpeed){
						ySpeed += (destinationY-y)*accel;
					}if(ySpeed > maxSpeed){
						ySpeed = maxSpeed;
					}else if(ySpeed < -maxSpeed){
						ySpeed = -maxSpeed;
					}
					
				}
				if ((Math.abs(destinationX-x)< 50  && Math.abs(destinationY-y)< 50)){
					state = 1;
				}
			}
			
			xSpeed = xSpeed*deceleration;
			ySpeed = ySpeed*deceleration;
			
			xSpeed = xSpeed - (xRecoilHolder * Math.cos(angle));
			x += xSpeed ;
			xRecoilHolder = 0;
			ySpeed =  ySpeed - (yRecoilHolder * Math.sin(angle));
			y += ySpeed;
			yRecoilHolder = 0;

			
			angle = Math.atan2(destinationY-y,destinationX-x);
			rotation = (float) Math.toDegrees(angle);
			
			rotatedbitmap = RotateBitmap(bitmap,rotation + 90);
			
			
				
				for ( int i = 0; i < lasers.size(); i++ ) {

					lasers.get(i).update();
					
				
			}
			
	}

	
	public void fireLaser(Bitmap bitmapL){
		Laser laser;
		Laser laser2;
		if (onCD == false){
			
			laser = new Laser(bitmapL, x, y, 30, 0, 5, 2);
		
			laser2 = new Laser(bitmapL, x, y, -30, 0, 5, 2);
	
			laser.accelX = destinationX - x;
			laser.accelY = destinationY - y;
			laser2.accelX = destinationX - x;
			laser2.accelY = destinationY - y;
			laser.setRotation();
			laser2.setRotation();
			lasers.add(laser);
			lasers.add(laser2);
			xRecoilHolder = recoil;
			yRecoilHolder = recoil;
			onCD = true;
			previousTime = System.currentTimeMillis();
			}
		}
	
	public void removeLaser(int index){
			lasers.remove(index);
		}
	
	public void setDestination(double x, double y){
		destinationX = (float)x;
		destinationY = (float)y;
	}
	}

	
	

	

