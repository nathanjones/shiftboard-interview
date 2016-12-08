package com.nathanrjones.shiftboard.util;

import android.content.res.Resources;

public class ViewUtils {

    public static final int MARGIN_SMALL = 8;
    public static final int MARGIN_NORMAL = 16;
    public static final int MARGIN_LARGE = 32;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
