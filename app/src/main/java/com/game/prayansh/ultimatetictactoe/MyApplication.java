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

import android.app.Application;
import android.content.SharedPreferences;

import com.game.prayansh.ultimatetictactoe.ui.GameUI;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

/**
 * Created by Prayansh on 16-05-13.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
        initTheme();
    }

    private void initTheme() {
        //Handle theme changes with sharepreferences
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
        int theme = preferences.getInt(getString(R.string.prefs_theme_key), 0);
        switch (theme) {
            case 0:
                ThemeManager.setMinimal(getApplicationContext());
                break;
            case 1:
                ThemeManager.setMarvel(getApplicationContext());
                break;
            case 2:
                ThemeManager.setDC(getApplicationContext());
                break;
            default:
                ThemeManager.setMinimal(getApplicationContext());
                break;
        }
    }

    protected void initSingletons() {
        // Initialize the instance of MySingleton
        GameUI.getInstance();
    }
}
