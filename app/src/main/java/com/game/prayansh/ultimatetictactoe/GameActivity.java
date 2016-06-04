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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.models.Board;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_turn)
    TextView mTurnTv;
    @BindView(R.id.settings)
    ImageButton mSettings;
    @BindView(R.id.restart)
    ImageButton newGame;
    @BindViews({
            R.id.c00, R.id.c01, R.id.c02,
            R.id.c10, R.id.c11, R.id.c12,
            R.id.c20, R.id.c21, R.id.c22})
    List<ImageView> boards;
    @BindViews({
            R.id.h00, R.id.h01, R.id.h02,
            R.id.h10, R.id.h11, R.id.h12,
            R.id.h20, R.id.h21, R.id.h22})
    List<View> highlights;

    private static final String TAG = "GameActivity#U";
    private static final String STATE_GAME = "game";
    public static final String STATE_PLAYER = "player";
    public static final String STATE_BOARDS = "boards";
    public static final String STATE_BITMAPS = "bitmaps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        ButterKnife.bind(this);
        setupGame();
        highlight();
        Toast.makeText(this, "Game Ready", Toast.LENGTH_LONG).show();
    }

    private void highlight() {
        for (View v : highlights) {
            v.setVisibility(View.GONE);
        }
        int index = GameUI.getInstance().getGame().getContextBoard();
        if (index != -1)
            highlights.get(index).setVisibility(View.VISIBLE);
        Log.i(TAG, "Highlighted " + (index + 1) + " board");
    }

    private void setupGame() {
        Bitmap[] bitmaps = new Bitmap[9];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),
                    R.drawable.placeholder_holder);// TODO change bitmap
            boards.get(i).setImageBitmap(bitmaps[i]);
        }
        mTurnTv.setText(Utils.getPlayerText());
        GameUI.getInstance().setGameBoards(bitmaps);
        Log.d(TAG, "Game is ready");
    }

    @OnClick({
            R.id.c00, R.id.c01, R.id.c02,
            R.id.c10, R.id.c11, R.id.c12,
            R.id.c20, R.id.c21, R.id.c22})
    public void maximiseBoard(ImageView iv) {
        int index = Integer.parseInt(iv.getContentDescription().toString());
        Log.d(TAG, "Maximising " + (index + 1) + " board");
        Intent intent = new Intent(this, BoardFragment.class);
        intent.putExtra(getString(R.string.board_data), index);
        startActivityForResult(intent, MyApplication.BOARD_REQUEST_CODE);
    }

    @OnClick(R.id.restart)
    public void restart() {
        GameUI.getInstance().newGame();
        setupGame();
        highlight();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        if (extras != null)
            if (extras.getBoolean(getString(R.string.any_changes))) {
                updateBoard();
                Log.d(TAG, "Changes made");
            }
    }

    private void updateBoard() {
        int i = 0;
        for (Bitmap bm : GameUI.getInstance().getGameBoards()) {
            boards.get(i).setImageBitmap(bm);
            i++;
        }
        for (i = 0; i < GameUI.getInstance().getGame().getBoards().length; i++) {
            if (GameUI.getInstance().getGame().getBoards()[i].solved())
                boards.get(i).setClickable(false);

        }
        mTurnTv.setText(Utils.getPlayerText());
        highlight();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }


    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            toggleHideyBar();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBundle(STATE_GAME, GameUI.getInstance().getGame().toBundle());
        savedInstanceState.putSerializable(STATE_BITMAPS, GameUI.getInstance().getGameBoards());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreGame(savedInstanceState);
    }

    private void restoreGame(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Bitmap[] gameBoards = (Bitmap[]) savedInstanceState.getSerializable(STATE_BITMAPS);
            Bundle bundle = savedInstanceState.getBundle(STATE_GAME);
            if (bundle == null)
                return;

            CellVal player = (CellVal) bundle.getSerializable(STATE_PLAYER);
            Board[] boards = (Board[]) bundle.getParcelableArray(STATE_BOARDS);

            GameUI.recreate(new Game(boards, player), gameBoards);

            mTurnTv.setText(Utils.getPlayerText());
            GameUI.getInstance().setGameBoards(gameBoards);
            highlight();
            Log.d(TAG, "Game Resumed");
        } else {
            Log.e(TAG, "Saved Instance State Empty");
        }

    }
}
