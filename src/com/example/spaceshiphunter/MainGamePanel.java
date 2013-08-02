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
import android.util.DisplayMetrics;
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
	Bitmap background1;
	Bitmap background2;
	Bitmap charge01;
	Bitmap charge02;
	Bitmap charge03;
	Bitmap charge04;
	Bitmap charge05;
	Bitmap charge06;
	Bitmap charge07;
	Bitmap charge08;
	Bitmap charge09;
	Bitmap charge10;
	Bitmap charge11;
	Bitmap charge12;
	Bitmap charge13;
	Bitmap charge14;
	Bitmap charge15;
	Bitmap glaser;
	Bitmap gle01;
	Bitmap gle02;
	Bitmap gle03;
	Bitmap gle04;
	Bitmap gle05;
	Bitmap gle06;
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
	float parX2;
	float parY2;
	
	public double scaleFactor = 0;
	boolean loaded = false;
	int defaultDPI = 220;
	private long previousTimeCharge;
	private float chargeModifier = 1;
	private int chargeLevel = 0;


	
	
	
	
	


	public MainGamePanel(Context context) {
		super(context);	
		
		//Context passing
		this.mContext = getContext();
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		
		

			
			//player0 = Bitmap.createScaledBitmap (player0, (int)(player0.getWidth()*scaleFactor), (int)(player0.getWidth()*scaleFactor), false);
		player0 = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		enemy0 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship);
		leftFlash = BitmapFactory.decodeResource(getResources(), R.drawable.leftflash);
		rightFlash = BitmapFactory.decodeResource(getResources(), R.drawable.rightflash);
		eleftFlash = BitmapFactory.decodeResource(getResources(), R.drawable.eleftflash2);
		erightFlash = BitmapFactory.decodeResource(getResources(), R.drawable.erightflash2);

		booster1 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters1);
		booster2 = BitmapFactory.decodeResource(getResources(), R.drawable.boosters2);
		ebooster1 = BitmapFactory.decodeResource(getResources(), R.drawable.eboosters12);
		ebooster2 = BitmapFactory.decodeResource(getResources(), R.drawable.eboosters22);
		
		eDroid = new EDroid(enemy0, 1500, 1500, eleftFlash, erightFlash, ebooster1, ebooster2);
		droid = new Droid(player0, 50, 50, leftFlash, rightFlash, booster1, booster2);
		
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
		charge01 = BitmapFactory.decodeResource(getResources(), R.drawable.gl01);
		charge02 = BitmapFactory.decodeResource(getResources(), R.drawable.gl02);
		charge03 = BitmapFactory.decodeResource(getResources(), R.drawable.gl03);
		charge04 = BitmapFactory.decodeResource(getResources(), R.drawable.gl04);
		charge05 = BitmapFactory.decodeResource(getResources(), R.drawable.gl05);
		charge06 = BitmapFactory.decodeResource(getResources(), R.drawable.gl06);
		charge07 = BitmapFactory.decodeResource(getResources(), R.drawable.gl07);
		charge08 = BitmapFactory.decodeResource(getResources(), R.drawable.gl08);
		charge09 = BitmapFactory.decodeResource(getResources(), R.drawable.gl09);
		charge10 = BitmapFactory.decodeResource(getResources(), R.drawable.gl10);
		charge11 = BitmapFactory.decodeResource(getResources(), R.drawable.gl11);
		charge12 = BitmapFactory.decodeResource(getResources(), R.drawable.gl12);
		charge13 = BitmapFactory.decodeResource(getResources(), R.drawable.gl13);
		charge14 = BitmapFactory.decodeResource(getResources(), R.drawable.gl14);
		charge15 = BitmapFactory.decodeResource(getResources(), R.drawable.gl15);
		glaser = BitmapFactory.decodeResource(getResources(), R.drawable.greenlaser);
		gle01 = BitmapFactory.decodeResource(getResources(), R.drawable.gle01);
		gle02 = BitmapFactory.decodeResource(getResources(), R.drawable.gle02);
		gle03 = BitmapFactory.decodeResource(getResources(), R.drawable.gle03);
		gle04 = BitmapFactory.decodeResource(getResources(), R.drawable.gle04);
		gle05 = BitmapFactory.decodeResource(getResources(), R.drawable.gle05);
		gle06 = BitmapFactory.decodeResource(getResources(), R.drawable.gle06);
		
		centerMarker = BitmapFactory.decodeResource(getResources(), R.drawable.centermark);
		marker3 = new Marker(marker,0, 0, 3);
		marker1 = new Marker(marker,0, 0, 1);
		marker2 = new Marker(marker,0, 0, 2);
		marker4 = new Marker(marker,0, 0, 4);
		cMarker = new Marker(centerMarker,0,0, 5);
		cMarker2 = new Marker(centerMarker,0,0, 6);
		cMarker3 = new Marker(centerMarker,0,0, 7);
		cMarker4 = new Marker(centerMarker,0,0, 8);
		
		double bgdecide = Math.random();
		
		if (bgdecide <=0.5){
			background1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
		}
		
		else{
			background1 = BitmapFactory.decodeResource(getResources(), R.drawable.battleground2);
		}
		
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
		
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
		//resize all bitmaps based on device's dpi
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		Log.d("Game", ""+ metrics.xdpi);
		scaleFactor = ((double)metrics.xdpi/defaultDPI)*1.1;
		droid.scaleFactor = scaleFactor;
		eDroid.scaleFactor = scaleFactor;
		player0 = Bitmap.createScaledBitmap (player0, (int)(player0.getWidth()/scaleFactor), (int)(player0.getHeight()/scaleFactor), true);
		enemy0 = Bitmap.createScaledBitmap (enemy0, (int)(enemy0.getWidth()/scaleFactor), (int)(enemy0.getHeight()/scaleFactor), true);
		droid.setBitmap(player0);
		eDroid.setBitmap(enemy0);
		laser = Bitmap.createScaledBitmap (laser, (int)(laser.getWidth()/scaleFactor), (int)(laser.getHeight()/scaleFactor), true);
		laser1 = Bitmap.createScaledBitmap (laser1, (int)(laser1.getWidth()/scaleFactor), (int)(laser1.getHeight()/scaleFactor), true);
		laser2 = Bitmap.createScaledBitmap (laser2, (int)(laser2.getWidth()/scaleFactor), (int)(laser2.getHeight()/scaleFactor), true);
		laser3 = Bitmap.createScaledBitmap (laser3, (int)(laser3.getWidth()/scaleFactor), (int)(laser3.getHeight()/scaleFactor), true);
		laser4 = Bitmap.createScaledBitmap (laser4, (int)(laser4.getWidth()/scaleFactor), (int)(laser4.getHeight()/scaleFactor), true);
		missile = Bitmap.createScaledBitmap (missile, (int)(missile.getWidth()/scaleFactor), (int)(missile.getHeight()/scaleFactor), true);
		missile1 = Bitmap.createScaledBitmap (missile1, (int)(missile1.getWidth()/scaleFactor), (int)(missile1.getHeight()/scaleFactor), true);
		missile2 = Bitmap.createScaledBitmap (missile2, (int)(missile2.getWidth()/scaleFactor), (int)(missile2.getHeight()/scaleFactor), true);
		missile3 = Bitmap.createScaledBitmap (missile3, (int)(missile3.getWidth()/scaleFactor), (int)(missile3.getHeight()/scaleFactor), true);
		missile4 = Bitmap.createScaledBitmap (missile4, (int)(missile4.getWidth()/scaleFactor), (int)(missile4.getHeight()/scaleFactor), true);
		leftFlash = Bitmap.createScaledBitmap (leftFlash, (int)(leftFlash.getWidth()/scaleFactor), (int)(leftFlash.getHeight()/scaleFactor), true);
		rightFlash = Bitmap.createScaledBitmap (rightFlash, (int)(rightFlash.getWidth()/scaleFactor), (int)(rightFlash.getHeight()/scaleFactor), true);
		eleftFlash = Bitmap.createScaledBitmap (eleftFlash, (int)(eleftFlash.getWidth()/scaleFactor), (int)(eleftFlash.getHeight()/scaleFactor), true);
		erightFlash = Bitmap.createScaledBitmap (erightFlash, (int)(erightFlash.getWidth()/scaleFactor), (int)(erightFlash.getHeight()/scaleFactor), true);
		
		booster1 = Bitmap.createScaledBitmap (booster1, (int)(booster1.getWidth()/scaleFactor), (int)(booster1.getHeight()/scaleFactor), true);
		booster2 = Bitmap.createScaledBitmap (booster2, (int)(booster2.getWidth()/scaleFactor), (int)(booster2.getHeight()/scaleFactor), true);
		ebooster1 = Bitmap.createScaledBitmap (ebooster1, (int)(ebooster1.getWidth()/scaleFactor), (int)(ebooster1.getHeight()/scaleFactor), true);
		ebooster2 = Bitmap.createScaledBitmap (ebooster2, (int)(ebooster2.getWidth()/scaleFactor), (int)(ebooster2.getHeight()/scaleFactor), true);
		charge01 = Bitmap.createScaledBitmap (charge01, (int)(charge01.getWidth()/scaleFactor), (int)(charge01.getHeight()/scaleFactor), true);
		charge02 = Bitmap.createScaledBitmap (charge02, (int)(charge02.getWidth()/scaleFactor), (int)(charge02.getHeight()/scaleFactor), true);
		charge03 = Bitmap.createScaledBitmap (charge03, (int)(charge03.getWidth()/scaleFactor), (int)(charge03.getHeight()/scaleFactor), true);
		charge04 = Bitmap.createScaledBitmap (charge04, (int)(charge04.getWidth()/scaleFactor), (int)(charge04.getHeight()/scaleFactor), true);
		charge05 = Bitmap.createScaledBitmap (charge05, (int)(charge05.getWidth()/scaleFactor), (int)(charge05.getHeight()/scaleFactor), true);
		charge06 = Bitmap.createScaledBitmap (charge06, (int)(charge06.getWidth()/scaleFactor), (int)(charge06.getHeight()/scaleFactor), true);
		charge07 = Bitmap.createScaledBitmap (charge07, (int)(charge07.getWidth()/scaleFactor), (int)(charge07.getHeight()/scaleFactor), true);
		charge08 = Bitmap.createScaledBitmap (charge08, (int)(charge08.getWidth()/scaleFactor), (int)(charge08.getHeight()/scaleFactor), true);
		charge09 = Bitmap.createScaledBitmap (charge09, (int)(charge09.getWidth()/scaleFactor), (int)(charge09.getHeight()/scaleFactor), true);
		charge10 = Bitmap.createScaledBitmap (charge10, (int)(charge10.getWidth()/scaleFactor), (int)(charge10.getHeight()/scaleFactor), true);
		charge11 = Bitmap.createScaledBitmap (charge11, (int)(charge11.getWidth()/scaleFactor), (int)(charge11.getHeight()/scaleFactor), true);
		charge12 = Bitmap.createScaledBitmap (charge12, (int)(charge12.getWidth()/scaleFactor), (int)(charge12.getHeight()/scaleFactor), true);
		charge13 = Bitmap.createScaledBitmap (charge13, (int)(charge13.getWidth()/scaleFactor), (int)(charge13.getHeight()/scaleFactor), true);
		charge14 = Bitmap.createScaledBitmap (charge14, (int)(charge14.getWidth()/scaleFactor), (int)(charge14.getHeight()/scaleFactor), true);
		glaser = Bitmap.createScaledBitmap (glaser, (int)(glaser.getWidth()/scaleFactor), (int)(glaser.getHeight()/scaleFactor), true);
		gle01 = Bitmap.createScaledBitmap (gle01, (int)(glaser.getWidth()/scaleFactor), (int)(gle01.getHeight()/scaleFactor), true);
		gle02 = Bitmap.createScaledBitmap (gle02, (int)(glaser.getWidth()/scaleFactor), (int)(gle02.getHeight()/scaleFactor), true);
		gle03 = Bitmap.createScaledBitmap (gle03, (int)(glaser.getWidth()/scaleFactor), (int)(gle03.getHeight()/scaleFactor), true);
		gle04 = Bitmap.createScaledBitmap (gle04, (int)(glaser.getWidth()/scaleFactor), (int)(gle04.getHeight()/scaleFactor), true);
		gle05 = Bitmap.createScaledBitmap (gle05, (int)(glaser.getWidth()/scaleFactor), (int)(gle05.getHeight()/scaleFactor), true);
		gle06 = Bitmap.createScaledBitmap (gle06, (int)(glaser.getWidth()/scaleFactor), (int)(gle06.getHeight()/scaleFactor), true);
		background2 = Bitmap.createScaledBitmap (background2, (int)(background2.getWidth()/scaleFactor), (int)(background2.getHeight()/scaleFactor), true);
		droid.booster1 = booster1;
		droid.booster2 = booster2;
		droid.leftFlash = leftFlash;
		droid.rightFlash = rightFlash;
		eDroid.booster1 = ebooster1;
		eDroid.booster2 = ebooster2;
		eDroid.leftFlash = eleftFlash;
		eDroid.rightFlash = erightFlash;
		
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
		laser.recycle();
		missile.recycle();
		missile1.recycle();
		missile2.recycle();
		missile3.recycle();
		missile4.recycle();
		leftFlash.recycle();
		rightFlash.recycle();
		eleftFlash.recycle();
		erightFlash.recycle();
		laser.recycle();
		laser2.recycle();
		laser3.recycle();
		laser4.recycle();
		booster1.recycle();
		booster2.recycle();
		ebooster1.recycle();
		ebooster2.recycle();
		marker.recycle();
		centerMarker.recycle();
		background1.recycle();
		background2.recycle();
		charge01.recycle();
		charge02.recycle();
		charge03.recycle();
		charge04.recycle();
		charge05.recycle();
		charge06.recycle();
		charge07.recycle();
		charge08.recycle();
		charge09.recycle();
		charge10.recycle();
		charge11.recycle();
		charge12.recycle();
		charge13.recycle();
		charge14.recycle();
		charge15.recycle();
		glaser.recycle();
		gle01.recycle();
		gle02.recycle();
		gle03.recycle();
		gle04.recycle();
		gle05.recycle();
		gle06.recycle();
		Log.d(TAG, "Thread was shut down cleanly");
	}
	

	public void render(Canvas canvas) {
		
		//draw background
		canvas.drawColor(Color.BLACK);
		parX= (float) (((Mission.dispXY.x)/2 - 360)  + ((0-droid.x)*0.3));
		parY = (float) (((Mission.dispXY.y)/2 - 216) + ((0-droid.y)*0.3));
		
		parX2= (float) (((Mission.dispXY.x)/2 + 310)  + ((0-droid.x)*0.4));
		parY2= (float) (((Mission.dispXY.y)/2 - 50) + ((0-droid.y)*0.4));
	
		
		canvas.drawBitmap(background1, parX, parY, null);
		canvas.drawBitmap(background2,parX2, parY2, null);
		
		eDroid.draw(canvas);
		for ( int i = 0; i < droid.lasers.size(); i++ ) {
			droid.lasers.get(i).draw(canvas);
			
		}
		for ( int i = 0; i < droid.glasers.size(); i++ ) {
			droid.glasers.get(i).draw(canvas);
			
		}
		droid.draw(canvas);
		for ( int i = 0; i < eDroid.lasers.size(); i++ ) {
			eDroid.lasers.get(i).draw(canvas);
			
	}
		//draw markers
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
		
	// timer for score screen
		if	(startTime == 0){
			startTime = System.currentTimeMillis();
		}
		//game end intents
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
				player1 = Bitmap.createScaledBitmap (player1, (int)(player1.getWidth()/scaleFactor), (int)(player1.getHeight()/scaleFactor), true);
				player0.recycle();
			}
			droid.changeBaseBitmap(player1);
			droidFrame = 1;
			}
		if (droid.healthPoints <= 100 && droidFrame == 1){
			if (player2 == null){
				player2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
				player2 = Bitmap.createScaledBitmap (player2, (int)(player2.getWidth()/scaleFactor), (int)(player2.getHeight()/scaleFactor), true);
				player1.recycle();
			}
			droid.changeBaseBitmap(player2);
			droidFrame = 2;
			}
		if (droid.healthPoints <= 50 && droidFrame == 2){
			Game.spool.play(Game.lowhealth, Game.volume,Game.volume, 5, 0, 1);
			if (player3 == null){
				player3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
				player3 = Bitmap.createScaledBitmap (player3, (int)(player3.getWidth()/scaleFactor), (int)(player3.getHeight()/scaleFactor), true);
				player2.recycle();	
				
			}
			droid.changeBaseBitmap(player3);
			droidFrame = 3;
			}
		
		//enemy ship damage
		if (eDroid.healthPoints <= 150 && eDroidFrame == 0){
			if (enemy1== null){
				enemy1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship1);
				enemy1 = Bitmap.createScaledBitmap (enemy1, (int)(enemy1.getWidth()/scaleFactor), (int)(enemy1.getHeight()/scaleFactor), true);
				enemy0.recycle();
			}
			eDroid.changeBaseBitmap(enemy1);
			eDroidFrame = 1;
			}
		if (eDroid.healthPoints <= 100 && eDroidFrame == 1){
			if (enemy2== null){
				enemy2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship2);
				enemy2 = Bitmap.createScaledBitmap (enemy2, (int)(enemy2.getWidth()/scaleFactor), (int)(enemy2.getHeight()/scaleFactor), true);
				enemy1.recycle();
			}
			eDroid.changeBaseBitmap(enemy2);
			eDroidFrame = 2;
			}
		if (eDroid.healthPoints <= 50 && eDroidFrame == 2){
			if (enemy3== null){
				enemy3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship3);
				enemy3 = Bitmap.createScaledBitmap (enemy3, (int)(enemy3.getWidth()/scaleFactor), (int)(enemy3.getHeight()/scaleFactor), true);
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
					playerd0 = Bitmap.createScaledBitmap (playerd0, (int)(playerd0.getWidth()/scaleFactor), (int)(playerd0.getHeight()/scaleFactor), true);
				}
				droid.changeBaseBitmap(playerd0);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
				
				
				Game.spool.play(Game.playerdeathsfx,Game.volume,Game.volume, 1, 0, 1);
				Game.vb.vibrate(1000);
				
				
			}else if (droidFrame == 4 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd1 == null){
					playerd1 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death1);
					playerd1 = Bitmap.createScaledBitmap (playerd1, (int)(playerd1.getWidth()/scaleFactor), (int)(playerd1.getHeight()/scaleFactor), true);
				}
				droid.changeBaseBitmap(playerd1);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 5 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd2 == null){
					playerd2 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death2);
					playerd2 = Bitmap.createScaledBitmap (playerd2, (int)(playerd2.getWidth()/scaleFactor), (int)(playerd2.getHeight()/scaleFactor), true);
				}
				droid.changeBaseBitmap(playerd2);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 6 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd3 == null){
					playerd3 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death3);
					playerd3 = Bitmap.createScaledBitmap (playerd3, (int)(playerd3.getWidth()/scaleFactor), (int)(playerd3.getHeight()/scaleFactor), true);
				}
				droid.changeBaseBitmap(playerd3);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 7 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd4 == null){
					playerd4 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death4);
					playerd4 = Bitmap.createScaledBitmap (playerd4, (int)(playerd4.getWidth()/scaleFactor), (int)(playerd4.getHeight()/scaleFactor), true);
				}
				droid.changeBaseBitmap(playerd4);
				droidTimer = System.currentTimeMillis();
				droidFrame++;
			}else if (droidFrame == 8 && System.currentTimeMillis() > droidTimer + droidTimerDelay){
				if (playerd5 == null){
					playerd5 = BitmapFactory.decodeResource(getResources(), R.drawable.player_death5);
					playerd5 = Bitmap.createScaledBitmap (playerd5, (int)(playerd5.getWidth()/scaleFactor), (int)(playerd5.getHeight()/scaleFactor), true);
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
					enemyd0 = Bitmap.createScaledBitmap (enemyd0, (int)(enemyd0.getWidth()/scaleFactor), (int)(enemyd0.getHeight()/scaleFactor), true);
				}
				eDroid.changeBaseBitmap(enemyd0);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
				Game.spool.play(Game.enemydeathsfx, Game.volume, Game.volume, 1, 0, 1);
				
				
			}else if (eDroidFrame == 4 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd1 == null){
					enemyd1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd1);
					enemyd1 = Bitmap.createScaledBitmap (enemyd1, (int)(enemyd1.getWidth()/scaleFactor), (int)(enemyd1.getHeight()/scaleFactor), true);
				}
				eDroid.changeBaseBitmap(enemyd1);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 5 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd2 == null){
					enemyd2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd2);
					enemyd2 = Bitmap.createScaledBitmap (enemyd2, (int)(enemyd2.getWidth()/scaleFactor), (int)(enemyd2.getHeight()/scaleFactor), true);
				}
				eDroid.changeBaseBitmap(enemyd2);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 6 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd3 == null){
					enemyd3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd3);
					enemyd3 = Bitmap.createScaledBitmap (enemyd3, (int)(enemyd3.getWidth()/scaleFactor), (int)(enemyd3.getHeight()/scaleFactor), true);
				}
				eDroid.changeBaseBitmap(enemyd3);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 7 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd4 == null){
					enemyd4 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd4);
					enemyd4 = Bitmap.createScaledBitmap (enemyd4, (int)(enemyd4.getWidth()/scaleFactor), (int)(enemyd4.getHeight()/scaleFactor), true);
				}
				eDroid.changeBaseBitmap(enemyd4);
				eDroidTimer = System.currentTimeMillis();
				eDroidFrame++;
			}else if (eDroidFrame == 8 && System.currentTimeMillis() > eDroidTimer + eDroidTimerDelay){
				if (enemyd5 == null){
					enemyd5 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyd5);
					enemyd5 = Bitmap.createScaledBitmap (enemyd5, (int)(enemyd5.getWidth()/scaleFactor), (int)(enemyd5.getHeight()/scaleFactor), true);
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
		//firing animation and timer for secondary attack
		if (firing2 && droid.healthPoints > 0){
			if (chargeLevel == 0){
				droid.charge = charge01;
				droid.charging = true;
				chargeLevel = 1;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 1 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge02;
				chargeLevel = 2;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 2 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge03;
				chargeLevel = 3;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 3 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge04;
				chargeLevel = 4;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 4 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge05;
				chargeLevel = 5;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 5 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge06;
				chargeLevel = 6;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 6 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge07;
				chargeLevel = 7;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 7 && System.currentTimeMillis() > previousTimeCharge + 100){
				droid.charge = charge08;
				chargeLevel = 8;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 8 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge09;
				chargeLevel = 9;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 9 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge10;
				chargeLevel = 10;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 10 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge11;
				chargeLevel = 11;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 11 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge12;
				chargeLevel = 12;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 12 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge13;
				chargeLevel = 13;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 13 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge14;
				chargeLevel = 14;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 14 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge15;
				chargeLevel = 15;
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 15 && System.currentTimeMillis() > previousTimeCharge + 50){
				droid.charge = charge15;
				chargeLevel = 16;
				droid.charging = false;
				droid.fireGLaser(glaser);
				previousTimeCharge = System.currentTimeMillis();
			}
			else if (chargeLevel == 16){
				chargeLevel = 0;
			}
		}else{
			droid.charging = false;
			droid.charge = charge01;
			chargeLevel = 0;
		}
		//firing mechanism for first attack
		if(firing1 && droid.healthPoints > 0 && firing2 == false){
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
			//collision detection for lasers to enemy ship
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
			//laser explosion animation
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
		for (int i = 0; i < droid.glasers.size(); i++){
			if (droid.glasers.get(i).animState >= 6){
				droid.removegLaser(i);
			}
			else if (droid.glasers.get(i).getX() > getWidth()+50 || droid.glasers.get(i).getX() < -50 || droid.glasers.get(i).getY() < -50 || droid.glasers.get(i).getY() > getHeight() + 50){
				droid.removegLaser(i);
			}
			//collision detection of secondary lasers
			else if(droid.glasers.get(i).exploded == false && eDroid.dying == false){ 
				if(droid.glasers.get(i).getX() > eDroid.getX() - eDroid.getBitmap().getWidth()/2  - droid.glasers.get(i).getBitmap().getWidth()/2 && 
					droid.glasers.get(i).getX() < eDroid.getX() + eDroid.getBitmap().getWidth()/2 + droid.glasers.get(i).getBitmap().getWidth()/2 && 
					droid.glasers.get(i).getY() > eDroid.getY() - eDroid.getBitmap().getHeight()/2  - droid.glasers.get(i).getBitmap().getHeight()/2 && 
					droid.glasers.get(i).getY() < eDroid.getY() + eDroid.getBitmap().getHeight()/2 + droid.glasers.get(i).getBitmap().getHeight()/2 ){
				droid.glasers.get(i).setExploded();
				eDroid.knockback(droid.glasers.get(i), 5);
				eDroid.fireHit(20);
				shotsHit++;
				}
			}
			//animation for secondary laser
			else if (droid.glasers.get(i).exploded){
				if (System.currentTimeMillis() > droid.glasers.get(i).laserTimer + droid.glasers.get(i).laserTimerDelay){
					if (droid.glasers.get(i).animState == 0){
						droid.glasers.get(i).changeBitmap(gle01);
						droid.glasers.get(i).animState ++;
					}else if (droid.glasers.get(i).animState == 1){
						droid.glasers.get(i).changeBitmap(gle02);
						droid.glasers.get(i).animState ++;
					}else if (droid.glasers.get(i).animState == 2){
						droid.glasers.get(i).changeBitmap(gle03);
						droid.glasers.get(i).animState ++;
					}else if (droid.glasers.get(i).animState == 3){
						droid.glasers.get(i).changeBitmap(gle04);
						droid.glasers.get(i).animState ++;
					}else if (droid.glasers.get(i).animState == 4){
						droid.glasers.get(i).changeBitmap(gle05);
						droid.glasers.get(i).animState ++;
					}else if (droid.glasers.get(i).animState == 5){
						droid.glasers.get(i).animState ++;
					}
					droid.glasers.get(i).laserTimer = System.currentTimeMillis();
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
			//collision detection for enemy missles
			else if(eDroid.lasers.get(i).exploded == false && droid.dying == false){ 
				if(eDroid.lasers.get(i).getX() > droid.getX() - droid.getBitmap().getWidth()/2 && 
						eDroid.lasers.get(i).getX() < droid.getX() + droid.getBitmap().getWidth()/2 && 
						eDroid.lasers.get(i).getY() > droid.getY() - droid.getBitmap().getHeight()/2 && 
						eDroid.lasers.get(i).getY() < droid.getY() + droid.getBitmap().getHeight()/2 ){
					eDroid.lasers.get(i).setExploded();
					droid.knockback(eDroid.lasers.get(i), 10);
					droid.fireHit(10);
					
					
					
					
					}
				//animation for enemy missles
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
		//update marker position
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