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

/**
 * Created by Prayansh on 16-07-02.
 */
public class Theme {
    private final int textColor;
    private final int gridColor;
    private final int background;
    private final int cross;
    private final int circle;
    private final int color;
    private final int blank;

    public Theme(int gridColor, int background,
                 int cross, int circle, int color, int blank, int textColor) {
        this.gridColor = gridColor;
        this.background = background;
        this.cross = cross;
        this.circle = circle;
        this.color = color;
        this.blank = blank;
        this.textColor = textColor;
    }

    public int getGridColor() {
        return gridColor;
    }

    public int getBackground() {
        return background;
    }

    public int getCross() {
        return cross;
    }

    public int getCircle() {
        return circle;
    }

    public int getColor() {
        return color;
    }

    public int getBlank() {
        return blank;
    }

    public int getTextColor() {
        return textColor;
    }
}
