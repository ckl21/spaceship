package com.example.spaceshiphunter;


import java.util.concurrent.TimeUnit;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Score extends Activity {

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
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Typeface PostPixel = Typeface.createFromAsset(this.getAssets(),"fonts/postpixel.ttf");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
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
		accuracyText = (TextView)findViewById(R.id.accuracyText);
		accuracyLabel = (TextView)findViewById(R.id.accuracyLabel);
		timeElapsedLabel = (TextView) findViewById(R.id.TimeElapsedLabel);
		gameScoreLabel = (TextView)findViewById(R.id.scoreLabel);
		healthRemainingLabel = (TextView)findViewById(R.id.healthRemainingLabel);	
		healthRemainingText = (TextView)findViewById(R.id.healthRemainingText);
		timeElapsedText = (TextView)findViewById(R.id.timeElapsedText);
		scoreText = (TextView)findViewById(R.id.scoreText);
		healthPercentage = ((double)healthRemaining/maxHealth)*100;
		accuracy = ((double)shotsHit/shotsFired)*100;
		
		accuracyLabel.setTypeface(PostPixel);
		timeElapsedLabel.setTypeface(PostPixel);
		gameScoreLabel.setTypeface(PostPixel);
		
	
		if (winner.equals("player")){
			status.setText("Victory!");
			healthRemainingLabel.setText("Health Remaining (Player)");
			
		}else{
			status.setText("Defeat!");
			healthRemainingLabel.setText("Health Remaining (Enemy)");
		}
		healthRemainingLabel.setTypeface(PostPixel);
		
		
		shotsFiredText.setText(String.format("%d",shotsFired));
		shotsFiredText.setTypeface(PostPixel);
	
		accuracyText.setText(String.format("%d", (int)accuracy) + " %");
		accuracyText.setTypeface(PostPixel);
		
		healthRemainingText.setText(String.format("%d", (int)healthPercentage) + " %");
		healthRemainingText.setTypeface(PostPixel);
		
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
		if(winner.equals("player")){
				scoreText.setText(letterGrade);
				scoreText.setTypeface(PostPixel);
		}else{
			scoreText.setText("F");
			scoreText.setTypeface(PostPixel);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}

}
