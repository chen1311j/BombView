package com.example.bombview.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.bombview.entity.FallingParticle;
import com.example.bombview.entity.Particle;

public class FallingParticleFactory extends ParticleFactory {
    @Override
    public Particle[][] generateParcicle(Bitmap bitmap, Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int wCount = w/PART_WH;
        int hCount = h/PART_WH;

        int partWCount = wCount > 0 ? wCount:1;
        int partHCount = hCount > 0 ? hCount:1;
        int rawCount = bitmap.getWidth()/partWCount;
        int columnCount = bitmap.getHeight()/partHCount;

        Particle[][] particles = new Particle[partHCount][partWCount];
        for(int raw = 0; raw < partHCount; raw++){
            for(int column = 0; column < partWCount; column++){
                int color = bitmap.getPixel(column*rawCount, raw*columnCount);
                int x = rect.left+raw*PART_WH;
                int y = rect.top+column*PART_WH;
                particles[raw][column] = new FallingParticle(x, y, color, rect);
            }
        }
        return particles;
    }
}
