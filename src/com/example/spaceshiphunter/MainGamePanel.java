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
	long previousTime = 0;
	long enemyDelay = 3000;
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
		droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.player), 50, 50);
		eDroid = new EDroid(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), 600, 400);
		laser = BitmapFactory.decodeResource(getResources(), R.drawable.attack_one);
		missile = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
		
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
		droid.draw(canvas);
		eDroid.draw(canvas);
	}

	public void update() {

		if (eDroid.state == 0){
				
				while (Math.abs(targetX - droid.getX()) < 50 && Math.abs(targetY - droid.getY()) < 50 || targetX == 0){
					targetX = Math.random()*(getWidth()-eDroid.getBitmap().getWidth()) + eDroid.getBitmap().getWidth()/2;
					targetY = Math.random()*(getHeight()-eDroid.getBitmap().getHeight()) + eDroid.getBitmap().getHeight()/2;
				}
			eDroid.setDestination(targetX, targetY);
			previousTime = System.currentTimeMillis();
			eDroid.update();
		}else if (eDroid.state == 1){
			if(System.currentTimeMillis() < previousTime + enemyDelay){
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
		if (firing1){

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
			if (droid.lasers.get(i).getX() > getWidth()+50 || droid.lasers.get(i).getX() < -50 || droid.lasers.get(i).getY() < -50 || droid.lasers.get(i).getY() > getHeight() + 50){
				droid.removeLaser(i);
			}
			else if(droid.lasers.get(i).getX() > eDroid.getX() - eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getX() < eDroid.getX() + eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getY() > eDroid.getY() - eDroid.getBitmap().getHeight()/2 && 
					droid.lasers.get(i).getY() < eDroid.getY() + eDroid.getBitmap().getHeight()/2 &&
					droid.lasers.get(i).exploded == false){
				droid.lasers.get(i).setExploded();
				droid.fireHit(5);
			}
		}
		for (int i = 0; i < eDroid.lasers.size(); i++){
			if (eDroid.lasers.get(i).getX() > getWidth()+50 || eDroid.lasers.get(i).getX() < -50 || eDroid.lasers.get(i).getY() < -50 || eDroid.lasers.get(i).getY() > getHeight() + 50){
				eDroid.removeLaser(i);
			}
			else if(eDroid.lasers.get(i).getX() > droid.getX() - droid.getBitmap().getWidth()/2 && 
					eDroid.lasers.get(i).getX() < droid.getX() + droid.getBitmap().getWidth()/2 && 
					eDroid.lasers.get(i).getY() > droid.getY() - droid.getBitmap().getHeight()/2 && 
					eDroid.lasers.get(i).getY() < droid.getY() + droid.getBitmap().getHeight()/2 &&
					eDroid.lasers.get(i).exploded == false){
				eDroid.lasers.get(i).setExploded();
				eDroid.fireHit(5);


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