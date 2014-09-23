package com.example.lal;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DoLaL extends Activity{
	private TextView screen;
	private SensorEventListener listener;
	private int count = 0;
	private SensorManager sm;
	private Sensor s;
	
	// TODO 屏蔽按键, 防止跳出界面; 
	
	private void initUI(){
		screen = (TextView)findViewById(R.id.screen);
	}
	
	private void initSensor(){
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	private void RegisterSensor(){
		sm.registerListener(listener, s, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private void UnRegisterSensor(){
		sm.unregisterListener(listener);
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
					//screen.setText("value " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
					float total = event.values[0] * event.values[0] + event.values[1] * event.values[1] + (event.values[2] - 9.8f) * (event.values[2] - 9.8f); 
					Log.e("tz", "totalAcce: " + total);
					if (total > 200.0f){
						count ++;
						screen.setText("" + (count / 2));
					}
				}
			}
			
		};
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_lal);
		// 以下顺序不能变化!
		initUI();
		initSensor();
		initListener();
		
		RegisterSensor();	// 别忘记注册Listener!
	}

	@Override
	protected void onPause() {
		super.onPause();
		UnRegisterSensor();
	}

	@Override
	protected void onResume() {
		super.onResume();
		RegisterSensor();
	}
}
