package com.example.spaceshiphunter;


import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		String winner = getIntent().getExtras().getString("Winner");
		int shotsFired = getIntent().getExtras().getInt("ShotsFired");
		int shotsHit = getIntent().getExtras().getInt("ShotsHit");
		int healthRemaining = getIntent().getExtras().getInt("HealthRemaining");
		int maxHealth = getIntent().getExtras().getInt("MaxHealth");
		long timeElapsed = getIntent().getExtras().getLong("TimeElapsed");
		
		status = (TextView)findViewById(R.id.statusText);
		shotsFiredText = (TextView)findViewById(R.id.shotsFiredText);
		accuracyText = (TextView)findViewById(R.id.accuracyText);
		healthRemainingLabel = (TextView)findViewById(R.id.healthRemainingLabel);
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
		
		shotsFiredText.setText(String.format("%d",shotsFired));
		accuracyText.setText(String.format("%d", (int)accuracy) + " %");
		healthRemainingText.setText(String.format("%d", (int)healthPercentage) + " %");
		
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
		if(winner.equals("player")){
				scoreText.setText(letterGrade);
		}else{
			scoreText.setText("F");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}

}
