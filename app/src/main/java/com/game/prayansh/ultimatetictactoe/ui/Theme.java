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
    private int gridColor;
    private int background;
    private int cross;
    private int colorCross;
    private int circle;
    private int colorCircle;
    private int color;

    public Theme(int gridColor, int background,
                 int cross, int colorCross, int circle, int colorCircle, int color) {
        this.gridColor = gridColor;
        this.background = background;
        this.cross = cross;
        this.colorCross = colorCross;
        this.circle = circle;
        this.colorCircle = colorCircle;
        this.color = color;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getCross() {
        return cross;
    }

    public void setCross(int cross) {
        this.cross = cross;
    }

    public int getColorCross() {
        return colorCross;
    }

    public void setColorCross(int colorCross) {
        this.colorCross = colorCross;
    }

    public int getCircle() {
        return circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public int getColorCircle() {
        return colorCircle;
    }

    public void setColorCircle(int colorCircle) {
        this.colorCircle = colorCircle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
