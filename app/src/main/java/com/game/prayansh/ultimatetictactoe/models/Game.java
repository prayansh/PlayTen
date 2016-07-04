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

import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;

public class Game {
    private Board[] gameBoards;
    private Board equivalentBoard;
    private TTTStack moves;

    public Game() {
        initBoards();
        moves = new TTTStack();
    }

    public Game(TTTStack stack) {
        moves = stack;
        updateGameBoard();
    }

    private void initBoards() {
        gameBoards = new Board[9];
        equivalentBoard = new Board();
        for (int i = 0; i < gameBoards.length; i++) {
            gameBoards[i] = new Board();
        }
    }

    private void updateGameBoard() {
        initBoards();
        int i = 0;
        for (Move m : moves) {
            CellVal player = (i % 2 == 0) ? CellVal.X : CellVal.O;
            gameBoards[m.getBoardNo()].setCellAt(m.getCellNo(), player);
            i++;
        }
        updateEquivalentBoard();
    }

    /**
     * Returns board index to play on
     *
     * @return -1 for free hit
     */
    public Board getContextGameBoard() {
        return gameBoards[getContextBoardIndex()];
    }

    /**
     * Returns board index to play on
     *
     * @return -1 for free hit
     */
    public int getContextBoardIndex() {
        return moves.getContextIndex();
    }

    public void setContextBoardIndex(int index) {
        moves.setContextIndex(index);
    }

    public CellVal getPlayer() {
        return (moves.top() % 2 == 0) ? CellVal.O : CellVal.X;
    }

    /**
     * Place the player at position in context board
     * update equivalent board
     * contextboard = -1 if free hit
     *
     * @see - check for contextboard = -1 before calling
     */
    public Move playMove(int position) throws InvalidMoveException, GameOverException {
        CellVal player = getPlayer();
        if (getContextBoardIndex() == -1)
            throw new IllegalStateException("No Context Board");
        boolean valid = getContextGameBoard().setCellAt(position, player);
        Move m = new Move(getContextBoardIndex(), position, moves.getFlag());
        if (!valid || !moves.push(m)) {
            throw new InvalidMoveException("Invalid Move for Player " + player.name() + ":" + position);
        }
        //Move has been added at this point
        if (getContextGameBoard().solved()) {
            CellVal winner = getContextGameBoard().winner();
            equivalentBoard.setCellAt(getContextBoardIndex(), winner);
            setContextBoardIndex(-1);
        }
        if (checkWinner(player))
            throw new GameOverException("Player " + player.name() + " has won", player);
        return m;
    }

    public boolean checkWinner(CellVal player) {
        return (equivalentBoard.solved() && equivalentBoard.winner() == player);
    }

    private void updateEquivalentBoard() {
        for (int i = 0; i < 9; i++) {
            if (gameBoards[i].solved()) {
                CellVal winner = gameBoards[i].winner();
                equivalentBoard.setCellAt(i, winner);
            }
        }
    }

    public Board[] getBoards() {
        return gameBoards;
    }

    public Board getEquivalent() {
        return equivalentBoard;
    }

    public Move undo() {
        Move m = moves.pop();
        if (isStarted()) {
            setContextBoardIndex(moves.peek().getCellNo());
            getBoards()[m.getBoardNo()].clearCellAt(m.getCellNo());
        }
        return m;
    }


    public boolean isStarted() {
        return moves.top() != -1;
    }

    public TTTStack getMoves() {
        return moves;
    }
}
