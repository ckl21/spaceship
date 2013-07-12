package com.example.spaceshiphunter;



import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class Game extends Activity implements SensorEventListener {
    /** Called when the activity is first created. */
	
	private static final String TAG = Game.class.getSimpleName();
	private SensorManager mySensorManager = null;
	private Sensor myAccelerometer = null;
	float[] accel_vals = new float[3];
	private float maxAccel = 8;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this));
        Log.d(TAG, "View added");
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		finish();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	
	@Override
	protected void onPause() {
		mySensorManager.unregisterListener(this);
	Log.d(TAG, "Pausing…");
	MainThread.running=false;//this is the value for stop the loop in the run()
	super.onPause();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int type = event.sensor.getType();
		if (type == Sensor.TYPE_ACCELEROMETER){
			accel_vals = event.values;
			float accelX = accel_vals[1];
			float accelY = accel_vals[0];
			if(accelX > maxAccel){
				accelX = maxAccel;
			}
			if(accelY > maxAccel){
				accelY = maxAccel;
			}
			MainThread.getGamePanel().setNewXY(accelX,accelY,accel_vals[1],accel_vals[0]);
		}
		
	}
    
    
}