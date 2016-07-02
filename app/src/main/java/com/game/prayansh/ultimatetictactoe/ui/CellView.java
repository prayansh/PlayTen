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

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.models.CellVal;

/**
 * Created by Prayansh on 16-06-26.
 */
public class CellView extends ImageView {

    private int crossRes, circleRes, crossTint, circleTint;
    private int mResource;
    private int block, cell;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.CENTER_CROP);
        cell = block = -1;
        int padding = context.getResources().getDimensionPixelSize(R.dimen.cell_margin);
        setPadding(padding, padding, padding, padding);
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getCell() {
        return cell;
    }

    public int getBlock() {
        return block;
    }

    private void setCross() {
        mResource = crossRes;
        setColorFilter(crossTint);
        redraw();
    }

    private void setCircle() {
        mResource = circleRes;
        setColorFilter(circleTint);
        redraw();
    }

    public void mark(CellVal player){
        switch (player){
            case X:
                setCross();
                break;
            case O:
                setCircle();
                break;
            case B:
                // Redundant code but for completion's sake
                setEmpty();
                break;
        }
    }

    private void setEmpty() {
        throw new AssertionError();
    }

    private void redraw() {
        setImageResource(mResource);
    }

    public void setCircleResource(int circle, int colorCircle) {
        circleRes = circle;
        circleTint = colorCircle;
    }

    public void setCrossResource(int cross, int colorCross) {
        crossRes = cross;
        crossTint = colorCross;
    }
}
