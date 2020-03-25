package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Drone extends GameObject implements Alien
{
    private float health = 10;
    private float resist = 1;

    public Drone(Context context, Point size)
    {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_alien);
        mLocation = new Point();
        mLocation.x = 50;
        mLocation.y = size.y / 2;
    }

    public void onHit(float dmg)
    {
        health = health - (dmg * resist);
    }

    public void setResistance(float resist)
    {
        this.resist = resist;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        super.draw(canvas, paint);
    }

}
