package com.example.towerdefense;

public class Angle {
    int angle;
    public Angle(){}
    public Angle(int angle){
        this.angle=angle%360;
    }

    public void setAngle(int angle) {
        this.angle = angle%360;
    }

    public int getAngle() {
        return angle;
    }
}
