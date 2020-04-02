package com.example.towerdefense;

//Basic movement strategy for the Drone
public class DroneMovementStrategy extends Movable
{
    public DroneMovementStrategy(int speed)
    {
        this.mSpeed = speed;
    }

    public void move()
    {
        //Move X direcion
        mLocation.x += mSpeed;

        //Move Y direction
    }
}
