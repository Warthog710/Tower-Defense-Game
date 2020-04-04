package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

public class AlienFactory
{
    Alien alien;

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienFactory(Context context, Point size, int pathHeight, String type)
    {
        if (type.equals("drone"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(10)
                    .setSpeed(5)
                    .setLocation(size)
                    .setPathHeight(pathHeight)
                    .setAttributeSize(36)
                    .setResist(1)
                    .setBitmap(context)
                    .attachHealthBar(10)
                    .attachMovementStrategy()
                    .build();
        }
    }

    //Return the created alien
    public Alien getAlien()
    {
        return this.alien;
    }

}
