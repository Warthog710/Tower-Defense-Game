package com.example.towerdefense;

public class Angle
{
    private int angle;

    public Angle(){}

    public Angle(int angle)
    {
        //make sure the angle is never greater than 360
        this.angle=angle%360;

        //make sure the angle is always positive
        if (this.angle<0)
        {
            this.angle=360+angle;
        }
    }

    public void setAngle(int angle)
    {
        //make sure the angle is always positive and not greater than 360
        this.angle = angle%360;

        if (this.angle<0)
        {
            this.angle=360+angle;
        }
    }

    //Return the angle
    public int getAngle() {
        return angle;
    }
}
