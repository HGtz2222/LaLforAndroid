package com.example.lal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MessageHandler extends Handler{
	DoLaL mDoLaL; 
	public MessageHandler(DoLaL doLaL, Looper looper) {
		super(looper);
		mDoLaL = doLaL;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.e("tz", "MessageHandler----" + msg.what);
		mDoLaL.refreshTime(msg.what/1000);
	}

}
