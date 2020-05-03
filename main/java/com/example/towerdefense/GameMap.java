package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Path;
import android.graphics.PathMeasure;

public class GameMap
{
    Bitmap base;
    Point size;
    private final int pathWidth=50;

    Path path;
    ArrayList<PathPoints> mPathCords = new ArrayList<>();

    enum level {level1, level2, level3}

    private level currentLevel;

    private int waveCount = 3;
    private int currentWave = 1;
    int spawnCounter = 0;
    private int ticks;

    //GameMap constructor
    public GameMap(Context context, Point size)
    {
        this.base = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);
        this.size = size;
    }

    //Converts the defined path into an ArrayList of points
    private void calculatePathCords()
    {
        //Method variables.
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float step = 1;
        float counter = 0;
        float[] cords = new float[2];
        float[] tangent = new float[2];

        while (true)
        {
            //While not at the end of the path element, execute the following...
            while (counter < length)
            {
                pm.getPosTan(counter, cords, tangent);
                double angle = Math.atan2((double)tangent[0], (double)tangent[1]);
                angle = angle * (180/Math.PI);
                mPathCords.add(new PathPoints(new Point((int)cords[0], (int)cords[1]), (int)angle));
                counter += step;
            }

            //If there is another path element...
            if (pm.nextContour())
            {
                //Get the new length and loadLevel counter to zero.
                length = pm.getLength();
                counter = 0;
            }

            //Else, end of path reached. Break out of loop.
            else
                break;
        }
    }


    //Draw the map
    void draw (Canvas canvas, Paint paint, BitMapContainer mBitmaps)
    {
        //Draw the background...
        canvas.drawBitmap(mBitmaps.getBackground(getCurrentLevel() - 1), 0, 0, null);

        //Draw the path...
        paint.setStrokeWidth(pathWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(255, 101, 67, 33));
        canvas.drawPath(path, paint);
        paint.reset();

        //Draw the base...
        canvas.drawBitmap(base, size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), null);
    }

    //Returns the hitbox of the base. Used to determine if the aliens intersect.
    public Rect getBaseCords()
    {
        return new Rect(size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), size.x, (size.y / 2) + (base.getHeight() / 2));
    }

    public ArrayList<PathPoints> getPathCords()
    {
        return mPathCords;
    }

    //Getter and setter methods.
    public int getWaveCount() { return waveCount; }
    public int getCurrentWave() { return currentWave; }
    public void setCurrentWave(int currentWave) { this.currentWave = currentWave; this.spawnCounter = 0; }


    //Returns true if the point is locationed on or near the path or base
    public boolean inPath(Point location)
    {
        boolean inpath=false;
        if (mPathCords != null) {
            Iterator<PathPoints> pathIterator = mPathCords.iterator();
            while (pathIterator.hasNext()) {
                PathPoints pathPoint = pathIterator.next();
                if(location.x<pathPoint.getPath().x+pathWidth/2 +Tower.towerSize/4 && location.x>pathPoint.getPath().x-pathWidth/4-Tower.towerSize/2
                &&location.y<pathPoint.getPath().y+pathWidth/2 +Tower.towerSize/4 && location.y>pathPoint.getPath().y-pathWidth/4 -Tower.towerSize/2){
                    inpath=true;
                    break;
                }
            }
        }
        if (getBaseCords().contains(location.x, location.y)){
            inpath=true;
        }
        return  inpath;
    }

    //Change the current level
    public void changeLevel(GameMap.level level)
    {
        this.path = new LevelPath(level, this.size).getPath();
        mPathCords.clear();
        calculatePathCords();
        this.currentLevel = level;
        System.out.println(level);
    }

    public int getCurrentLevel(){ //return the current level
        int level=00000000;
        switch (currentLevel){

            case level1:
                level=1;
                break;
            case level2:
                level=2;
                break;
            case level3:
                level=3;
                break;
        }
        return level;
    }

}
