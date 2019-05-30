package com.example.bombview.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.bombview.factory.FallingParticleFactory;

import java.util.Random;

public class FallingParticle extends Particle {

    private Rect rect;
    private float alphas = 1f;
    private float radius = FallingParticleFactory.PART_WH;
    private Random mRandom = new Random();

    public FallingParticle(float cx, float cy, int color, Rect rect) {
        super(cx, cy, color);
        this.rect = rect;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color)*alphas));
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    protected void calculate(float factor) {
        cx+= factor*(mRandom.nextInt(rect.width()))*(mRandom.nextFloat()-0.5);
        cy+= factor*(mRandom.nextInt(rect.height()))*(mRandom.nextFloat()-0.5);
        radius = factor*mRandom.nextInt(2);
        alphas = (1-factor)*(mRandom.nextFloat()+1);
    }
}
