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

/**
 * Created by Prayansh on 16-06-30.
 */
public class TTTStack {
    private static final int MAX_CAPACITY = 81;
    private int top;
    private int contextIndex;
    private Move[] data;
    private boolean freeHit;

    public TTTStack() {
        top = -1;
        contextIndex = -1;
        freeHit = true;
        data = new Move[MAX_CAPACITY];
    }

    public int top() {
        return top;
    }

    public boolean push(Move m) {
        freeHit = false;
        if (m.getBoardNo() == contextIndex)
            data[++top] = m;
        else return false;
        return robust();
    }

    public Move pop() {
        return data[top--];
    }

    public Move peek() {
        return data[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean robust() {
        if (top == -1 || top == 0)
            return true;
        boolean flag = true;
        for (int i = 0; i < top - 1; i++) {
            if (data[i].getBoardNo() != data[i + 1].getCellNo())
                flag = false;
        }
        return flag;
    }

    public int getContextIndex() {
        return contextIndex;
    }

    public boolean getFlag() {
        return freeHit;
    }

    public void setContextIndex(int contextIndex) {
        this.contextIndex = contextIndex;
        freeHit = true;
    }
}

