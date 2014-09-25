package com.example.lal;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class Ring {
	public static void ring(final Activity activity){
		Vibrator vib = (Vibrator)activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(100);
	}
}
