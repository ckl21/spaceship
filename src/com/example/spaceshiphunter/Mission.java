package com.example.spaceshiphunter;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ImageView;

public class Mission extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission);
		
		ImageView imgView=(ImageView) findViewById(R.id.space);
		Drawable  drawable  = getResources().getDrawable(R.drawable.mission_bg);
		imgView.setImageDrawable(drawable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission, menu);
		return true;
	}

}
