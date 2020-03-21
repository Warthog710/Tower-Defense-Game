package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Alien
{
    void onHit(float dmg);
    void setResistance(float resist);
    void draw(Canvas canvas, Paint paint);
}
