package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

public class AlienEnemy
{
    Alien alien;

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienEnemy(Context context, Point size, int pathHeight, String type)
    {
        if (type.equals("drone"))
        {
            this.alien = new Drone(context, size, new DroneMovementStrategy(5), pathHeight, 36);
        }
    }

    //Return the created alien
    public Alien getAlien()
    {
        return this.alien;
    }

}
