package com.example.towerdefense;

import android.graphics.Path;
import android.graphics.Point;

public class LevelPath
{
    private Path path = new Path();

    //builds the correct path based on the current level.
    public LevelPath (GameMap.level currentLevel, Point size)
    {
        switch (currentLevel)
        {
            case level1:
                path.moveTo(0, size.y/2);
                path.lineTo(size.x / 10, size.y /2);
                path.cubicTo(size.x / 4, size.y /2, size.x /9, size.y/1, size.x /2, size.y - (size.y / 3));
                path.cubicTo(size.x /2 + (size.x /8), size.y/2 - (size.y / 10), size.x - (size.x / 3), size.y/2, size.x - (size.x / 4), size.y/2);
                path.lineTo(size.x, size.y/2);
                break;

            case level2:
                path.moveTo(size.x / 20, size.y);
                path.cubicTo(size.x / 8, -size.y + (size.y /10), size.x /4, size.y + (size.y /20), size.x /3, size.y / 2 + (size.y /10));
                path.cubicTo(size.x /2 + (size.x /5), (-size.y / 2) + (size.y / 3), size.x /2, size.y /2, size.x - (size.x /10), size.y/2);
                break;

            case level3:
                path.moveTo(-10, size.y /3);
                path.cubicTo(size.x /4, size.y /2 +(size.y/5), size.x /4 + (size.x /10), size.y/2, size.x/2, size.y/2);
                path.lineTo(size.x, size.y/2);
                break;

             default:
                 break;
        }
    }

    public Path getPath() { return this.path; }
}
