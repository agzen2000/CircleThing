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
    int deviceWidth = this.getResources().getDisplayMetrics().widthPixels;
    int deviceHeight = this.getResources().getDisplayMetrics().heightPixels;
    public final int RADIUS = deviceWidth/20;
    private double angle = 1000;
    private static double vX;
    private static double vY;
    private static int vT = 5;
    private static int left;
    private static int right;
    private static int top;
    private static int bottom;
    private boolean stop = false;
    private Thread mThread;

    public Ball(Context context) {
        super(context);
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
        int x = deviceWidth /2;
        int y = deviceHeight /2;

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
        }
    };

    final Runnable move = new Runnable()
    {
        @Override
        public void run()
        {
            while(!stop)
            {
                moveHandler.sendEmptyMessage(0);
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
}

