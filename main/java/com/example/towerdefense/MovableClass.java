package com.example.towerdefense;

public abstract class MovableClass extends GameObject
{
    Angle mHeading;
    int mSpeed;
    abstract void move();
}
