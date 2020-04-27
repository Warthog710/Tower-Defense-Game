package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

public class AlienFactory
{
    Alien alien;

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienFactory(Context context, Point size, int pathHeight, String type, PathPoints start)
    {
        //Build a soldier
        if (type.equals("soldier"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(50)
                    .setSpeed(3)
                    .setLocation(start)
                    .setPathHeight(pathHeight)
                    .setAttributeSize(48)
                    .setResist(5)
                    .setBitmap(context, type)
                    .setInfo("Soldier: medium speed with medium health")
                    .setResist(2)
                    .attachHealthBar(10)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a behemoth
        if (type.equals("behemoth"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(150)
                    .setSpeed(1)
                    .setLocation(start)
                    .setPathHeight(pathHeight)
                    .setAttributeSize(48)
                    .setResist(1)
                    .setBitmap(context, type)
                    .setInfo("Behemoth: low speed with a large health pool")
                    .attachHealthBar(10)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a drone
        if (type.equals("drone"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(10)
                    .setSpeed(5)
                    .setLocation(start)
                    .setPathHeight(pathHeight)
                    .setAttributeSize(48)
                    .setResist(10)
                    .setBitmap(context, type)
                    .setInfo("Drone: fast but with low health")
                    .attachHealthBar(10)
                    .attachMovementStrategy(type)
                    .build();
        }
    }

    //Return the created alien
    public Alien getAlien()
    {
        return this.alien;
    }

}
