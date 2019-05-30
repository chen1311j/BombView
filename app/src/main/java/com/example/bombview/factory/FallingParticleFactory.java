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

        int rawCount = bitmap.getWidth()/(wCount > 0 ? wCount:1);
        int columnCount = bitmap.getHeight()/(hCount > 0 ? hCount:1);

        Particle[][] particles = new Particle[rawCount][columnCount];
        for(int raw = 0; raw < rawCount; raw++){
            for(int column = 0; column < columnCount; column++){
                int color = bitmap.getPixel(rawCount*rawCount, column*columnCount);
                int x = rect.left+raw*PART_WH;
                int y = rect.top+column*PART_WH;
                particles[raw][column] = new FallingParticle(x, y, color, rect);
            }
        }
        return particles;
    }
}
