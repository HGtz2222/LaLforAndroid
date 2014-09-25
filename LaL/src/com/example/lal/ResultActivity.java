package com.example.lal;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity{

	private TextView scoreScreen;
	private Button btn_return;
	private Button btn_share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("tz", "ResultActivity start!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		scoreScreen = (TextView)findViewById(R.id.scoreScreen);
		
		Intent intent = getIntent();
		int count = intent.getIntExtra("result", 0);
		Resources rs = getResources();
		scoreScreen.setText(rs.getString(R.string.score) + count);
		
		btn_return = (Button)findViewById(R.id.btn_return);
		btn_share = (Button)findViewById(R.id.btn_share);
		
		btn_return.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				finish();
				Log.e("tz", "Return main menu");
			}
			
		});
		
		btn_share.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO 分享到微信
				Log.e("tz", "Share to WeiXin");
				Toast toast = Toast.makeText(ResultActivity.this, "玩命研发中, 敬请期待!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
		});
	}

}
