package com.github.ppartisan.popularmoviesi.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public final class MetricUtils {

    private MetricUtils() { throw new AssertionError(); }

    private static int getScreenWidthInPx(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int getColumnCountForScreenWidth(Context ctx) {
        final int columnWidth = Integer.parseInt(FetchJsonMovieDataUtils.IMAGE_WIDTH.substring(1));
        return (int)Math.ceil(getScreenWidthInPx(ctx) / columnWidth);
    }

}
