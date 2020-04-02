package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


public class AlienHealthBar
{
    private int width;
    private int height;
    private float maxHealth;

    //Class constructor
    public AlienHealthBar(int height, int width, float maxHealth)
    {
        this.height = height;
        this.width = width;
        this.maxHealth = maxHealth;
    }

    //Draws the two rectangles that compose the health bar
    public void draw(Canvas canvas, Paint paint, float health, Point mLocation)
    {
        //Draw back of health bar...
        paint.setColor(Color.RED);
        canvas.drawRect(mLocation.x, mLocation.y - height, mLocation.x + width, mLocation.y, paint);

        //Draw front of health bar...
        paint.setColor(Color.GREEN);
        canvas.drawRect(mLocation.x, mLocation.y - height, mLocation.x + getGoodHealthWidth(health), mLocation.y, paint);

    }

    //Returns the width of the "current health" section of the health bar
    private float getGoodHealthWidth(float health)
    {
        float healthWidth = ((width / maxHealth) * health);
        return healthWidth;
    }

}
