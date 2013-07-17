package com.example.spaceshiphunter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;


public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback{

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Droid droid;
	private EDroid eDroid;
	Bitmap laser;
	Bitmap missile;
	Bitmap missile1;
	Bitmap missile2;
	Bitmap missile3;
	Bitmap missile4;
	Bitmap leftFlash;
	Bitmap rightFlash;
	Bitmap laser1;
	Bitmap laser2;
	Bitmap laser3;
	Bitmap laser4;
	Bitmap player0;
	Bitmap player1;
	Bitmap player2;
	Bitmap player3;
	Bitmap playerd0;
	Bitmap playerd1;
	Bitmap playerd2;
	Bitmap playerd3;
	Bitmap playerd4;
	Bitmap playerd5;
	Bitmap booster1;
	Bitmap booster2;
	long previousTime = 0;
	long enemyDelay = 3000;
	int droidFrame = 0;
	long droidTimer;
	long droidTimerDelay = 150;
	private double targetX = 0;
	private double targetY;
	float offsetX = 0;
	float offsetY = 3;
	boolean firing1 = false;
	boolean firing2 = false;

	


	public MainGamePanel(Context context) {
		super(context);	
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		player0 = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		player1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
		player2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
		player3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
		playerd0 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death0);
		playerd1 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death1);
		playerd2 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death2);
		playerd3 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death3);
		playerd4 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death4);
		playerd5 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death5);
		leftFlash = BitmapFactory.decodeResource(getResources(), R.drawable.leftflash);
		rightFlash = BitmapFactory.decodeResource(getResources(), R.drawable.rightflash);
		booster1 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters1);
		booster2 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters2);
		droid = new Droid(player0, 50, 50, leftFlash, rightFlash, booster1, booster2);
		eDroid = new EDroid(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), 600, 400);
		laser = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one);
		laser1 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one1);
		laser2 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one2);
		laser3 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one3);
		laser4 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one4);
		missile = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
		missile1 = BitmapFactory.decodeResource(getResources(), R.drawable.missile1);
		missile2 = BitmapFactory.decodeResource(getResources(), R.drawable.missile2);
		missile3 = BitmapFactory.decodeResource(getResources(), R.drawable.missile3);
		missile4 = BitmapFactory.decodeResource(getResources(), R.drawable.missile4);


		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(thread.getState() == Thread.State.NEW){
			thread.setRunning(true);
			thread.start();
			}else
				if (thread.getState() == Thread.State.TERMINATED){
					thread = new MainThread(getHolder(), this);
					thread.setRunning(true);
					thread.start(); // Start a new thread
					}
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		eDroid.draw(canvas);
		for ( int i = 0; i < droid.lasers.size(); i++ ) {
			droid.lasers.get(i).draw(canvas);
			
		}
		droid.draw(canvas);
		for ( int i = 0; i < eDroid.lasers.size(); i++ ) {
			eDroid.lasers.get(i).draw(canvas);
			
	}
	}

	public void update() {

		//set player ship image based on damage
		if (droid.healthPoints <= 150 && droidFrame == 0){
			droid.changeBaseBitmap(player1);
			droidFrame = 1;
			}
		if (droid.healthPoints <= 100 && droidFrame == 1){
			droid.changeBaseBitmap(player2);
			droidFrame = 2;
			}
		if (droid.healthPoints <= 50 && droidFrame == 2){
			droid.changeBaseBitmap(player3);
			droidFrame = 3;
			}
		
		if (droid.healthPoints <= 0 ){
			if (droidFrame == 3){
				droid.dying = true;
				droid.changeBaseBitmap(playerd0);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 4 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.changeBaseBitmap(playerd1);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 5 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.changeBaseBitmap(playerd2);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 6 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.changeBaseBitmap(playerd3);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 7 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.changeBaseBitmap(playerd4);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 8 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.changeBaseBitmap(playerd5);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 9 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.dead = true;
				droidFrame++;
			}
		}
		
		
		if (eDroid.healthPoints > 0){
			if (eDroid.state == 0){
					
					while (Math.abs(targetX - droid.getX()) < 50 && Math.abs(targetY - droid.getY()) < 50 || targetX == 0){
						targetX = Math.random()*(getWidth()-eDroid.getBitmap().getWidth()) + eDroid.getBitmap().getWidth()/2;
						targetY = Math.random()*(getHeight()-eDroid.getBitmap().getHeight()) + eDroid.getBitmap().getHeight()/2;
					}
				eDroid.setDestination(targetX, targetY);
				previousTime = System.currentTimeMillis();
				eDroid.update();
			}else if (eDroid.state == 1){
				if(System.currentTimeMillis() < previousTime + enemyDelay && droid.dying == false){
					targetX = droid.getX();
					targetY = droid.getY();
					eDroid.setDestination(targetX, targetY);
					eDroid.fireLaser(missile);
					eDroid.update();
				}else{
					targetX = Math.random()*(getWidth()-eDroid.getBitmap().getWidth()) + eDroid.getBitmap().getWidth()/2;
					targetY = Math.random()*(getHeight()-eDroid.getBitmap().getHeight()) + eDroid.getBitmap().getHeight()/2;
					eDroid.state = 0;
				}
			}
		}else{
			eDroid.update();
		}
		if (firing1 && droid.healthPoints > 0){
			droid.fireLaser(laser);
		}
		// check collision with right wall if heading right
		if (droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth() && droid.getXSpeed() > 0) {
			droid.setX(getWidth()-droid.getBitmap().getWidth() / 2);
			droid.setXSpeed(0);
			
			
			
		}
		// check collision with left wall if heading left
		if (droid.getX() - droid.getBitmap().getWidth() / 2 <= 0 && droid.getXSpeed() < 0) {
			droid.setX(droid.getBitmap().getWidth() / 2);
			droid.setXSpeed(0);
			
		}
		// check collision with bottom wall if heading down
		if (droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight() && droid.getYSpeed() > 0) {
			droid.setY(getHeight()-droid.getBitmap().getHeight() / 2);
			droid.setYSpeed(0);
			
			
			
			
			
		}
		// check collision with top wall if heading up
		if (droid.getY() - droid.getBitmap().getHeight() / 2 <= 0 && droid.getYSpeed() < 0) {
			droid.setY(droid.getBitmap().getHeight() / 2);
			droid.setYSpeed(0);
			
		}
		// Update the lone droid
		droid.update();
		
		
		//laser removal when out of bounds
		for (int i = 0; i < droid.lasers.size(); i++){
			if (droid.lasers.get(i).animState >= 5){
				droid.removeLaser(i);
			}
			else if (droid.lasers.get(i).getX() > getWidth()+50 || droid.lasers.get(i).getX() < -50 || droid.lasers.get(i).getY() < -50 || droid.lasers.get(i).getY() > getHeight() + 50){
				droid.removeLaser(i);
			}
			else if(droid.lasers.get(i).exploded == false){ 
				if(droid.lasers.get(i).getX() > eDroid.getX() - eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getX() < eDroid.getX() + eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getY() > eDroid.getY() - eDroid.getBitmap().getHeight()/2 && 
					droid.lasers.get(i).getY() < eDroid.getY() + eDroid.getBitmap().getHeight()/2 ){
				droid.lasers.get(i).setExploded();
				eDroid.knockback(droid.lasers.get(i), 5);
				eDroid.fireHit(5);
				
				}
			}
			else if (droid.lasers.get(i).exploded){
				if (System.currentTimeMillis() > droid.lasers.get(i).laserTimer + droid.lasers.get(i).laserTimerDelay){
					if (droid.lasers.get(i).animState == 0){
						droid.lasers.get(i).changeBitmap(laser1);
						droid.lasers.get(i).animState ++;
					}else if (droid.lasers.get(i).animState == 1){
						droid.lasers.get(i).changeBitmap(laser2);
						droid.lasers.get(i).animState ++;
					}else if (droid.lasers.get(i).animState == 2){
						droid.lasers.get(i).changeBitmap(laser3);
						droid.lasers.get(i).animState ++;
					}else if (droid.lasers.get(i).animState == 3){
						droid.lasers.get(i).changeBitmap(laser4);
						droid.lasers.get(i).animState ++;
					}else if (droid.lasers.get(i).animState == 4){
						droid.lasers.get(i).animState ++;
					}
					droid.lasers.get(i).laserTimer = System.currentTimeMillis();
				}
			}
			
		}
		
		for (int i = 0; i < eDroid.lasers.size(); i++){
			if (eDroid.lasers.get(i).animState >= 5){
				eDroid.removeLaser(i);
			}
			else if (eDroid.lasers.get(i).getX() > getWidth()+50 || eDroid.lasers.get(i).getX() < -50 || eDroid.lasers.get(i).getY() < -50 || eDroid.lasers.get(i).getY() > getHeight() + 50){
				eDroid.removeLaser(i);
			}
			else if(eDroid.lasers.get(i).exploded == false && droid.dying == false){ 
				if(eDroid.lasers.get(i).getX() > droid.getX() - droid.getBitmap().getWidth()/2 && 
						eDroid.lasers.get(i).getX() < droid.getX() + droid.getBitmap().getWidth()/2 && 
						eDroid.lasers.get(i).getY() > droid.getY() - droid.getBitmap().getHeight()/2 && 
						eDroid.lasers.get(i).getY() < droid.getY() + droid.getBitmap().getHeight()/2 ){
					eDroid.lasers.get(i).setExploded();
					droid.knockback(eDroid.lasers.get(i), 10);
					droid.fireHit(15);
					
					}
			}else if (eDroid.lasers.get(i).exploded){
				if (System.currentTimeMillis() > eDroid.lasers.get(i).laserTimer + eDroid.lasers.get(i).laserTimerDelay){
					if (eDroid.lasers.get(i).animState == 0){
						eDroid.lasers.get(i).changeBitmap(missile1);
						eDroid.lasers.get(i).animState ++;
					}else if (eDroid.lasers.get(i).animState == 1){
						eDroid.lasers.get(i).changeBitmap(missile2);
						eDroid.lasers.get(i).animState ++;
					}else if (eDroid.lasers.get(i).animState == 2){
						eDroid.lasers.get(i).changeBitmap(missile3);
						eDroid.lasers.get(i).animState ++;
					}else if (eDroid.lasers.get(i).animState == 3){
						eDroid.lasers.get(i).changeBitmap(missile4);
						eDroid.lasers.get(i).animState ++;
					}else if (eDroid.lasers.get(i).animState == 4){
						eDroid.lasers.get(i).animState ++;
					}
					eDroid.lasers.get(i).laserTimer = System.currentTimeMillis();
				}
			}
		}

	}
	
	public void setNewXY(float newX, float newY, float accelX, float accelY){

		droid.newX = newX - offsetX;
		droid.newY = newY - offsetY;
		droid.accelX = accelX - offsetX;
		droid.accelY = accelY - offsetY;
	
	}
	
	public void check1(boolean w1){
		firing1 = w1;
		
	}
	
	public void check2(boolean w2){
		firing2 = w2;
	}
	
}