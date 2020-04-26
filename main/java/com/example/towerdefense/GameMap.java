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
    Bitmap mMap, base;
    Point size;
    private final int pathWidth=30;

    Path testPath = new Path();
    ArrayList<PathPoints> mPathCords = new ArrayList<>();

    enum level {level1, level2, level3}

    private level currentLevel;

    private int waveCount = 3;
    private int currentWave = 1;
    int lastSpawn = 0;
    int spawnCounter = 0;
    private int ticks;

    //GameMap constructor
    public GameMap(Context context, Point size)
    {
        this.mMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        this.base = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);
        this.mMap = Bitmap.createScaledBitmap(mMap, size.x, size.y, true);
        this.size = size;

        //Pre-defined path. Move inside custom pathRoute object?
        testPath.moveTo(0, size.y/2);
        testPath.lineTo(100, size.y/2);
        testPath.lineTo(150, (size.y /2) + 200);
        testPath.cubicTo(150, (size.y /2) + 400, 234, (size.y /2) + 113, 300, (size.y /2));
        testPath.cubicTo(1645, (size.y /2) + 212, -263, (size.y /2) + 634, 1000, (size.y /2));
        testPath.cubicTo(1300, (size.y /2) - 177, 1200, (size.y /2) + 14, 1500, (size.y /2));
        testPath.lineTo(1920, (size.y/2));

        calculatePathCords();
    }

    //Converts the defined path into an ArrayList of points
    private void calculatePathCords()
    {
        //Method variables.
        PathMeasure pm = new PathMeasure(testPath, false);
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
    void draw (Canvas canvas, Paint paint)
    {
        //Draw the background...
        canvas.drawBitmap(mMap, 0, 0, null);

        //Draw the path...
        paint.setStrokeWidth(pathWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawPath(testPath, paint);
        paint.reset();

        //Draw the base...
        canvas.drawBitmap(base, size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), null);
    }

    //Returns the hitbox of the base. Used to determine if the aliens intersect.
    public Rect getBaseCords()
    {
        return new Rect(size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2), size.x, (size.y / 2) + (base.getHeight() / 2));
    }

    //Used when an alien spawns to verify they spawn on the path.
    public int getPathHeight()
    {
        return pathWidth;
    }

    public ArrayList<PathPoints> getPathCords()
    {
        return mPathCords;
    }

    //Spawns a pre-defined set of waves when called.
    public ArrayList<Alien> spawn(Context context, ArrayList<Alien> aliens)
    {
        ticks+=1;
        switch(currentWave)
        {
            case 1:
                //If enough time has elapsed since the last spawn
                if (ticks - lastSpawn > 40)
                {
                    //Spawn five enemies.
                    if (spawnCounter < 20)
                    {
                        //Make enemy, increment spawnCounter, and set lastSpawn.
                        aliens.add(new AlienFactory(context, size, getPathHeight(), "drone", getPathCords().get(0)).getAlien());
                        spawnCounter++;
                        lastSpawn = ticks;
                    }
                }

                //If five enemies have been spawned and the enemy list is empty... Spawn the next wave...
                if (spawnCounter >= 20 && aliens.isEmpty())
                {
                    spawnCounter = 0;
                    currentWave++;
                }
                break;

                //Wave 2
            case 2:
                if (ticks - lastSpawn > 60)
                {
                    if (spawnCounter < 10)
                    {
                        aliens.add(new AlienFactory(context, size, getPathHeight(), "soldier", getPathCords().get(0)).getAlien());
                        spawnCounter++;
                        lastSpawn = ticks;
                    }
                }
                if (spawnCounter >= 10 && aliens.isEmpty())
                {
                    spawnCounter = 0;
                    currentWave++;
                }
                break;

                //Wave 3
            case 3:
                if (ticks - lastSpawn > 80)
                {
                    if (spawnCounter < 15)
                    {
                        aliens.add(new AlienFactory(context, size, getPathHeight(), "behemoth", getPathCords().get(0)).getAlien());
                        spawnCounter++;
                        lastSpawn = ticks;
                    }
                }
                if (spawnCounter >= 15 && aliens.isEmpty())
                {
                    spawnCounter = 0;
                    currentWave++;
                }
                break;
        }

        //Return the new aliens list.
        return aliens;
    }

    //Getter and setter methods.
    public int getWaveCount() { return waveCount; }
    public int getCurrentWave() { return currentWave; }
    public void setCurrentWave(int currentWave) { this.currentWave = currentWave; this.spawnCounter = 0; }

    //Called to check if something collides with the path.

    //NOTE!!! CURRENTLY BROKEN!!!
    public boolean inPath(Point location)
    {
        boolean inpath=false;
        if (mPathCords != null) {
            Iterator<PathPoints> pathIterator = mPathCords.iterator();
            while (pathIterator.hasNext()) {
                PathPoints pathPoint = pathIterator.next();
                if(location.x<pathPoint.getPath().x+pathWidth/2 && location.x>pathPoint.getPath().x-pathWidth/2
                &&location.y<pathPoint.getPath().y+pathWidth/2 && location.y>pathPoint.getPath().y-pathWidth/2){
                    inpath=true;
                    break;
                }
            }
        }
        System.out.println(inpath);
        return  inpath;
    }
    public void changeLevel(GameMap.level level){
        currentLevel=level;
    }
    public int getCurrentLevel(){
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
