package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class UIController implements InputObserver
{
    Context context; //hold onto the context for later use

    public UIController(GameEngineBroadcaster b, Context context) //make the controller
    {
        b.addObserver(this); //add it as an observer
        this.context=context;
    }

    @Override
    public void handleInput(MotionEvent event, GameWorld gameState, HUD mHud)
    {
        ArrayList<Rect> buttons=mHud.getControls();
        int i = event.getActionIndex();
        int x = (int) event.getX(i); //get the x location
        int y = (int) event.getY(i); //get the y location
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        TowerFactory towerFactory = new TowerFactory(); //make a tower factory
        if(eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP)
        {

            if (buttons.get(HUD.PAUSE).contains(x, y)) //the player has hit the pause button
            {
                // If the game is not paused
                if (!gameState.getPaused())
                {
                    // Pause the game
                    gameState.pause();
                }

                //If game is over start a new game
                else if (gameState.getGameOver())
                {
                    gameState.startNewGame();
                }

                //Paused and not game over
                else if (gameState.getPaused()
                        && !gameState.getGameOver())
                {
                    gameState.resume();
                }
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
            }
            //if the player hits the plasma tower and the game is not paused
            else if (buttons.get(HUD.PlasmaTower).contains(x, y) && !gameState.getPaused() )
            {
                if(gameState.getCash()>=50){
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.PLASMA);
                    gameState.loseCash(50);
                }
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
            }
            //laser tower
            else if (buttons.get(HUD.LaserTower).contains(x, y) && !gameState.getPaused() )
            {
                if(gameState.getCash()>=100){
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.LASER);
                    gameState.loseCash(100);
                }
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
            }
            //rocket tower
            else if (buttons.get(HUD.RocketTower).contains(x, y) && !gameState.getPaused() )
            {
                if(gameState.getCash()>=100){
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.ROCKET);
                    gameState.loseCash(100);
                }
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
            }
            //if the player has a tower to place and the game is not paused and the location is not on the path
            else if (gameState.getmPlacing() && !gameState.getPaused()&& !gameState.mMap.inPath(new Point(x,y)))
            { //place tower
                gameState.closemPlacing();
                gameState.addTower(towerFactory.getTower (gameState.getTowerType(),context,new Point(x,y)));
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
            }
            else if(mHud.towerInfo !=null && mHud.towerInfo.upgradeButton.contains(x, y)){
                if (gameState.getCash()>=mHud.towerInfo.upgradeCost()){
                    gameState.loseCash(mHud.towerInfo.upgradeCost());
                    mHud.towerInfo.upgrade();
                }
                mHud.removeAlienInfo();
            }
            else if (gameState.mMap.inPath(new Point(x,y))){ //the player has clicked the path (trying to click a enemy)
                mHud.removeTowerInfo();
                mHud.removeAlienInfo();
                Iterator<Alien> alienIterator = gameState.mAliens.iterator();
                while(alienIterator.hasNext())
                {
                    Alien currentAlien = alienIterator.next();
                    if (currentAlien.getHitbox().contains(x,y)){
                        mHud.addAlienInfo(currentAlien);
                        break;
                    }
                }
            }
            //if the player has clicked a tower
            else if (gameState.mTowers != null)
            {
                mHud.removeAlienInfo();
                Iterator<Tower> towerIterator = gameState.mTowers.iterator();
                while(towerIterator.hasNext())
                {
                    Tower currentTower = towerIterator.next();
                    if (currentTower.contains(new Point (x,y))){
                        //gameState.range=new Circle(currentTower.mLocation, currentTower.mRange);
                        mHud.addTowerInfo(currentTower);
                        break;

                    }
                    mHud.removeTowerInfo();
                }

            }
        }
    }
}