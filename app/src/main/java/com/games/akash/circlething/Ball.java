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

import java.util.Objects;

/**
 * Created by benamar18 on 5/12/2017.
 */

public class Ball extends View {

    private ShapeDrawable ball;
    int deviceWidth = this.getResources().getDisplayMetrics().widthPixels;
    int deviceHeight = this.getResources().getDisplayMetrics().heightPixels;
    public final int RADIUS = deviceWidth/20;
    private static int Vx = 0;
    private static int Vy = 5;
    private static int x;
    private static int y;
    private static int Vt = 5;
    Thread m;
    private boolean stop = false;

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
        Random rand = new Random();
        m = new Thread(move);
        m.start();
    }

    final Handler moveHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            ball.setBounds(ball.getBounds().top+Vy, ball.getBounds().right+Vx,
                    ball.getBounds().bottom+Vy, ball.getBounds().left+Vx);
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

    public void setVelocity(int v)
    {
        this.Vt = v;
    }

    public int getVelocity()
    {
        return Vt;
    }
}

