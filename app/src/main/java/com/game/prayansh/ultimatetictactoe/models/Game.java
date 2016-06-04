/*
 * Copyright 2016 Prayansh Srivastava
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.game.prayansh.ultimatetictactoe.models;


import android.os.Bundle;

import com.game.prayansh.ultimatetictactoe.GameActivity;
import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;

public class Game {
    private CellVal player;
    private Board[] boards;
    private Board equivalent; // The board equivalent of the game_board
    private int contextBoard;

    public Game() {
        boards = new Board[9];
        equivalent = new Board();
        for (int i = 0; i < boards.length; i++) {
            boards[i] = new Board();
        }
        contextBoard = -1;
        player = CellVal.X;
    }

    public Game(Board[] boards, CellVal player) {
        this.boards = boards;
        this.player = player;
        updateEquivalentBoard();
    }

    public Board getInContextBoard() {
        return boards[contextBoard];
    }

    public int getContextBoard() {
        return contextBoard;
    }

    public CellVal getPlayer() {
        return player;
    }

    /**
     * To be called for first move to decide starting context board
     */
    public void setContextBoard(int val) {
        contextBoard = val;
    }

    /**
     * Place the player at position in context board
     * update equivalent board
     * produce the next context board, if valid move
     * contextboard = -1 if free hit
     * togglePlayer
     */
    public int playMove(int position) throws InvalidMoveException, GameOverException {
        boolean valid = getInContextBoard().setCellAt(position, player);
        if (!valid) {
            throw new InvalidMoveException("Invalid Move for Player " + player.name() + ":" + position);
        }
        updateEquivalentBoard();
        contextBoard = position;
        if (getInContextBoard().solved()) {
            int index = contextBoard;
            contextBoard = -1;
        }
        if (checkWinner())
            throw new GameOverException("Player " + player.name() + " has won", player);
        togglePlayer();
        return contextBoard;
    }

    private void togglePlayer() {
        switch (player) {
            case X:
                player = CellVal.O;
                break;
            case O:
                player = CellVal.X;
                break;
        }
    }

    public boolean checkWinner() {
        return (equivalent.solved() && equivalent.winner() == player);
    }

    private void updateEquivalentBoard() {
        for (int i = 0; i < 9; i++) {
            if (boards[i].solved()) {
                CellVal winner = boards[i].winner();
                equivalent.setCellAt(i, winner);
            }
        }
    }

    public Board[] getBoards() {
        return boards;
    }

    public Board getEquivalent() {
        return equivalent;
    }

    public Bundle toBundle() {
        Bundle thisInstance = new Bundle();
        thisInstance.putSerializable(GameActivity.STATE_PLAYER, player);
        thisInstance.putParcelableArray(GameActivity.STATE_BOARDS, boards);
        return thisInstance;
    }
}
