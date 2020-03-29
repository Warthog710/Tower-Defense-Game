package com.example.towerdefense;

import android.graphics.Point;

public class DroneMovementStrategy implements Movable
{
    private int speed = 5;
    private int heading = 90;

    public Point move(Point mLocation)
    {
        //Move X direction
        mLocation.x += speed;

        //Move Y direction

        return mLocation;

    }
}
