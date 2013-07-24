package com.example.spaceshiphunter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;
import com.example.spaceshiphunter.Mission;


public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback{

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Droid droid;
	private EDroid eDroid;
	private Marker marker3;
	private Marker marker1;
	private Marker marker2;
	private Marker marker4;
	private Marker cMarker;
	private Marker cMarker2;
	private Marker cMarker3;
	private Marker cMarker4;
	Bitmap laser;
	Bitmap missile;
	Bitmap missile1;
	Bitmap missile2;
	Bitmap missile3;
	Bitmap missile4;
	Bitmap leftFlash;
	Bitmap rightFlash;
	Bitmap eleftFlash;
	Bitmap erightFlash;
	Bitmap laser1;
	Bitmap laser2;
	Bitmap laser3;
	Bitmap laser4;
	Bitmap player0;
	Bitmap player1;
	Bitmap player2;
	Bitmap player3;
	Bitmap enemy0;
	Bitmap enemy1;
	Bitmap enemy2;
	Bitmap enemy3;
	Bitmap playerd0;
	Bitmap playerd1;
	Bitmap playerd2;
	Bitmap playerd3;
	Bitmap playerd4;
	Bitmap playerd5;
	Bitmap enemyd0;
	Bitmap enemyd1;
	Bitmap enemyd2;
	Bitmap enemyd3;
	Bitmap enemyd4;
	Bitmap enemyd5;
	Bitmap booster1;
	Bitmap booster2;
	Bitmap ebooster1;
	Bitmap ebooster2;
	Bitmap marker;
	Bitmap centerMarker;
	Bitmap background;
	long previousTime = 0;
	long enemyDelay = 3000;
	int droidFrame = 0;
	int eDroidFrame = 0;
	long droidTimer;
	long eDroidTimer;
	long eDroidTimerDelay = 150;
	long droidTimerDelay = 150;
	private double targetX = 0;
	private double targetY;
	float offsetX = 0;
	float offsetY = 3;
	boolean firing1 = false;
	boolean firing2 = false;
	boolean gameEnded = false;
	protected static Context mContext;
	SoundPool spool;
	
	private int shotsHit = 0;
	private long timeElapsed;
	private long startTime = 0;
	float parX;
	float parY;

	
	
	
	
	


	public MainGamePanel(Context context) {
		super(context);	
		
		//Context passing
		this.mContext = getContext();
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		player0 = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		//player0 = Bitmap.createScaledBitmap (player0, player0.getWidth()/2, player0.getWidth()/2, false);
		enemy0 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship);
		
		
		leftFlash = BitmapFactory.decodeResource(getResources(), R.drawable.leftflash);
		rightFlash = BitmapFactory.decodeResource(getResources(), R.drawable.rightflash);
		eleftFlash = BitmapFactory.decodeResource(getResources(), R.drawable.eleftflash2);
		erightFlash = BitmapFactory.decodeResource(getResources(), R.drawable.erightflash2);

		booster1 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters1);
		booster2 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters2);
		ebooster1 = BitmapFactory.decodeResource(getResources(), R.drawable.eboosters12);
		ebooster2 = BitmapFactory.decodeResource(getResources(), R.drawable.eboosters22);
		droid = new Droid(player0, 50, 50, leftFlash, rightFlash, booster1, booster2);
		eDroid = new EDroid(enemy0, 1500, 1500, eleftFlash, erightFlash, ebooster1, ebooster2);
		
		
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
		marker = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
		centerMarker = BitmapFactory.decodeResource(getResources(), R.drawable.centermark);
		marker3 = new Marker(marker,0, 0, 3);
		marker1 = new Marker(marker,0, 0, 1);
		marker2 = new Marker(marker,0, 0, 2);
		marker4 = new Marker(marker,0, 0, 4);
		cMarker = new Marker(centerMarker,0,0, 5);
		cMarker2 = new Marker(centerMarker,0,0, 6);
		cMarker3 = new Marker(centerMarker,0,0, 7);
		cMarker4 = new Marker(centerMarker,0,0, 8);
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.battleground2);
		
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
		parX= (float) (((Mission.dispXY.x)/2 - 360)  + ((0-droid.x)*0.3));
		parY = (float) (((Mission.dispXY.y)/2 - 216) + ((0-droid.y)*0.3));
		canvas.drawBitmap(background, parX, parY, null);
		
		eDroid.draw(canvas);
		for ( int i = 0; i < droid.lasers.size(); i++ ) {
			droid.lasers.get(i).draw(canvas);
			
		}
		droid.draw(canvas);
		for ( int i = 0; i < eDroid.lasers.size(); i++ ) {
			eDroid.lasers.get(i).draw(canvas);
			
	}
		cMarker.draw(canvas);
		cMarker2.draw(canvas);
		cMarker3.draw(canvas);
		cMarker4.draw(canvas);
		marker3.draw(canvas);
		marker1.draw(canvas);
		marker2.draw(canvas);
		marker4.draw(canvas);

	}

	public void update() {
		if	(startTime == 0){
			startTime = System.currentTimeMillis();
		}
		if (gameEnded == false){
			if(droid.end){
				timeElapsed = System.currentTimeMillis() - startTime;
				Context context = getContext();
				Intent i = new Intent(context, Score.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				i.putExtra("Winner", "enemy" );
				i.putExtra("ShotsFired", droid.shotsFired );
				i.putExtra("ShotsHit", shotsHit );
				i.putExtra("HealthRemaining", eDroid.healthPoints );
				i.putExtra("MaxHealth", eDroid.maxHealth );
				i.putExtra("TimeElapsed", timeElapsed );
		    	context.startActivity(i); 
		    	((Activity)(context)).finish();
		    	gameEnded = true;    	
		    	
			}
			


			else if(eDroid.end){
				timeElapsed = System.currentTimeMillis() - startTime;
				Context context = getContext();
				Intent i = new Intent(context, Score.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				i.putExtra("Winner", "player");
				i.putExtra("ShotsFired", droid.shotsFired );
				i.putExtra("ShotsHit", shotsHit );
				i.putExtra("HealthRemaining", droid.healthPoints );
				i.putExtra("MaxHealth", droid.maxHealth );
				i.putExtra("TimeElapsed", timeElapsed );
		    	context.startActivity(i); 
		    	((Activity)(context)).finish();
		    	gameEnded = true;
		    	
			}
		}
		


		//set player ship image based on damage
		if (droid.healthPoints <= 150 && droidFrame == 0){
			if (player1 == null){
				player1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
				player0.recycle();
			}
			droid.changeBaseBitmap(player1);
			droidFrame = 1;
			}
		if (droid.healthPoints <= 100 && droidFrame == 1){
			if (player2 == null){
				player2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
				player1.recycle();
			}
			droid.changeBaseBitmap(player2);
			droidFrame = 2;
			}
		if (droid.healthPoints <= 50 && droidFrame == 2){
			if (player3 == null){
				player3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
				player2.recycle();
			}
			droid.changeBaseBitmap(player3);
			droidFrame = 3;
			}
		
		//enemy ship damage
		if (eDroid.healthPoints <= 150 && eDroidFrame == 0){
			if (enemy1== null){
				enemy1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship1);
				enemy0.recycle();
			}
			eDroid.changeBaseBitmap(enemy1);
			eDroidFrame = 1;
			}
		if (eDroid.healthPoints <= 100 && eDroidFrame == 1){
			if (enemy2== null){
				enemy2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship2);
				enemy1.recycle();
			}
			eDroid.changeBaseBitmap(enemy2);
			eDroidFrame = 2;
			}
		if (eDroid.healthPoints <= 50 && eDroidFrame == 2){
			if (enemy3== null){
				enemy3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship3);
				enemy2.recycle();
			}
			eDroid.changeBaseBitmap(enemy3);
			eDroidFrame = 3;
			}
		
		//player death animation
		if (droid.healthPoints <= 0 ){
				
			if (droidFrame == 3)	{
				droid.dying = true;
				if (playerd0 == null){
					playerd0 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death0);
				}
				droid.changeBaseBitmap(playerd0);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
				
				Game.spool.play(Game.playerdeathsfx,Game.volume,Game.volume, 1, 0, 1);
				Game.vb.vibrate(1000);
				
				
			}else if (droidFrame == 4 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd1 == null){
					playerd1 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death1);
				}
				droid.changeBaseBitmap(playerd1);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 5 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd2 == null){
					playerd2 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death2);
				}
				droid.changeBaseBitmap(playerd2);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 6 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd3 == null){
					playerd3 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death3);
				}
				droid.changeBaseBitmap(playerd3);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 7 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd4 == null){
					playerd4 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death4);
				}
				droid.changeBaseBitmap(playerd4);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 8 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd5 == null){
					playerd5 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death5);
				}
				droid.changeBaseBitmap(playerd5);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 9 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				droid.dead = true;
				droidFrame++;
			}else if (droidFrame == 10 && System.currentTimeMillis() > droidTimer + droidTimerDelay*20){
				droid.end = true;
				droidFrame++;
			}
		}
		
		//enemy death animation
		if (eDroid.healthPoints <= 0 ){
				
			if (eDroidFrame == 3)	{
				eDroid.dying = true;
				if (enemyd0 == null){
					enemyd0 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd0);
				}
				eDroid.changeBaseBitmap(enemyd0);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
				Game.spool.play(Game.enemydeathsfx, Game.volume, Game.volume, 1, 0, 1);
				
				
			}else if (eDroidFrame == 4 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd1 == null){
					enemyd1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd1);
				}
				eDroid.changeBaseBitmap(enemyd1);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 5 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd2 == null){
					enemyd2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd2);
				}
				eDroid.changeBaseBitmap(enemyd2);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 6 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd3 == null){
					enemyd3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd3);
				}
				eDroid.changeBaseBitmap(enemyd3);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 7 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd4 == null){
					enemyd4 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd4);
				}
				eDroid.changeBaseBitmap(enemyd4);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 8 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd5 == null){
					enemyd5 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd5);
				}
				eDroid.changeBaseBitmap(enemyd5);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 9 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				eDroid.dead = true;
				eDroidFrame++;
			}else if (eDroidFrame == 10 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay*20){
				eDroid.end = true;
				eDroidFrame++;
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
					//sPool.play(R.raw.missile, 1, 1, 1, 0, 1);
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
			else if(droid.lasers.get(i).exploded == false && eDroid.dying == false){ 
				if(droid.lasers.get(i).getX() > eDroid.getX() - eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getX() < eDroid.getX() + eDroid.getBitmap().getWidth()/2 && 
					droid.lasers.get(i).getY() > eDroid.getY() - eDroid.getBitmap().getHeight()/2 && 
					droid.lasers.get(i).getY() < eDroid.getY() + eDroid.getBitmap().getHeight()/2 ){
				droid.lasers.get(i).setExploded();
				eDroid.knockback(droid.lasers.get(i), 5);
				eDroid.fireHit(5);
				shotsHit++;
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
					droid.fireHit(10);
					
					
					
					
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
		cMarker.update(getHeight(), getWidth());
		cMarker2.update(getHeight(), getWidth());
		cMarker3.update(getHeight(), getWidth());
		cMarker4.update(getHeight(), getWidth());
		marker3.update(getHeight(), getWidth());
		marker1.update(getHeight(), getWidth());
		marker2.update(getHeight(), getWidth());
		marker4.update(getHeight(), getWidth());
		
		

	}
	
	public void setNewXY(float newX, float newY, float accelX, float accelY){

		droid.newX = newX - offsetX;
		droid.newY = newY - offsetY;
		droid.accelX = accelX - offsetX;
		droid.accelY = accelY - offsetY;
		marker3.accelX= accelX - offsetX;
		marker3.accelY= accelY - offsetY;
		marker1.accelX= accelX - offsetX;
		marker1.accelY= accelY - offsetY;
		marker2.accelX= accelX - offsetX;
		marker2.accelY= accelY - offsetY;
		marker4.accelX= accelX - offsetX;
		marker4.accelY= accelY - offsetY;
		
	
	}
	
	public void check1(boolean w1){
		firing1 = w1;
	
		
	}
	
	public void check2(boolean w2){
		firing2 = w2;
		
	}

	
}