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

package com.game.prayansh.ultimatetictactoe.exceptions;

import com.game.prayansh.ultimatetictactoe.models.CellVal;

/**
 * Created by Prayansh on 16-05-12.
 */
public class GameOverException extends Throwable {
    CellVal winner;

    public CellVal getWinner() {
        return winner;
    }

    public GameOverException(String s, CellVal winner) {
        super(s);
        this.winner = winner;
    }
}
