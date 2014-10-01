package com.example.lal;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
		
		//initAd(); // 暂时关闭插屏广告; 
		initAdBar();
	}

	@Override
	protected void onDestroy() {
		releaseAd();
		super.onDestroy();
	}
	
	private void initAdBar(){
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		adView.setAdListener(new AdViewListener() {

		    @Override
		    public void onSwitchedAd(AdView adView) {
		        // 切换广告并展示
		    	Log.e("tz", "切换广告");
		    }

		    @Override
		    public void onReceivedAd(AdView adView) {
		        // 请求广告成功
		    	Log.e("tz", "请求广告成功");
		    }

		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // 请求广告失败
		    	Log.e("tz", "请求广告失败");
		    }
		});

	}
	
	private void initAd(){
		
		if(SpotManager.getInstance(this).checkLoadComplete()){
			
			Log.e("tz", "-------广告success");
		}else{
			Log.e("tz", "-------广告failed");
		}
		
		SpotManager.getInstance(this).setSpotOrientation(
	            SpotManager.ORIENTATION_PORTRAIT);
		SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
		    @Override
		    public void onShowSuccess() {
		        Log.i("tz", "onShowSuccess");
		    }

		    @Override
		    public void onShowFailed() {
		        Log.i("tz", "onShowFailed");
		    }

		    @Override
		    public void onSpotClosed() {
		        Log.e("sdkDemo", "closed");
		    }
		});
	}

	private void releaseAd(){
		SpotManager.getInstance(this).unregisterSceenReceiver();
	}
	
	@Override
	public void onBackPressed() {
	    // 如果有需要，可以点击后退关闭插屏广告（可选）。
	    if (!SpotManager.getInstance(this).disMiss(true)) {
	        super.onBackPressed();
	    }
	}

	@Override
	protected void onStop() {
	    //如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
	    SpotManager.getInstance(this).disMiss(false);

	    super.onStop();
	}
}
