package com.games.akash.circlething;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    Circle ring;
    Ball ball;
    RelativeLayout mainLayout;
    Button btn;
    TextView txt;
    TextView counter;
    int sweep = 90;
    int speed = 5;
    Timer timer;
    TimerTask setTxt;
    int counterNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ring = new Circle(this);

        mainLayout = (RelativeLayout)findViewById(R.id.activity_main);
        btn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.textView);
        counter = (TextView) findViewById(R.id.counter);
        timer = new Timer();
        counterNum = 3;
        setTxt = new TimerTask() {
            @Override
            public void run() {
                counterNum--;
                    if(counterNum == 0) {
                        timer.cancel();
                        ball.start();
                        ring.start();
                        counter.setVisibility(View.INVISIBLE);
                    } else {
                        counter.setText(String.valueOf(counterNum));
                    }
                }
        };
        counter.setVisibility(View.INVISIBLE);
        mainLayout.addView(ring, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        btn.bringToFront();
        txt.bringToFront();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ball = new Ball(getApplicationContext(), ring, speed);
                mainLayout.addView(ball, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                btn.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                counter.bringToFront();
                counter.setVisibility(View.VISIBLE);
                timer.schedule(setTxt, 1000);
            }
        });

    }
}
