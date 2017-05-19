package com.games.akash.circlething;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    Circle c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = new Circle(this);
        setContentView(R.layout.activity_main);



    }
}
