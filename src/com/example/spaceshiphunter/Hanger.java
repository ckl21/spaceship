package com.example.spaceshiphunter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Hanger extends Activity implements OnClickListener {
	
	ImageButton btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hanger);
		
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.hangerLayout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );

				btn = new ImageButton(this);
				String prevAct = getIntent().getExtras().getString("FROM");
				if (prevAct.equals("mission")){
		    	  //replace with launch button
		      btn.setBackgroundResource(R.drawable.launch);
		      btn.setId(1);
		      }else{
		    	  //replace with choose mission button
		    	  btn.setBackgroundResource(R.drawable.mission_select);
			      btn.setId(2);
		      }
		         

		      layout.addView(btn, lp); 
		      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btn.getLayoutParams();
		      params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		      params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		      
		      btn.setLayoutParams(params); //causes layout update
		      btn.setOnClickListener(this);
		    
		         
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hanger, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == 1){
			Intent i = new Intent(this, Game.class);
			
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			if (MainActivity.silent == false){
				MainActivity.spool.play(MainActivity.buttonsfx, MainActivity.volume, MainActivity.volume, 0,0,1);
			}
			
			btn.setBackgroundResource(R.drawable.launch_pressed);
			finish();
		}else if(v.getId() == 2){
			if (MainActivity.silent == false){
				MainActivity.spool.play(MainActivity.buttonsfx, MainActivity.volume, MainActivity.volume, 0,0,1);
			}
			Intent i = new Intent(this, Mission.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			btn.setBackgroundResource(R.drawable.mission_selected_pressed);
			finish();
		}
		
	}

}
