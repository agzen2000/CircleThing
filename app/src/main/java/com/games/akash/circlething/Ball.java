package com.games.akash.circlething;

import android.graphics.Color;

import java.util.Objects;

/**
 * Created by benamar18 on 5/12/2017.
 */

public class Ball {



    public final int ballRadius =5;
    private double ballSpeed;
    private Color color;
    private int direction;
    private double x;
    private double y;

    public Ball( double speed,Color color,double x, double y  ){

        this.ballSpeed = speed;
        this.color= color;
        this.x = x;
        this.y=y;

    }





    public void setBallSpeed(double s) {
        ballSpeed = s;

    }

    public double getBallSpeed(){
        return ballSpeed;
    }


    public void  setcolor(Color c){
        color= c;
    }
    public Color getColor (){
        return  color;
    }

    public void setDirection(int d){
        direction = d;
    }
    public int getDirection(){
        return  direction;
    }
    public void setX(double x) {
         this.x  = x;

    }

    public double getX() {
        return x;
    }
    public void setY(double y) {
        this.y = y;

    }

    public double getY() {
        return y;
    }




}

