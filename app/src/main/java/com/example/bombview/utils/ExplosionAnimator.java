package com.example.bombview.utils;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.bombview.entity.Particle;
import com.example.bombview.factory.ParticleFactory;

public class ExplosionAnimator extends ValueAnimator {

    private static final int DEFAULT_DURING = 1500;
    private Particle[][] particles;
    private ParticleFactory particleFactory;
    private View container;
    private Paint pain;

    public ExplosionAnimator(ParticleFactory particleFactory, View container, Bitmap bitmap, Rect rect) {
        this.particleFactory = particleFactory;
        this.container = container;
        pain = new Paint();
        setFloatValues(0f, 1f);
        setDuration(DEFAULT_DURING);
        particles = particleFactory.generateParcicle(bitmap, rect);
    }

    public void draw(Canvas canvas){
        if(!isStarted()){
            //动画停止结束绘制爆炸效果
            return;
        }
        for(Particle particleItems[] : particles){
            for(Particle particle : particleItems){
                particle.advance(canvas, pain, (Float) getAnimatedValue());
            }
        }
        container.invalidate();
    }

    @Override
    public void start() {
        super.start();
        container.invalidate();
    }
}
