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

import com.game.prayansh.ultimatetictactoe.R;

/**
 * Created by Prayansh on 16-06-26.
 */
public class CellView extends ImageView {

    public static final int CROSS_RESOURCE = R.drawable.cross;
    public static final int CIRCLE_RESOURCE = R.drawable.circle;
    private int mResource;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.CENTER_CROP);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.cell_margin);
        setPadding(padding, padding, padding, padding);
        setColorFilter(Color.argb(255, 255, 255, 255));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setCircle();
                return false;
            }
        });
    }

    private void setCross() {
        mResource = CROSS_RESOURCE;
        redraw();
    }

    private void setCircle() {
        mResource = CIRCLE_RESOURCE;
        redraw();
    }

    /**
     * @param mark 0 - Cross ; 1 - Circle
     */
    public void mark(int mark) {
        switch (mark) {
            case -1:
                setCircle();
                break;
            case 0:
                setEmpty();
                break;
            case 1:
                setCross();
                break;
        }
    }

    private void setEmpty() {
        throw new AssertionError();
    }

    private void redraw() {
        setImageResource(mResource);
    }

}