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
                eventType == MotionEvent.ACTION_POINTER_UP) {
            if (gameState.getGameRunning()) {

                if (buttons.get(HUD.PAUSE).contains(x, y)) //the player has hit the pause button
                {
                    // If the game is not paused
                    if (!gameState.getPaused()) {
                        // Pause the game
                        gameState.pause();
                    }

                    //If game is over start a new game
                    else if (gameState.getGameOver()) {
                        gameState.startNewGame();
                        gameState.setReadyForNewGameFalse();
                    }

                    //Paused and not game over
                    else if (gameState.getPaused()
                            && !gameState.getGameOver()) {
                        gameState.resume();
                    }
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                }
                //if the player hits the plasma tower and the game is not paused
                else if (buttons.get(HUD.PlasmaTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    if (gameState.getCash() >= 50) {
                        gameState.setmPlacing();
                        gameState.setTowerType(Tower.TowerType.PLASMA);
                        gameState.loseCash(50);
                    }
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    mHud.addPlacementInfo(Tower.TowerType.PLASMA);
                }
                //laser tower
                else if (buttons.get(HUD.LaserTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    if (gameState.getCash() >= 100) {
                        gameState.setmPlacing();
                        gameState.setTowerType(Tower.TowerType.LASER);
                        gameState.loseCash(100);
                    }
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    mHud.addPlacementInfo(Tower.TowerType.LASER);
                }
                //rocket tower
                else if (buttons.get(HUD.RocketTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    if (gameState.getCash() >= 100) {
                        gameState.setmPlacing();
                        gameState.setTowerType(Tower.TowerType.ROCKET);
                        gameState.loseCash(100);
                    }
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    mHud.addPlacementInfo(Tower.TowerType.ROCKET);
                } else if (buttons.get(HUD.SPEEDUP).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) //speed up
                {
                    gameState.changeSpeed();
                } else if (mHud.towerInfo != null && mHud.towerInfo.upgradeButton.contains(x, y)) { //upgrade button hit
                    if (gameState.getCash() >= mHud.towerInfo.upgradeCost()) {
                        gameState.loseCash(mHud.towerInfo.upgradeCost());
                        mHud.towerInfo.upgrade();
                    }
                    mHud.removeAlienInfo();
                    mHud.removeAlienInfo();
                } else if (mHud.placingInfo != null && mHud.placingInfo.cancelButton.contains(x, y)) { //hit the cancel button
                    gameState.addCashAmmount(mHud.placingInfo.cancel());
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    gameState.closemPlacing();
                }
                //if the player has a tower to place and the game is not paused and the location is not on the path
                else if (gameState.getmPlacing() && !gameState.getPaused() && !gameState.mMap.inPath(new Point(x, y))
                        && !mHud.onButton(new Point(x, y)) && !gameState.overTower(new Point(x, y))) { //place tower
                    gameState.closemPlacing();
                    gameState.addTower(towerFactory.getTower(gameState.getTowerType(), context, new Point(x, y)));
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                } else if (gameState.mMap.inPath(new Point(x, y)) && !gameState.getmPlacing()) { //the player has clicked the path (trying to click a enemy)
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    Iterator<Alien> alienIterator = gameState.mAliens.iterator();
                    while (alienIterator.hasNext()) {
                        Alien currentAlien = alienIterator.next();
                        if (currentAlien.getHitbox().contains(x, y)) {
                            mHud.addAlienInfo(currentAlien);
                            break;
                        }
                    }
                }
                //===========================================
                //===========================================
                //===========================================
                //code for taking the player back to the main screen after a game is iver
                else if(gameState.getPaused() && gameState.getGameOver() && !gameState.getReadyForNewGame()){
                    gameState.setGameRunningOff();
                    gameState.setReadyForNewGameFalse();
                }
                //===========================================
                //===========================================
                //code for starting the game once the player sees the map
                else if(gameState.getPaused() && gameState.getGameOver() && gameState.getReadyForNewGame()){
                    gameState.startNewGame();
                    gameState.setReadyForNewGameFalse();
                }

                //if the player has clicked a tower
                else if (gameState.mTowers != null && !gameState.getmPlacing()) {
                    mHud.removeTowerInfo();
                    mHud.removeAlienInfo();
                    mHud.removePlacementInfo();
                    Tower tower = gameState.onTower(new Point(x, y));
                    if (tower != null) {
                        mHud.addTowerInfo(tower);
                    }

                }
            }else{ //we are on the start screen
                if(gameState.startScreen.getButtons().get(StartScreen.LEVEL1).contains(x,y)){ //level 1
                    gameState.setGameRunningOn();
                    gameState.mMap.changeLevel(GameMap.level.level1);
                    gameState.setReadyForNewGameTrue();
                    gameState.reset();

                }
                else if(gameState.startScreen.getButtons().get(StartScreen.LEVEL2).contains(x,y)) //level 2
                {
                    gameState.setGameRunningOn();
                    gameState.mMap.changeLevel(GameMap.level.level2);
                    gameState.setReadyForNewGameTrue();
                    gameState.reset();
                }
                else if(gameState.startScreen.getButtons().get(StartScreen.LEVEL3).contains(x,y)) //level 3
                {
                    gameState.setGameRunningOn();
                    gameState.mMap.changeLevel(GameMap.level.level3);
                    gameState.setReadyForNewGameTrue();
                    gameState.reset();
                }
                else if(gameState.startScreen.getButtons().get(StartScreen.RULES).contains(x,y)) //rules or back
                {
                    gameState.startScreen.rulesOnOff();

                }
                else if(gameState.startScreen.getButtons().get(StartScreen.SOUND).contains(x,y)) //rules or back
                {
                    gameState.mSound.changeSound();

                }
            }
            System.out.println("running: " + gameState.getGameRunning());
            System.out.println("paused: " + gameState.getPaused());
            System.out.println("over: " + gameState.getGameOver());
            System.out.println("ready for new: "+gameState.getReadyForNewGame());
        }
    }

}