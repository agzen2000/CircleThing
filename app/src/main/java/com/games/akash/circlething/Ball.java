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
    private static int Vx = 5;
    private static int Vy = 5;
    private static int Vt = 5;
    private static int l;
    private static int r;
    private static int t;
    private static int b;
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

        m = new Thread(move);
        m.start();
    }

    final Handler moveHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            l = ball.getBounds().left;
            r = ball.getBounds().right;
            t = ball.getBounds().top;
            b = ball.getBounds().bottom;
            l+=Vx;
            r+=Vx;
            t+=Vy;
            b+=Vy;
            ball.setBounds(l, t, r, b);
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

