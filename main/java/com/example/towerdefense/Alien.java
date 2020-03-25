package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;

public interface Alien
{
    void onHit(float dmg);
    void setResistance(float resist);
    void draw(Canvas canvas, Paint paint);
    Point getLocation();
}
