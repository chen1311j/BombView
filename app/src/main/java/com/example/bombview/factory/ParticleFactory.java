package com.example.bombview.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.bombview.entity.Particle;

public abstract class ParticleFactory {
    public static final int PART_WH = 8;//爆炸颗粒的大小

    public abstract Particle[][] generateParcicle(Bitmap bitmap, Rect rect);
}
