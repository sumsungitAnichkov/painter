package com.vlad.hackaton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;

import com.vlad.hackaton.R;
import com.vlad.hackaton.myPaint;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SurfView surfView = findViewById(R.id.surfView);

        View.OnClickListener buttonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.spline:
                        surfView.setMode(SurfView.DrawingMode.Spline);
                        break;
                    case R.id.line:
                        surfView.setMode(SurfView.DrawingMode.Line);
                        break;
                    case R.id.rectangle:
                        surfView.setMode(SurfView.DrawingMode.Rect);
                        break;
                    case R.id.circle:
                        surfView.setMode(SurfView.DrawingMode.Circle);
                        break;
                    case R.id.redo:
                        surfView.redo();
                        ;
                        break;
                }
            }
        };

        findViewById(R.id.spline).setOnClickListener(buttonsListener);
        findViewById(R.id.line).setOnClickListener(buttonsListener);
        findViewById(R.id.rectangle).setOnClickListener(buttonsListener);
        findViewById(R.id.circle).setOnClickListener(buttonsListener);
        findViewById(R.id.redo).setOnClickListener(buttonsListener);

        final int[] rgb = new int[3];

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.R:
                        rgb[0] = progress;
                        break;
                    case R.id.G:
                        rgb[1] = progress;
                        break;
                    case R.id.B:
                        rgb[2] = progress;
                        break;
                }

                surfView.setColor(rgb[0], rgb[1], rgb[2]);
                surfView.update();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        ((SeekBar) findViewById(R.id.R)).setMax(255);
        ((SeekBar) findViewById(R.id.B)).setMax(255);
        ((SeekBar) findViewById(R.id.G)).setMax(255);

        ((SeekBar) findViewById(R.id.R)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.G)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.B)).setOnSeekBarChangeListener(listener);

    }
}