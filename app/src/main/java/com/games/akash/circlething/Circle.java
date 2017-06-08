package com.games.akash.circlething;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by endenoa18 on 5/15/2017.
 */

public class Circle extends View
{
    private ShapeDrawable baseCircle;
    private ShapeDrawable goodGate;
    private ShapeDrawable badGate;
    private ShapeDrawable topCircle;
    private boolean lDown = false;
    private boolean rDown = false;
    private int angle = 80;
    int mWidth= this.getResources().getDisplayMetrics().widthPixels;
    int mHeight= this.getResources().getDisplayMetrics().heightPixels;
    int radius = 2 * mWidth/5;
    int x = mWidth/2;
    int y = mHeight/2;

    public Circle(Context context) {
        super(context);
        init(context);
    }


    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Circle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    final Handler rotateHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg)
        {
            if(lDown)
            {
                angle-=1;
                invalidate();
            }
            if(rDown)
            {
                angle+=1;
                invalidate();
            }
        }
    };

    final Runnable rotate = new Runnable() {
        @Override
        public void run()
        {
            while(lDown||rDown)
            {
                rotateHandler.sendEmptyMessage(0);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        int x = (int)event.getX();


        // put your code in here to handle the event
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();
                if(x>=mWidth/2)
                {
                    rDown = true;
                    Thread t = new Thread(rotate);
                    t.start();
                }
                if(x<mWidth/2)
                {
                    lDown = true;
                    Thread t = new Thread(rotate);
                    t.start();
                }
                break;
            case MotionEvent.ACTION_UP:
                //Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();
                lDown = false;
                rDown = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(x>=mWidth/2)
                {
                    rDown = true;
                    lDown = false;
                }
                if(x<mWidth/2)
                {
                    lDown = true;
                    rDown = false;
                }
        }

        invalidate();
        return true;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {

        super.setOnTouchListener(l);
    }

    public void init(Context context)
    {
        baseCircle = new ShapeDrawable(new OvalShape());
        baseCircle.getPaint().setColor(Color.GRAY);
        baseCircle.setBounds(x-radius, y-radius, x+radius, y+radius);

        topCircle = new ShapeDrawable(new OvalShape());
        topCircle.getPaint().setColor(Color.WHITE);
        topCircle.setBounds(x-radius+x/8, y-radius+x/8, x+radius - x/8, y+radius - x/8);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        goodGate = new ShapeDrawable(new ArcShape(angle+180,20));
        goodGate.getPaint().setColor(Color.GREEN);
        goodGate.setBounds(x-radius, y-radius, x+radius, y+radius);

        badGate= new ShapeDrawable(new ArcShape(angle,20));
        badGate.getPaint().setColor(Color.RED);
        badGate.setBounds(x-radius, y-radius, x+radius, y+radius);

        baseCircle.draw(canvas);
        goodGate.draw(canvas);
        badGate.draw(canvas);
        topCircle.draw(canvas);
    }
}
