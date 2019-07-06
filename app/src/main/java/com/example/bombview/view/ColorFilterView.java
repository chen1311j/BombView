package com.example.bombview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.bombview.R;

public class ColorFilterView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private int saturation = 0;//默认原图

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ColorFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Bitmap resource = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        int height = resource.getHeight();
        int width = resource.getWidth();
        float scale = (float) 800/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mBitmap = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //方法1：设置色彩饱和度为0
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(saturation);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);


//        //方法2：设置色彩矩阵，RGB分量相等且通道值之和为1
//        float[] matrix = {
//            0.3f, 0.4f, 0.3f, 0, 0f,
//            0.3f, 0.4f, 0.3f, 0, 0f,
//            0.3f, 0.4f, 0.3f, 0, 0f,
//            0, 0, 0, 1, 0,
//        };
//        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//        mPaint.setColorFilter(filter);
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    public void transFormBitmap(int saturation){
        this.saturation = saturation;
        postInvalidate();
    }
}
