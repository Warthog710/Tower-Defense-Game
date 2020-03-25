package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class UIController implements InputObserver {
    Context context;
    public UIController(GameEngineBroadcaster b, Context context){
        b.addObserver(this);
        this.context=context;
    }
    @Override
    public void handleInput(MotionEvent event, GameWorld gameState, ArrayList<Rect> buttons) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        if(eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP) {

            if (buttons.get(HUD.PAUSE).contains(x, y)){
                // Player pressed the pause button
                // Respond differently depending
                // upon the game's state
                // If the game is not paused
                if (!gameState.getPaused()) {
                    // Pause the game
                    gameState.pause();
                }
                // If game is over start a new game
                else if (gameState.getGameOver()) {
                    gameState.startNewGame();
                }
                // Paused and not game over
                else if (gameState.getPaused()
                        && !gameState.getGameOver()) {
                    gameState.resume();
                }
            }else if (buttons.get(HUD.PlasmaTower).contains(x, y) && !gameState.getPaused()){
                gameState.setmPlacing();
                gameState.setmTowerType(HUD.PlasmaTower);
            }else if (gameState.getmPlacing() && !gameState.getPaused()){ //place tower
                gameState.closemPlacing();
                gameState.addTower(new PlasmaTower (context,new Point(x-(Tower.towerSize/2),y-(Tower.towerSize/2))));

            }
        }
    }
}
