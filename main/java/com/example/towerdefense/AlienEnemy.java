package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

public class AlienEnemy extends GameObject
{
    Alien alien;
    private Movable strategy;

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienEnemy(Context context, Point size, String type)
    {
        if (type.equals("drone"))
        {
            this.alien = new Drone(context, size);
            strategy = new DroneMovementStrategy();
        }
    }

    //Call the corresponding movement strategy.
    public void move()
    {
        strategy.move();
    }
}
