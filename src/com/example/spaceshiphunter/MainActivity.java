package com.example.spaceshiphunter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener {
	//file for saved preferences
	public static final String PREFS_NAME = "MyPrefsFile";
	MediaPlayer mp;
	ToggleButton musicToggle;
	ImageButton missionButton;
	ImageButton hangerButton;
	RelativeLayout screenLayout;
	int marginX = 250;
	int marginY = 50;
	static boolean silent;
	static float volume = 0.35f;
	static SoundPool spool;
	static int buttonsfx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Sound effects
		spool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		buttonsfx = spool.load(this, R.raw.buttonpress,0);
		
		
		
		musicToggle = (ToggleButton) findViewById(R.id.soundToggle);
		musicToggle.setOnClickListener(this);
		
		missionButton = new ImageButton(this);
		missionButton.setBackgroundResource(R.drawable.mission);
		missionButton.setId(10);
		RelativeLayout.LayoutParams b1layout = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		b1layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		b1layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		b1layout.rightMargin=marginX;
		b1layout.bottomMargin=marginY;
		
		missionButton.setLayoutParams(b1layout);
		missionButton.setOnTouchListener(this);
		
						
		hangerButton = new ImageButton(this);
		hangerButton.setBackgroundResource(R.drawable.hangar);
		hangerButton.setId(11);
		RelativeLayout.LayoutParams b2layout = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		b2layout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		b2layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		b2layout.leftMargin=marginX;
		b2layout.bottomMargin=marginY;
		hangerButton.setLayoutParams(b2layout);
		hangerButton.setOnTouchListener(this);
		
		
		screenLayout = (RelativeLayout)findViewById(R.id.screenLayout);
		screenLayout.addView(missionButton);
		screenLayout.addView(hangerButton);
		
				
		//retrieve shared preferences for music state
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    silent = settings.getBoolean("silentMode", false);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		 if (!silent){   
			mp = MediaPlayer.create(getApplicationContext(), R.raw.arpanauts);
			mp.setVolume(volume,volume);
			mp.setLooping(true);
			mp.start();
			musicToggle.setChecked(true);
		 }else{
		    musicToggle.setChecked(false);
		 }
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.soundToggle){
			if(!musicToggle.isChecked()){
				mp.stop();
				mp.release();
				mp = null;
				silent = true;
				
			}else{
				mp = MediaPlayer.create(getApplicationContext(), R.raw.arpanauts);
				mp.setVolume(volume,volume);
				mp.setLooping(true);
				mp.start();
				silent = false;
			}
			 // We need an Editor object to make preference changes.
		    // All objects are from android.context.Context
		    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean("silentMode", silent);
		    // Commit the edits!
		    editor.commit();
		}
		
		
		
	}
	protected void onStop(){
		if (mp!=null){
				mp.stop();
				mp.release();
				mp = null;
		 }
	    super.onStop();
	   
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == 10){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
			Intent i = new Intent(this, Mission.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			missionButton.setBackgroundResource(R.drawable.mission_pressed);
			if (!silent){
				spool.play(buttonsfx, volume, volume, 1, 0, 1);
			}

		}
			else if (event.getAction() == MotionEvent.ACTION_UP){
				missionButton.setBackgroundResource(R.drawable.mission);	
				}
		}
		if (v.getId() == 11){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				if (!silent){
					spool.play(buttonsfx, volume, volume, 1, 0, 1);
				}
			
			Intent i = new Intent(this, Hanger.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			i.putExtra("FROM","menu");
			startActivity(i);
			hangerButton.setBackgroundResource(R.drawable.hangar_pressed);
		}
			else if (event.getAction() == MotionEvent.ACTION_UP){
				hangerButton.setBackgroundResource(R.drawable.hangar);	
				}
		}
		return false;
	}
}