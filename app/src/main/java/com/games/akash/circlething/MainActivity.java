package com.games.akash.circlething;

import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    int speed;
    Timer timer;
    int counterNum;
    MainActivity mActivity;
    int streak;

    final android.os.Handler setTextHandler = new android.os.Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg)
        {
            counter.setText(String.valueOf(counterNum));
        }
    };
    final Runnable setTextRunnable = new Runnable() {
        @Override
        public void run()
        {
            setTextHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    final android.os.Handler setInvisibleHandler = new android.os.Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg)
        {
            counter.setVisibility(View.INVISIBLE);
        }
    };
    final Runnable setInvisibleRunnable = new Runnable() {
        @Override
        public void run()
        {
            setInvisibleHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speed = 5;
        streak = 0;
        ring = new Circle(this);
        mainLayout = (RelativeLayout)findViewById(R.id.activity_main);
        btn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.textView);
        counter = (TextView) findViewById(R.id.counter);
        timer = new Timer();
        mActivity = this;
        counterNum = 3;
        counter.setVisibility(View.INVISIBLE);
        mainLayout.addView(ring, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        btn.bringToFront();
        txt.bringToFront();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ball = new Ball(getApplicationContext(), mActivity, ring, speed);
                mainLayout.addView(ball, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                btn.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                counter.bringToFront();
                counter.setText("3");
                counterNum = 3;
                counter.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        counterNum--;
                        if(counterNum == 0) {
                            timer.cancel();
                            ball.start();
                            ring.start();
                            Thread t = new Thread(setInvisibleRunnable);
                            t.start();

                        } else {
                            Thread t = new Thread(setTextRunnable);
                            t.start();
                        }
                    }
                }, 1000, 1000);
            }
        });

    }

    public void complete(boolean win) {
        ball.setVisibility(View.GONE);
        if(win) {
            streak++;
            txt.setText("You WON: " + streak);
            btn.setText("NEXT");
            speed = speed + 1;
        } else {
            txt.setText("You LOSE");
            btn.setText("RETRY");
            speed = 5;
            streak = 0;
        }
        btn.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);
        ring.stop();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ball = new Ball(getApplicationContext(), mActivity, ring, speed);
                mainLayout.addView(ball, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                btn.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                ball.start();
                ring.start();
            }
        });
    }
}
