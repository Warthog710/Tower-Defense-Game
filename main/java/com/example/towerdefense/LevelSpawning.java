package com.example.towerdefense;

import java.util.Random;
import java.util.ArrayList;

public class LevelSpawning
{
    //Time till next wave in MS
    private int ticksTillNextWave = 0;
    private int timeTillNextSpawn = 0;
    private int waveCount;
    private int currentWave = 0;
    private int spawnCounter = 0;
    private int enemyPerWave;
    private int ticks = 0;

    //Note behemoth chance is whatever is not covered by the drone and soldier chance
    private int droneChance;
    private int soldierChance;



    public LevelSpawning(GameMap.level currentLevel)
    {
        switch (currentLevel)
        {
            case level1:
                waveCount = 5;
                enemyPerWave = 10;
                droneChance = 75;
                soldierChance = 20;
                //ticksTillNextWave = 250;
                break;

            case level2:
                waveCount = 5;
                enemyPerWave = 15;
                droneChance = 50;
                soldierChance = 40;
                //ticksTillNextWave = 250;
                break;

            case level3:
                waveCount = 5;
                enemyPerWave = 20;
                droneChance = 30;
                soldierChance = 50;
                //ticksTillNextWave = 250;
                break;
        }
    }

    public ArrayList<Alien> spawn(ArrayList<Alien> aliens, PathPoints start)
    {
        this.ticks += 1;

        //Spawn a new enemy if...
        //1. Enough ticks have elapsed since last enemy spawn
        //2. Not waiting for a new wave
        //3. Soldiers per wave is not full.



        if (spawnCounter < enemyPerWave && ticksTillNextWave - ticks <= 0 && timeTillNextSpawn - ticks <= 0)
        {
            Random random = new Random();

            //System.out.println("I'm in spawning");
            //Spawn an enemy based on the defined chance
            //Set wait time till next spawn

            //Set wait time till next wave to zero since we are in a wave
            ticksTillNextWave = 0;

            //Get a random number 0-99
            int temp = random.nextInt() % 100;

            //Spawn a drone
            if (temp <= droneChance)
            {
                aliens.add(new AlienFactory("drone", start).getAlien());
                timeTillNextSpawn = 40;
                ticks = 0;
                spawnCounter++;
            }

            //Spawn a soldier
            else if (temp <= (droneChance + soldierChance))
            {
                aliens.add(new AlienFactory("soldier", start).getAlien());
                timeTillNextSpawn = 60;
                ticks = 0;
                spawnCounter++;
            }

            //Spawn a behemoth
            else
            {
                aliens.add(new AlienFactory("behemoth", start).getAlien());
                timeTillNextSpawn = 80;
                ticks = 0;
                spawnCounter++;
            }
        }

        //If we have spawned the number of enemies in each wave. Wait until it is dead
        if(spawnCounter >= enemyPerWave && aliens.isEmpty())
        {
            timeTillNextSpawn = 0;
            ticks = 0;
            spawnCounter = 0;
            currentWave++;
            System.out.println("Next wave: " + currentWave);
            ticksTillNextWave = 250;
        }

        return aliens;
    }

    public int getWaveCount()
    {
        return waveCount;
    }

    public int getCurrentWave()
    {
        return currentWave;
    }
}
