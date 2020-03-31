package com.example.towerdefense;

public class Angle
{
    int angle;

    public Angle(){}

    public Angle(int angle){

        this.angle=angle%360; //make sure the angle is never greater than 360

        if (this.angle<0){ //make sure the angle is always positive
            this.angle=360+angle;
        }
    }

    public void setAngle(int angle) {

        this.angle = angle%360;
        if (this.angle<0){ //make sure the angle is always positive
            this.angle=360+angle;
        }
    }

    public int getAngle() {
        return angle;
    }
}
