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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;
import com.game.prayansh.ultimatetictactoe.ui.Theme;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Prayansh on 16-07-02.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bg)
    View background;
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.ivCircle)
    ImageView ivCircle;
    @BindView(R.id.bSingle)
    Button single;
    @BindView(R.id.bLocal)
    Button local;
    @BindView(R.id.bSettings)
    Button settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setupTheme();
    }

    private void setupTheme() {
        Theme mTheme = ThemeManager.getTheme();
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
        // Setting up views
        background.setBackgroundResource(mTheme.getBackground());
        ivCross.setImageResource(mTheme.getCross());
        ivCircle.setImageResource(mTheme.getCircle());
        single.setTextColor(mTheme.getColor());
        single.setTypeface(typeFace);
        single.setBackgroundResource(mTheme.getBtn());
        local.setTextColor(mTheme.getColor());
        local.setTypeface(typeFace);
        local.setBackgroundResource(mTheme.getBtn());
        settings.setTextColor(mTheme.getColor());
        settings.setTypeface(typeFace);
        settings.setBackgroundResource(mTheme.getBtn());
    }

    @OnClick(R.id.bSingle)
    public void newSingleGame() {
        GameUI.getInstance().newGame();
        Intent newGameIntent = new Intent(this, SinglePlayerGameActivity.class);
        startActivity(newGameIntent);
    }

    @OnClick(R.id.bLocal)
    public void newLocalGame() {
        GameUI.getInstance().newGame();
        Intent newGameIntent = new Intent(this, MultiPlayerGameActivity.class);
        startActivity(newGameIntent);
    }

    @OnClick(R.id.bSettings)
    public void openSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTheme();
    }
}
