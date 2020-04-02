package com.example.towerdefense;

public abstract class Movable extends GameObject
{
    Angle mHeading; //angle that the object is heading
    int mSpeed; //speed
    abstract void move(); //how it moves
}
