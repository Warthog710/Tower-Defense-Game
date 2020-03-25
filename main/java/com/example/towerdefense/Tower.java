package com.example.towerdefense;

public abstract class Tower extends GameObject {
    public TowerData mToweData;
    public boolean canShoot=true;
    public long lastShot;
    private int towerSize=100;


    public abstract void shoot(GameWorld gameWorld);
    public void setSize(){setAttributeSize(towerSize);}
}
