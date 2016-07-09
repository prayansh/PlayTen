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
import com.game.prayansh.ultimatetictactoe.models.TTTStack;

/**
 * Created by Prayansh on 16-05-12.
 */
public class GameUI {
    private static final String GAME = "game";
    private Game mGame;
    private static GameUI gameUI;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private GameUI() {
        newGame();
    }

    private GameUI(TTTStack stack) {
        mGame = new Game(stack);
    }

    public static void recreate(TTTStack stack) {
        gameUI = new GameUI(stack);
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

    public void newGame() {
        mGame = new Game();
    }

    public Game getGame() {
        return mGame;
    }
}
