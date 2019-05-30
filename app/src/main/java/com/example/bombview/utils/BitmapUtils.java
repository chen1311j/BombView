package com.example.bombview.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class BitmapUtils {

    public static Canvas CANVAS = new Canvas();

    public static Bitmap createBitmapFromView(View view){
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444, 1);
        if(bitmap != null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retry) {
        try {
            return Bitmap.createBitmap(width, height, config);
        }catch (OutOfMemoryError e){
            if(retry > 0){
                System.gc();
                return createBitmapSafely(width, height, config, retry-1);
            }
        }
        return null;
    }
}
