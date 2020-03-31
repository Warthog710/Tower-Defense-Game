package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

public class TowerFactory {

    public Tower getTower(Tower.TowerType towerType, Context context, Point mLocation){
        Tower tower=null;
        switch(towerType)
        {
            case PLASMA: //make a plasma tower
                tower = new PlasmaTower(context, mLocation);
                break;
        }
        return tower;
    }
}
