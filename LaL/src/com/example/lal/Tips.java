package com.example.lal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Tips extends Activity{
	
	private int time = 0;
	
	private SensorEventListener listener;
	private SensorManager sm;
	private Sensor s;
	
	private void initSensor(){
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	private void initListener() {
		listener = new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor s, int arg1) {
				// Empty Function!	
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
					//Log.e("tz", "value " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
					float total = event.values[0] * event.values[0] + event.values[1] * event.values[1] + (event.values[2] - 9.8f) * (event.values[2] - 9.8f); 
					//Log.e("tz", "totalAcce: " + total);
					if (total > 200.0f){
						startGame();
					}
				}
			}
			
		};
	}
	
	private void startGame(){
		Intent intent = new Intent(Tips.this, DoLaL.class);	
		intent.putExtra("time", time);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip);
		
		Intent intent = getIntent();
		time  = intent.getIntExtra("time", 5000);
		Log.e("tz", "运行时间: " + time);
		
		initSensor();
		initListener();
		sm.registerListener(listener, s, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(listener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(listener, s, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
}
