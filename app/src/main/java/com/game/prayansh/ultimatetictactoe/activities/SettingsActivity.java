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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.ui.Theme;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Prayansh on 16-07-07.
 */
public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.cross)
    ImageView cross;
    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.tMinimal)
    Button minimal;
    @BindView(R.id.tClassic)
    Button classic;
    @BindView(R.id.tMarvel)
    Button marvel;
    @BindView(R.id.bg)
    View bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        initTheme();
    }

    @OnClick(R.id.tMinimal)
    public void setMinimal() {
        ThemeManager.setMinimal(getApplicationContext());
        initTheme();
    }

    @OnClick(R.id.tClassic)
    public void setClassic() {
        ThemeManager.setClassic(getApplicationContext());
        initTheme();
    }

    @OnClick(R.id.tMarvel)
    public void setMarvel() {
        ThemeManager.setMarvel(getApplicationContext());
        initTheme();
    }

    private void initTheme() {
        Theme theme = ThemeManager.getTheme();
        circle.setImageResource(theme.getCircle());
        cross.setImageResource(theme.getCross());
        bg.setBackgroundResource(theme.getBackground());
    }

}
