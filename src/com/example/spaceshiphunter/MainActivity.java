package com.example.spaceshiphunter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {
	//file for saved preferences
	public static final String PREFS_NAME = "MyPrefsFile";
	MediaPlayer mp;
	ToggleButton musicToggle;
	Button missionButton;
	boolean silent;
	float volume = 0.3f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		musicToggle = (ToggleButton) findViewById(R.id.soundToggle);
		musicToggle.setOnClickListener(this);
		
		missionButton = (Button) findViewById(R.id.missionButton);
		missionButton.setOnClickListener(this);
				
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
		
		if (v.getId() == R.id.missionButton){
			Intent i = new Intent(this, Mission.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			
		}
		if (v.getId() == R.id.hangerButton){
			Intent i = new Intent(this, Hanger.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			i.putExtra("FROM","menu");
			startActivity(i);
			
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
}