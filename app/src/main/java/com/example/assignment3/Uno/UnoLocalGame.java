package com.example.assignment3.Uno;

import com.example.assignment3.Uno.UnoCard.UnoDrawCardAction;
import com.example.assignment3.Uno.UnoCard.UnoPlayCardAction;
import com.example.assignment3.Uno.UnoCard.UnoSelectColorAction;
import com.example.assignment3.Uno.UnoCard.UnoSpecialCard;

import GameFramework.LocalGame;
import GameFramework.actionMessage.GameAction;
import GameFramework.players.GamePlayer;

public class UnoLocalGame extends LocalGame {
    private final UnoGameState gameState;

    public UnoLocalGame() {
        gameState = new UnoGameState();
    }
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new UnoGameState(gameState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == gameState.getPlayerID();
    }

    // apply
    @Override
    protected String checkIfGameOver() {
        for(int i = 0; i < gameState.getPlayerNum(); i++) {
            if (gameState.getHandArray().get(i).size() == 0){
                return "Congratulations Player " + i+1 + " for winning the game.";
            }
        }
        return null;
    }

    /**
     *
     * @param action - the action received from a player
     *
     * @return if the move was valid
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof UnoPlayCardAction){
            if(gameState.playCard(((UnoPlayCardAction) action).index)){
                if(gameState.getSpcInPlay() == UnoSpecialCard.SKIP){
                    gameState.skip();
                }
                else if(gameState.getSpcInPlay() == UnoSpecialCard.DRAWTWO){
                    gameState.drawTwo();
                }
                else if(gameState.getSpcInPlay() == UnoSpecialCard.REVERSE){
                    gameState.reverse();
                }
                else if(gameState.getSpcInPlay() == UnoSpecialCard.DRAWFOUR){
                    gameState.drawFour();
                }
                return true;
            }
            else {
                return false;
            }

        }
        else if (action instanceof UnoDrawCardAction){
            gameState.drawCard();
            gameState.endTurn();
            return true;
        }
        else if (action instanceof UnoSelectColorAction){
            gameState.selectColor(((UnoSelectColorAction) action).selectedColor);
            return true;
        }
        return false;
    }
}
