package com.example.lal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btn_lvl1;
	private Button btn_lvl2;
	private Button btn_lvl3;
	
	private void initCtrl(){
		btn_lvl1 = (Button)findViewById(R.id.btn_lvl1);
		btn_lvl2 = (Button)findViewById(R.id.btn_lvl2);
		btn_lvl3 = (Button)findViewById(R.id.btn_lvl3);
	}
	
	private void initListener(){
		Button.OnClickListener listener = new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				Button btn = (Button)view;
				Intent intent = new Intent(MainActivity.this, DoLaL.class);
				if (btn.getId() == R.id.btn_lvl1){
					intent.putExtra("time", 5);
				}
				// TODO 挂接其他按钮
				startActivity(intent);
			}
			
		};
		btn_lvl1.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initCtrl();
		initListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
