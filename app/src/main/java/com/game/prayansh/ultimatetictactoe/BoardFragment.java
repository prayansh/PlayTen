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

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;
import com.game.prayansh.ultimatetictactoe.models.Board;
import com.game.prayansh.ultimatetictactoe.models.Cell;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Prayansh on 16-05-13.
 */
public class BoardFragment extends AppCompatActivity {
    @BindView(R.id.winner)
    ImageView decision;

    @BindViews({
            R.id.c00, R.id.c01, R.id.c02,
            R.id.c10, R.id.c11, R.id.c12,
            R.id.c20, R.id.c21, R.id.c22,})
    List<TextView> cells;

    @BindView(R.id.tv_turn)
    TextView tvTurn;

    @BindView(R.id.back)
    ImageButton back;

    int contextIndex;
    Board board;
    Game mGame;

    private static final String TAG = "BoardFragment#U";

    static final ButterKnife.Action<View> CLEAR = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            TextView tv = (TextView) view;
            tv.setText("");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_fragment);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            contextIndex = getIntent().getExtras().getInt(getString(R.string.board_data));
        } else {
            contextIndex = 0;
        }
        board = GameUI.getInstance().getGame().getBoards()[contextIndex];
        mGame = GameUI.getInstance().getGame();
        setupBoard();
    }

    private void setupBoard() {
        //Clear Board
        ButterKnife.apply(cells, CLEAR);
        fillBoard();
        tvTurn.setText(Utils.getPlayerText());
        Log.i(TAG, "Board#" + (contextIndex + 1) + " is setup");
    }

    private void fillBoard() {
        int i = 0;
        for (Cell c : board) {
            if (c.getVal() != CellVal.B)
                cells.get(i).setText(c.getVal().name());
            i++;
        }
        checkWin();
    }

    @OnClick({
            R.id.c00, R.id.c01, R.id.c02,
            R.id.c10, R.id.c11, R.id.c12,
            R.id.c20, R.id.c21, R.id.c22,})
    public void play(TextView tv) {
        try {
            if (mGame.getContextBoard() == -1)
                mGame.setContextBoard(contextIndex);
            if (contextIndex != mGame.getContextBoard())
                Toast.makeText(this, "You can't Play here", Toast.LENGTH_SHORT).show();
            else {
                int index = Integer.parseInt(tv.getContentDescription().toString());
                Log.d(TAG, "Played at cell " + (index + 1));
                mGame.playMove(index);
            }
        } catch (InvalidMoveException e) {
            Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Invalid Move made");
        } catch (GameOverException e) {
            Toast.makeText(this, "Game Over! " + e.getWinner() + " has won", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Game Over! " + e.getWinner() + " has won");
        } finally {
            fillBoard();
            checkWin();
            Log.i(TAG, "Board updated");
        }
        delayedMinimize(500);
    }

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            minimize(true);
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedMinimize(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void minimize(boolean anyChanges) {
        snap();
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.any_changes), anyChanges);
        setResult(RESULT_OK, intent);
        Log.d(TAG, "Minimizing " + (contextIndex + 1) + " board");
        finish();
    }

    private void checkWin() {
        //Check if win
        //Add Player logo
        //Opacity of behind view is 50
        if (board.solved()) {
            switch (board.winner()) {
                case X:
                    decision.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                            R.drawable.cross));
                    break;
                case O:
                    decision.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                            R.drawable.circle));
                    break;
            }
            findViewById(R.id.bd).setAlpha(0.5f);
        }
    }

    private void snap() {
        GameUI.getInstance().replaceBoard(contextIndex, Utils.screenShot(findViewById(R.id.contentView)));
    }

    @Override
    @OnClick(R.id.back)
    public void onBackPressed() {
        minimize(true);
    }
}
