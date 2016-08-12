/**
 *
 */
package com.snnu.yefan.utils;

import android.content.Context;


/**
 * @author wcy
 */
public class Utils {

//    public static String formatLogoUrl(String logo) {
//        return String.format(Constants.URL_LOGO, logo);
//    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


}
