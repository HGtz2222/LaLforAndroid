package com.example.lal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends Activity{

	private TextView resultScreen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("tz", "ResultActivity start!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		resultScreen = (TextView)findViewById(R.id.resultScreen);
		
		Intent intent = getIntent();
		int count = intent.getIntExtra("result", 0);
		resultScreen.setText("" + count);
	}

}
