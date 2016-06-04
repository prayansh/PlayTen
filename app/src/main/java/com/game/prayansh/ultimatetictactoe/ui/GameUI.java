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

package com.game.prayansh.ultimatetictactoe.ui;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.game.prayansh.ultimatetictactoe.models.Game;

/**
 * Created by Prayansh on 16-05-12.
 */
public class GameUI {
    private static final String BITMAPS = "bitmaps";
    private static final String GAME = "game";
    private Game mGame;
    private Bitmap[] gameBoards; // stores bitmaps of each small board
    private static GameUI gameUI;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private GameUI() {
        newGame();
    }

    private GameUI(Game game, Bitmap[] bitmaps) {
        mGame = game;
        gameBoards = bitmaps;
    }

    public static void recreate(Game game, Bitmap[] bitmaps) {
        gameUI = new GameUI(game, bitmaps);
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     *
     * @return instance of EventLog
     */
    public static GameUI getInstance() {
        if (gameUI == null)
            gameUI = new GameUI();
        return gameUI;
    }


    public void setGameBoards(Bitmap[] gameBoards) {
        this.gameBoards = gameBoards;
    }

    public void replaceBoard(int index, Bitmap bm) {
        gameBoards[index] = bm;
    }

    public void newGame() {
        mGame = new Game();
        gameBoards = new Bitmap[9];
    }

    public Game getGame() {
        return mGame;
    }

    public Bitmap[] getGameBoards() {
        return gameBoards;
    }
}
