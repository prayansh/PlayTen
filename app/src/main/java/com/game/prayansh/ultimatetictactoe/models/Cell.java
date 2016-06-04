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

import java.io.Serializable;

/**
 * Created by Prayansh on 2016-05-11.
 */
public class Cell implements Serializable {
    private CellVal val;

    public Cell() {
        val = CellVal.B;
    }

    public void setVal(CellVal val) {
        this.val = val;
    }

    public CellVal getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.name();
    }
}
