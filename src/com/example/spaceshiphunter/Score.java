package com.example.spaceshiphunter;


import java.util.concurrent.TimeUnit;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("NewApi")
public class Score extends Activity implements OnTouchListener{

	TextView status;
	TextView shotsFiredText;
	TextView accuracyText;
	TextView healthRemainingLabel;
	TextView healthRemainingText;
	TextView timeElapsedText;
	TextView scoreText;
	double healthPercentage;
	double accuracy;
	int finalScore;
	String letterGrade;
	TextView shotsFiredLabel;
	TextView accuracyLabel;
	TextView timeElapsedLabel;
	TextView gameScoreLabel;
	Button nextb;
	
	

	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_score);
		super.onCreate(savedInstanceState);
		
		nextb = new Button(this);
		nextb.setId(27);
		nextb.setBackgroundResource(R.drawable.next);
		
		
		
		RelativeLayout.LayoutParams nextButton = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		nextButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		nextButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		nextButton.rightMargin = 0;
		nextButton.bottomMargin = 0;
		
		nextb.setLayoutParams(nextButton);
		nextb.setOnTouchListener(this);
		
		
		RelativeLayout mLayout = (RelativeLayout)findViewById(R.id.scoreLayout);
		mLayout.addView(nextb);
		
		
		
	
		
		Typeface PostPixel = Typeface.createFromAsset(this.getAssets(),"fonts/postpixel.ttf");
		
		String winner = getIntent().getExtras().getString("Winner");
		int shotsFired = getIntent().getExtras().getInt("ShotsFired");
		int shotsHit = getIntent().getExtras().getInt("ShotsHit");
		int healthRemaining = getIntent().getExtras().getInt("HealthRemaining");
		int maxHealth = getIntent().getExtras().getInt("MaxHealth");
		long timeElapsed = getIntent().getExtras().getLong("TimeElapsed");
		
		status = (TextView)findViewById(R.id.statusText);
		status.setTypeface(PostPixel);
		
		shotsFiredText = (TextView)findViewById(R.id.shotsFiredText);
		
			
		
		shotsFiredLabel = (TextView)findViewById(R.id.shotsFiredLabel);
		shotsFiredLabel.setTypeface(PostPixel);
		shotsFiredLabel.setTextSize(14f);
		
		
		accuracyLabel = (TextView)findViewById(R.id.accuracyLabel);
		accuracyLabel.setTextSize(14f);
		accuracyLabel.setTypeface(PostPixel);
		accuracyText = (TextView)findViewById(R.id.accuracyText);
		
		timeElapsedLabel = (TextView) findViewById(R.id.TimeElapsedLabel);
		timeElapsedLabel.setTypeface(PostPixel);
		timeElapsedLabel.setTextSize(14f);
		
		gameScoreLabel = (TextView)findViewById(R.id.scoreLabel);
		gameScoreLabel.setTypeface(PostPixel);
		gameScoreLabel.setTextSize(14f);
		
		healthRemainingLabel = (TextView)findViewById(R.id.healthRemainingLabel);
		healthRemainingLabel.setTypeface(PostPixel);
		healthRemainingLabel.setTextSize(14f);
		
		
		healthRemainingText = (TextView)findViewById(R.id.healthRemainingText);
		timeElapsedText = (TextView)findViewById(R.id.timeElapsedText);
		scoreText = (TextView)findViewById(R.id.scoreText);
		healthPercentage = ((double)healthRemaining/maxHealth)*100;
		accuracy = ((double)shotsHit/shotsFired)*100;
		
		
	
		if (winner.equals("player")){
			status.setText("Victory!");
			healthRemainingLabel.setText("Health Remaining (Player)");
			
			
		}else{
			status.setText("Defeat!");
			
			healthRemainingLabel.setText("Health Remaining (Enemy)");
		}
		
		status.setTextSize(30f);
		
		shotsFiredText.setText(String.format("%d",shotsFired));
		shotsFiredText.setTypeface(PostPixel);
		shotsFiredText.setTextSize(18f);
	
		accuracyText.setText(String.format("%d", (int)accuracy) + " %");
		accuracyText.setTypeface(PostPixel);
		accuracyText.setTextSize(18f);
		
		healthRemainingText.setText(String.format("%d", (int)healthPercentage) + " %");
		healthRemainingText.setTypeface(PostPixel);
		healthRemainingText.setTextSize(18f);
		
		finalScore = (int) accuracy + (int) healthPercentage - (int)((timeElapsed/1000)/2);
		Log.d("Game", "score = " + finalScore);
		
		if(finalScore <= -200){
			letterGrade = "C";
		}else if (finalScore <= -30){
			letterGrade = "C+";
		}else if (finalScore <= -10){
			letterGrade = "B-";
		}else if (finalScore <= 10){
			letterGrade = "B";
		}else if (finalScore <= 30){
			letterGrade = "B+";
		}else if (finalScore <= 50){
			letterGrade = "A-";
		}else if (finalScore <= 70){
			letterGrade = "A";
		}else if (finalScore <= 90){
			letterGrade = "A+";
		}else{
			letterGrade = "S";
		}
	
		timeElapsedText.setText(String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
			    TimeUnit.MILLISECONDS.toSeconds(timeElapsed) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)))
			    
			);
			timeElapsedText.setTypeface(PostPixel);
			timeElapsedText.setTextSize(18f);
		if(winner.equals("player")){
				scoreText.setText(letterGrade);
				scoreText.setTypeface(PostPixel);
				
		}else{
			scoreText.setText("F");
			scoreText.setTypeface(PostPixel);
		}
		scoreText.setTextSize(30f);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == 27){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				nextb.setBackgroundResource(R.drawable.next_pressed);
				finish();
			}
			
			if (event.getAction() == MotionEvent.ACTION_UP){
				nextb.setBackgroundResource(R.drawable.next);
			}
		}
		return false;
	}

}
