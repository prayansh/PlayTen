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

package com.game.prayansh.ultimatetictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.game.prayansh.ultimatetictactoe.ui.BoardView;
import com.game.prayansh.ultimatetictactoe.ui.CellView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prayansh on 16-06-26.
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.view2)
    BoardView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);
        setupBoard();
    }

    private void setupBoard() {
        switch (mBoardView.getType()) {
            case 0:
                buildEmptyBoard(mBoardView);
                break;
            case 1:
                for (int i = 0; i < mBoardView.getMaxChildren(); i++) {
                    BoardView bv = new BoardView(getApplicationContext());
                    mBoardView.addView(buildEmptyBoard(bv));
                }
                break;
        }
    }

    private BoardView buildEmptyBoard(BoardView bv) {
        for (int j = 0; j < mBoardView.getMaxChildren(); j++) {
            CellView cv = new CellView(getApplicationContext());
            bv.addView(cv);
        }
        return bv;
    }

}
