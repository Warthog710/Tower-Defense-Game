package com.example.towerdefense;

public class Angle {
    double angle;
    public Angle(){}
    public Angle(double angle){
        this.angle=angle%360;
    }

    public void setAngle(int angle) {
        this.angle = angle%360;
    }

    public double getAngle() {
        return angle;
    }
}
