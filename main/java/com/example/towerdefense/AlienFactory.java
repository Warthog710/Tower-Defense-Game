package com.example.towerdefense;

public class AlienFactory
{
    Alien alien;

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienFactory(String type, PathPoints start)
    {
        //Build a soldier
        if (type.equals("soldier"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(50)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(1)
                    .setMoney(25)
                    .setInfo("Soldier: medium speed with medium health")
                    .setResist(1)
                    .attachHealthBar(10)
                    .setType(type)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a behemoth
        if (type.equals("behemoth"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(150)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(1)
                    .setMoney(50)
                    .setInfo("Behemoth: low speed with a large health pool")
                    .attachHealthBar(10)
                    .setType(type)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a drone
        if (type.equals("drone"))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(10)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(1)
                    .setMoney(10)
                    .setInfo("Drone: fast but with low health")
                    .attachHealthBar(10)
                    .setType(type)
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
