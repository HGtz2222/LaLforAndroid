package com.example.lal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Tips extends Activity{
	Button btn_start;
	private int time = 0;
	
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
		
		btn_start = (Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				// 震动提示开使
				Ring.ring(Tips.this);
				startGame();
			}
			
		});
	}
}
