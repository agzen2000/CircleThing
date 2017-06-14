package com.games.akash.circlething;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by benamar18 on 5/12/2017.
 */

public class Ball extends View {

    private ShapeDrawable ball;
    private int deviceWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int deviceHeight = this.getResources().getDisplayMetrics().heightPixels;
    private int x = deviceWidth /2;
    private int y = deviceHeight /2;
    public final int RADIUS = deviceWidth/25;
    private MainActivity mainActivity;
    private double angle = 1000;
    private double vX;
    private double vY;
    private  int vT;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private boolean start = false;
    private Thread mThread;
    private Circle ring;

    public Ball(Context context, MainActivity main, Circle c, int speed) {
        super(context);
        mainActivity = main;
        vT = speed;
        ring = c;
        init(context);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Ball(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init (Context context) {

        ball = new ShapeDrawable(new OvalShape());
        ball.getPaint().setColor(Color.BLUE);
        ball.setBounds(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);
        if(angle == 1000) {
            Random rand = new Random();
            do {
                angle = rand.nextInt(361);
            } while(angle < 5 || (angle>85 && angle<95) || (angle>175 && angle<185) || (angle>265 && angle<275) || angle > 355);
            angle = Math.toRadians(angle);
        }

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        vX = cos * vT;
        vY = sin * vT;

        mThread = new Thread(move);
        mThread.start();
    }

    final Handler moveHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            left = ball.getBounds().left;
            right = ball.getBounds().right;
            top = ball.getBounds().top;
            bottom = ball.getBounds().bottom;

            left+=vX;
            right+=vX;
            top+=vY;
            bottom+=vY;
            ball.setBounds(left, top, right, bottom);
            invalidate();

            int relativeX = left - x + RADIUS;
            int relativeY = y - top - RADIUS;
            int relativeRadius = ring.radius - x/8 - RADIUS;

            if(relativeRadius * relativeRadius <= relativeX * relativeX + relativeY * relativeY) {
                boolean sin = true;
                boolean cos = true;
                if(relativeX < 0) {
                    cos = false;
                }
                if(relativeY < 0) {
                    sin = false;
                }

                if(sin && cos) {
                    mainActivity.complete(ring.angle == 270);
                    stopBall();
                } else if(sin && !cos) {
                    mainActivity.complete(ring.angle == 180);
                    stopBall();
                } else if(!sin && cos) {
                    mainActivity.complete(ring.angle == 0);
                    stopBall();
                } else if(!sin && !cos) {
                    mainActivity.complete(ring.angle == 90);
                    stopBall();
                }
            }
        }
    };

    private void stopBall() {
        vX = 0;
        vY = 0;
        ball.setBounds(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);
    }

    final Runnable move = new Runnable()
    {
        @Override
        public void run()
        {
            while(true)
            {
                if(start) {
                    moveHandler.sendEmptyMessage(0);
                }
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    @Override
    protected void onDraw(Canvas canvas) {
        ball.draw(canvas);
    }

    public void start() {
        start = true;
    }
}

