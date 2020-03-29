package com.example.towerdefense;

public abstract class Tower extends GameObject
{
    public TowerData mTowerData;
    public long lastShot;
    final static int towerSize=100;

    public abstract void shoot(GameWorld gameWorld);
    public void setSize(){setAttributeSize(towerSize);}
}
