package com.example.bombview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bombview.factory.FallingParticleFactory;
import com.example.bombview.view.ExplosionField;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.addListener(findViewById(R.id.iv_icon));
    }
}
