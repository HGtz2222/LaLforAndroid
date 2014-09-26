package com.example.lal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class DoLaL extends Activity{
	
	private TextView screen;
	private TextView screenTime;
	private SensorEventListener listener;
	private int lalCount = 0;
	private SensorManager sm;
	private Sensor s;
	private Thread timerThread;
	private Boolean timerThreadPauseFlag = false;
	private int time = 0;
	private MessageHandler messageHandler;
	
	private void initUI(){
		screen = (TextView)findViewById(R.id.screen);
		screenTime = (TextView)findViewById(R.id.timeScreen);
	}
	
	private void initSensor(){
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	private void initMessageHandler(){
		Looper looper = Looper.myLooper();
		messageHandler = new MessageHandler(this, looper);
	}
	
	private void initTimerThread() {
		Intent intent = getIntent();
		time = intent.getIntExtra("time", 5000);
		Log.e("tz", "DoLaL time: " + time);
		
		timerThread = new Thread(){

			@Override
			public void run() {
				super.run();
				try {
					while(time > 0){
						if (timerThreadPauseFlag){
							Thread.sleep(1000);
							continue;
						}
						// 通过发送消息, 设置剩余时间;
						Message message = Message.obtain();
						message.what = time;
						messageHandler.sendMessage(message);
						Thread.sleep(1000);
						time -= 1000;
					}
					Log.e("tz", "Finish--------------");
					calcResult();
					Ring.ring(DoLaL.this); // 震动提示结束; 
				} catch (InterruptedException except) {
					Log.e("tz", except.getMessage());
				}
			}
			
		};
		timerThread.start();
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
					//Log.e("tz", "totalAcce: " + total);
					if (total > 150.0f){
						lalCount ++;
						screen.setText("" + lalCount);
					}
				}
			}
			
		};
	}
	
	private void calcResult(){
		// 1. 将结果保存, 发送给结果resultActivity;
		Intent intent = new Intent(DoLaL.this, ResultActivity.class);
		intent.putExtra("result", lalCount);
		startActivity(intent);
		// 2. 终结当前的Activity;
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_lal);
		// 以下顺序不能变化!
		initUI();
		initSensor();
		initListener();
		initMessageHandler();
		initTimerThread();
		
		RegisterSensor();	// 别忘记注册Listener!
	}

	@Override
	protected void onPause() {
		super.onPause();
		UnRegisterSensor();
		timerThreadPauseFlag = true;
		Log.e("tz", "DoLaL onDestroy");
	}

	@Override
	protected void onResume() {
		super.onResume();
		RegisterSensor();
		timerThreadPauseFlag = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("tz", "DoLaL onDestroy");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e("tz", "DoLaL onStop");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("tz", "onKeyDown");
		return true;
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return true;
	}

	public void refreshTime(int time){
		Resources resource = getResources();
		String timeStr = resource.getString(R.string.time_str);
		screenTime.setText(timeStr + time + "s");
	}
}
