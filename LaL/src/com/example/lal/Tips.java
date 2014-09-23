package com.example.lal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Tips extends Activity{
	private Button start;
	private int time = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip);
		
		Intent intent = getIntent();
		time  = intent.getIntExtra("time", 5);
		Log.e("tz", "运行时间: " + time);
		
		start = (Button)findViewById(R.id.btn_start);
		start.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Tips.this, DoLaL.class);	
				startActivity(intent);
				finish();
			}
			
		});
	}

}
