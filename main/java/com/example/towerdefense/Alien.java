package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

//Interface for all alien types
public interface Alien
{
    void draw(Canvas canvas, Paint paint, BitMapContainer mBitmaps, boolean isPaused);
    void draw(Canvas canvas, Paint paint);
    void move(ArrayList<PathPoints> path);
    boolean checkCollision(Rect base);
    void setResistance(float resist);
    String getResistance();
    void onHit(float dmg);
    boolean getStatus();
    Point getLocation();
    float getHealth();
    Rect getHitbox();
    String getInfo();
    int getMoney();
    void kill();
}
