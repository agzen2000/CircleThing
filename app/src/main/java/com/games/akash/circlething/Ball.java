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
    private double angle = 1000;
    private static double vX;
    private static double vY;
    private static int vT = 5;
    private static int left;
    private static int right;
    private static int top;
    private static int bottom;
    private boolean start = false;
    private Thread mThread;
    private Circle ring;

    public Ball(Context context, Circle c, int speed) {
        super(context);
        init(context);
        vT = speed;
        ring = c;

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
            angle = rand.nextInt(361);
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
            int relativeY = top - y + RADIUS;
            int relativeRadius = ring.radius - ring.x/8 - RADIUS;

            if(relativeRadius * relativeRadius <= relativeX * relativeX + relativeY * relativeY) {

            }
        }
    };

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
                    Thread.sleep(10);
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

