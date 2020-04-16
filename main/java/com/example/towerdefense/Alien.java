package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

//Interface for all alien types
public interface Alien
{
    void onHit(float dmg);
    void setResistance(float resist);
    void draw(Canvas canvas, Paint paint);
    void move();
    Point getLocation();
    float getHealth();
    boolean checkCollision(Rect base);
    Rect getHitbox();
    String getInfo();
    void kill();
}
