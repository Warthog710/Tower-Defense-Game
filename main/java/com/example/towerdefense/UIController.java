package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class UIController implements InputObserver
{
    Context context;
    public UIController(GameEngineBroadcaster b, Context context)
    {
        b.addObserver(this);
        this.context=context;
    }

    @Override
    public void handleInput(MotionEvent event, GameWorld gameState, ArrayList<Rect> buttons)
    {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        TowerFactory towerFactory = new TowerFactory();
        if(eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP)
        {
            //check if tower is touched

            if (buttons.get(HUD.PAUSE).contains(x, y))
            {
                // Player pressed the pause button
                // Respond differently depending
                // upon the game's state
                // If the game is not paused
                if (!gameState.getPaused())
                {
                    // Pause the game
                    gameState.pause();
                }
                // If game is over start a new game
                else if (gameState.getGameOver())
                {
                    gameState.startNewGame();
                }
                // Paused and not game over
                else if (gameState.getPaused()
                        && !gameState.getGameOver())
                {
                    gameState.resume();
                }
                gameState.range=null;
            }
            else if (buttons.get(HUD.PlasmaTower).contains(x, y) && !gameState.getPaused())
            {
                if(gameState.getCash()>=50){
                    gameState.setmPlacing();
                    gameState.setmTowerType(HUD.PlasmaTower);
                    gameState.loseCash(50);
                }
                gameState.range=null;
            }
            else if (gameState.getmPlacing() && !gameState.getPaused())
            { //place tower
                gameState.closemPlacing();
                gameState.addTower(towerFactory.getTower (Tower.TowerType.PLASMA,context,new Point(x-(Tower.towerSize/2),y-(Tower.towerSize/2))));
                gameState.range=null;
            }
            else if (gameState.mTowers != null)
            {

                Iterator<Tower> towerIterator = gameState.mTowers.iterator();
                while(towerIterator.hasNext())
                {
                    Tower currentTower = towerIterator.next();
                    if (currentTower.contains(new Point (x,y))){
                        gameState.range=new Circle(currentTower.mLocation, currentTower.mTowerData.mRange);
                    }
                }

            }else
            {
                gameState.range=null;
            }
        }
    }
}