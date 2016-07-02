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

package com.game.prayansh.ultimatetictactoe.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;
import com.game.prayansh.ultimatetictactoe.models.Board;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.models.Move;
import com.game.prayansh.ultimatetictactoe.ui.BoardView;
import com.game.prayansh.ultimatetictactoe.ui.CellView;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;
import com.game.prayansh.ultimatetictactoe.ui.Theme;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prayansh on 16-07-02.
 */
public class GameActivity extends AppCompatActivity {

    @BindView(R.id.bg)
    View background;
    @BindView(R.id.board)
    BoardView gameBoard;
    @BindView(R.id.tvInfo)
    TextView info;
    @BindView(R.id.bRestart)
    Button restart;
    @BindView(R.id.bUndo)
    Button undo;
    private View.OnClickListener cellTouchListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_activity);
        ButterKnife.bind(this);
        cellTouchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView(v);
            }
        };
        setupThemeAndViews();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.gameplay_activity);
        setupThemeAndViews();
    }

    private void clickView(View v) {
        CellView cv = (CellView) v;
        Game game = GameUI.getInstance().getGame();
        if (game.getContextBoardIndex() == -1)
            game.setContextBoard(cv.getBlock());
        else if (game.getContextBoardIndex() != cv.getBlock()) {
            //TODO Show Message for Invalid Move
            Toast.makeText(getApplicationContext(), "Invalid Context", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Move m = game.playMove(cv.getCell());
            cv.mark(game.getPlayer());
            checkWins();
        } catch (InvalidMoveException | IllegalStateException e) {
            //TODO Show Message for Invalid Move
            Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
        } catch (GameOverException e) {
            //TODO Show Message for Game Over
            Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkWins() {
        Game game = GameUI.getInstance().getGame();
        int i = 0;
        for (Board b : game.getBoards()) {
            if (b.solved()) {
                boolean cross = (b.winner() == CellVal.X);
                ((BoardView) gameBoard.getChildAt(i)).setWinner(cross);
            }
            i++;
        }
    }

    private void setupThemeAndViews() {
        Theme theme = ThemeManager.getPrimary(getApplicationContext());
        // Setup views
        background.setBackgroundResource(theme.getBackground());
        info.setTextColor(theme.getTextColor());
        restart.setTextColor(theme.getColor());
        undo.setTextColor(theme.getColor());

        // Setup Board
        for (int i = 0; i < gameBoard.getMaxChildren(); i++) {
            BoardView bv = new BoardView(getApplicationContext());
            bv.setBorderPaint(theme.getGridColor());
            gameBoard.addView(buildEmptyBoard(bv, i, theme));
        }
    }

    private BoardView buildEmptyBoard(BoardView bv, int block, Theme theme) {
        for (int j = 0; j < gameBoard.getMaxChildren(); j++) {
            CellView cv = new CellView(getApplicationContext());
            cv.setBlock(block);
            cv.setCell(j);
            //Set up view resources
            cv.setImageResource(theme.getBlank());
            cv.setCircleResource(theme.getCircle(), theme.getColorCircle());
            cv.setCrossResource(theme.getCross(), theme.getColorCross());
            cv.setOnClickListener(cellTouchListener);
            bv.addView(cv);
        }
        return bv;
    }
}
