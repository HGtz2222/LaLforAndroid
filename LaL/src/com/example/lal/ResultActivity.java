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
				// TODO ����΢��
				Log.e("tz", "Share to WeiXin");
				Toast toast = Toast.makeText(ResultActivity.this, "�����з���, �����ڴ�!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
		});
		
		//initAd(); // ��ʱ�رղ������; 
		initAdBar();
	}

	@Override
	protected void onDestroy() {
		releaseAd();
		super.onDestroy();
	}
	
	private void initAdBar(){
		// ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// ��ȡҪǶ�������Ĳ���
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		// ����������뵽������
		adLayout.addView(adView);
		
		adView.setAdListener(new AdViewListener() {

		    @Override
		    public void onSwitchedAd(AdView adView) {
		        // �л���沢չʾ
		    	Log.e("tz", "�л����");
		    }

		    @Override
		    public void onReceivedAd(AdView adView) {
		        // ������ɹ�
		    	Log.e("tz", "������ɹ�");
		    }

		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // ������ʧ��
		    	Log.e("tz", "������ʧ��");
		    }
		});

	}
	
	private void initAd(){
		
		if(SpotManager.getInstance(this).checkLoadComplete()){
			
			Log.e("tz", "-------���success");
		}else{
			Log.e("tz", "-------���failed");
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
	    // �������Ҫ�����Ե�����˹رղ�����棨��ѡ����
	    if (!SpotManager.getInstance(this).disMiss(true)) {
	        super.onBackPressed();
	    }
	}

	@Override
	protected void onStop() {
	    //��������ô˷�������home����ʱ������ͼ���޷���ʾ�������
	    SpotManager.getInstance(this).disMiss(false);

	    super.onStop();
	}
}
