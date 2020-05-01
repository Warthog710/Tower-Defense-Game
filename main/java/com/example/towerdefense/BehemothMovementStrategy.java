package com.example.towerdefense;

import android.graphics.Point;
import java.util.ArrayList;

//Basic movement strategy for the Soldier
public class BehemothMovementStrategy implements EnemyMovable
{
    //Class variables
    private int mSpeed = 2; //speed
    private int currentIndex = 0;
    private Angle mHeading;
    private Point mLocation;

    public BehemothMovementStrategy(Point mLocation)
    {
        this.mLocation = mLocation;
    }

    public void move(ArrayList<PathPoints> path)
    {
        //Increment current index by speed and retrieve the next point.
        this.currentIndex += this.mSpeed;
        this.mLocation = path.get(this.currentIndex).getPath();

        //Determine Heading...
        //double deltaY = (mLocation.y - path.get(currentIndex + 5).getPath().y);
        //double deltaX = (path.get(currentIndex + 5).getPath().x - mLocation.x);
        //int result = (int)Math.toDegrees(Math.atan2(deltaY, deltaX));
        //mHeading.setAngle(result + 90);
        //System.out.println("Heading " + mHeading.getAngle());
        //Move X direction
        //mLocation.x += mSpeed;
        //Move Y direction
    }

    //Getter methods
    public int getAngle() { return mHeading.getAngle(); }
    public Point getLocation() { return mLocation; }
}