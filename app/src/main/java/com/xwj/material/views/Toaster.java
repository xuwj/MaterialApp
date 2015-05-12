package com.xwj.material.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义Toast
 */
public class Toaster {
	private static Toast sToast;
	private static int sMessageTextId;
	
	public static void init(Context context, int layoutId, int textId) {
		sToast = new Toast(context);
		sToast.setView(LinearLayout.inflate(context, layoutId, null));
		sToast.setDuration(Toast.LENGTH_SHORT);
		sMessageTextId = textId;
	}
	
	public static void destory() {
		if (sToast != null) {
			sToast.cancel();
		}
	}
	
	public static void toast(int titleResId) {
		if (sToast != null) {
			((TextView) sToast.getView().findViewById(sMessageTextId))
					.setText(titleResId);
			sToast.show();
		}
	}

	public static void toast(String title) {
		if (sToast != null) {
			((TextView) sToast.getView().findViewById(sMessageTextId))
					.setText(title);
			sToast.show();
		}
	}
}
