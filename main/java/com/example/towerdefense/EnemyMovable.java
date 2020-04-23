package com.example.towerdefense;

import android.graphics.Point;

import java.util.ArrayList;

//Interface for enemy movement strategies
public interface EnemyMovable
{
    void move(ArrayList<PathPoints> path);
    int getAngle();
    Point getLocation();

}
