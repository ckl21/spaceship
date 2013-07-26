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
import android.util.Log;
import android.view.MotionEvent;
import com.example.spaceshiphunter.Game;


public class EDroid {

	private Bitmap bitmap;	// the actual bitmap
	private Bitmap rotatedbitmap;
	private float x;			// the X coordinate
	private float y;			// the Y coordinate
	public float newX;
	public float newY;
	public float accelX;
	public float accelY;
	public int healthPoints = 250;
	public int maxHealth = 250;
	private double xSpeed;
	private double ySpeed;
	private double maxSpeed = 10;
	private double accel = .2;
	private float rotation;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	private long previousTime;
	private long fireCooldown = 400;
	private long fireAnimCooldown = 125;
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
	private float xKnockback = 0;
	private float yKnockback = 0;
	public boolean dead = false;
	public boolean dying = false;
	public boolean end = false;
	public Bitmap leftFlash;
	private Bitmap leftRotated;
	private Bitmap rightRotated;
	public Bitmap rightFlash;
	public Bitmap booster1;
	public Bitmap booster2;
	private Bitmap rotatedBooster;
	private boolean leftFlashing = false;
	private boolean rightFlashing = false;
	public double scaleFactor;

	

	
	public EDroid(Bitmap bitmap, int x, int y,Bitmap lf, Bitmap rf, Bitmap booster1, Bitmap booster2) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		leftFlash = lf;
		rightFlash = rf;
		this.booster1 = booster1;
		this.booster2 = booster2;

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

			
		if (dead == false){
			canvas.drawBitmap(rotatedbitmap, x - (rotatedbitmap.getWidth() / 2), y - (rotatedbitmap.getHeight() / 2), null);
			if (dying == false){
				canvas.drawBitmap(rotatedBooster, x - (rotatedBooster.getWidth() / 2), y - (rotatedBooster.getHeight() / 2), null);
				if (leftFlashing){
					canvas.drawBitmap(leftRotated, x - (leftRotated.getWidth() / 2), y - (leftRotated.getHeight() / 2), null);
				}
				if (rightFlashing){
					canvas.drawBitmap(rightRotated, x - (rightRotated.getWidth() / 2), y - (rightRotated.getHeight() / 2), null);
				}
			}
		}
		
		
		
		
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
			if (onCD && previousTime + fireAnimCooldown <= System.currentTimeMillis() && (leftFlashing || rightFlashing)){
				leftFlashing = false;
				rightFlashing = false;
			}
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
			
			xSpeed = xSpeed - (xRecoilHolder * Math.cos(angle)) + xKnockback;
			if (dying){
				xSpeed = 0;
			}
			x += xSpeed ;
			xRecoilHolder = 0;
			xKnockback = 0;
			ySpeed =  ySpeed - (yRecoilHolder * Math.sin(angle)) + yKnockback;
			if (dying){
				ySpeed = 0;
			}
			y += ySpeed;
			yRecoilHolder = 0;
			yKnockback = 0;

			if (dying == false){
				angle = Math.atan2(destinationY-y,destinationX-x);
				rotation = (float) Math.toDegrees(angle);
			}
			rotatedbitmap = RotateBitmap(bitmap,rotation + 90);
			leftRotated = RotateBitmap(leftFlash,rotation + 90);
			rightRotated = RotateBitmap(rightFlash,rotation + 90);
			if (xSpeed > 3 || ySpeed > 3){
				rotatedBooster = RotateBitmap(booster2,rotation + 90);
			}else{
				rotatedBooster = RotateBitmap(booster1,rotation + 90);
			}
		
			
				
				for ( int i = 0; i < lasers.size(); i++ ) {

					lasers.get(i).update();
					
				
			}
			
	}

	
	public void fireLaser(Bitmap bitmapL){
		Laser laser;
		if (onCD == false){
			if (firingSide == false){
				laser = new Laser(bitmapL, x, y, 50/scaleFactor,60/scaleFactor, (Math.random()*10)-5, 20, 2, 1.04);
				Game.spool.play(Game.missilesfx, Game.volume, Game.volume, 0, 0,0.8f);
				firingSide = true;
				rightFlashing = true;
			}else{
			
				laser = new Laser(bitmapL, x, y, -50/scaleFactor,60/scaleFactor, (Math.random()*10)-5, 20, 2, 1.04);

				firingSide = false;
				leftFlashing = true;
				
			}
	
			laser.accelX = destinationX - x;
			laser.accelY = destinationY - y;
			
			
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
	
	public void setDestination(double x, double y){
		destinationX = (float)x;
		destinationY = (float)y;
	}
	
	public void fireHit(int damage){
		healthPoints -= damage;
		Game.spool.play(Game.enemyhitsfx, Game.volume, Game.volume, 1, 0, 1); 
	
	}
	public void knockback(Laser laser, double str){
		xKnockback = (float)(str*laser.getXAngle());
		yKnockback = (float)(str*laser.getYAngle());
	}
	public void changeBaseBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	}

	
	

	


