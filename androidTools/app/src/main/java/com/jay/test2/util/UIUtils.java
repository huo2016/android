package com.jay.test2.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.jay.test2.MyApplication;


/**
 * 和ui相关的工具类
 * 
 * @author Administrator
 */
public class UIUtils {

	/**
	 * dip-->px
	 */
	public static int dip2Px(Context context,int dip) {
		// px/dip = density;
		// density = dpi/160
		// 320*480 density = 1 1px = 1dp
		// 1280*720 density = 2 2px = 1dp

		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dip * density + 0.5f);
		return px;
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context,float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
