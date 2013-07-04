package com.example.spaceshiphunter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Mission extends Activity implements OnTouchListener{

	int nextActivity;
	public static final String PREFS_NAME = "MyPrefsFile";
	boolean silent;
	MediaPlayer mp;
	float volume = 0.2f;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission);
	
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    silent = settings.getBoolean("silentMode", false);
	    imageView = (ImageView) findViewById (R.id.image);
	    imageView.setOnTouchListener(this);
	    
	   
		
	}

	@Override
	protected void onResume() {
		 if (!silent){   
			 mp = MediaPlayer.create(getApplicationContext(), R.raw.hhavok);
				mp.setVolume(volume,volume);
				mp.setLooping(true);
				mp.start();
		 }
		 nextActivity = 0;
		 super.onResume();
	}
	
	@Override
	protected void onPause() {
		imageView.setImageResource (R.drawable.mission_screen);
		super.onPause();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission, menu);
		return true;
	}

	@Override
	public boolean onTouch (View v, MotionEvent ev) 
	{

	    final int action = ev.getAction();

	    final int evX = (int) ev.getX();
	    final int evY = (int) ev.getY();
	    

	    // If we cannot find the imageView, return.
	    

	    int touchColor = getHotspotColor (R.id.image_areas, evX, evY);
	    ColorTool ct = new ColorTool ();
	    int tolerance = 25;
	    // Now that we know the current resource being displayed we can handle the DOWN and UP events.

	    switch (action) {
	    case MotionEvent.ACTION_DOWN :
	    	 
		       
		       if (ct.closeMatch (Color.BLUE, touchColor, tolerance)) {
		    	   imageView.setImageResource (R.drawable.mission_screen_blue);
		    	   nextActivity = 1;
		       }
		       else if (ct.closeMatch (Color.RED, touchColor, tolerance)) {
		    	   imageView.setImageResource (R.drawable.mission_screen_red);
		    	   nextActivity = 2;
		       }
		        
		       break;
	    case MotionEvent.ACTION_UP :      
	       if (nextActivity == 1) {
	    	   finish();
	    	   overridePendingTransition(0, 0);
	       } 
	       else if (nextActivity == 2) {
	    	   Intent i = new Intent(this, Hanger.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				i.putExtra("FROM","mission");
				startActivity(i);
	       } 
	       break;
	    } // end switch
	    return true;
	}   

	

	public int getHotspotColor (int hotspotId, int x, int y) {
		
	    ImageView img = (ImageView) findViewById (hotspotId);
	    img.setDrawingCacheEnabled(true); 
	    Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache()); 
	   
	    img.setDrawingCacheEnabled(false);
	    return hotspots.getPixel(x, y);
	      
	    
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
