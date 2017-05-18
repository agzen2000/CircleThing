package com.games.akash.circlething;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by endenoa18 on 5/15/2017.
 */

public class Circle extends View
{
    private ShapeDrawable mDrawable;
    int mWidth= this.getResources().getDisplayMetrics().widthPixels;
    int mHeight= this.getResources().getDisplayMetrics().heightPixels;

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

    public void init(Context context)
    {
        int x = mWidth/2;
        int y = mHeight/2;
        int radius = mWidth/3;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(Color.BLACK);
        mDrawable.setBounds(x-radius, y-radius, x+radius, y+radius);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        mDrawable.draw(canvas);
    }
}
