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

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.exceptions.BoardSolvedException;
import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidBlockException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;
import com.game.prayansh.ultimatetictactoe.models.Board;
import com.game.prayansh.ultimatetictactoe.models.Cell;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.models.Move;
import com.game.prayansh.ultimatetictactoe.ui.BoardView;
import com.game.prayansh.ultimatetictactoe.ui.CellView;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.OnClick;

/**
 * Created by Prayansh on 16-07-12.
 */
public class SinglePlayerGameActivity extends GameActivity {

    private static final long AI_MOVE_DURATION = 500;
    final Handler handler = new Handler();

    private void toggleInteractions(boolean enable) {
        for (int i = 0; i < gameBoard.getMaxChildren(); i++) {
            BoardView bv = (BoardView) gameBoard.getChildAt(i);
            for (int j = 0; j < bv.getMaxChildren(); j++) {
                bv.getChildAt(j).setClickable(enable);
            }
        }
        undo.setClickable(enable);
        restart.setClickable(enable);
    }

    Runnable pcMove = new Runnable() {
        @Override
        public void run() {
            aiMove();
            toggleInteractions(true);
        }
    };

    protected void clickView(View v) {
        CellView cv = (CellView) v;
        Game game = GameUI.getInstance().getGame();
        CellVal player = game.getPlayer();
        boolean movePlayed = false;
        //TODO create free hit message

        try {
            Move m = game.playMove(cv.getBlock(), cv.getCell());
            cv.mark(player);
            movePlayed = true;
        } catch (InvalidMoveException e) {
            Toast.makeText(getApplicationContext(), "You can't play there", Toast.LENGTH_SHORT).show();
            movePlayed = false;
        } catch (GameOverException e) {
            cv.mark(player);
            buildGameOverDialog(e.getWinner());
            movePlayed = false;
        } catch (InvalidBlockException e) {
            movePlayed = false;
            Toast.makeText(getApplicationContext(),
                    "You have to play on " + e.getContextIndex() + " board", Toast.LENGTH_SHORT).show();
        } catch (BoardSolvedException e) {
            movePlayed = false;
            Toast.makeText(getApplicationContext(), "You can't play on solved board", Toast.LENGTH_SHORT).show();
        } finally {
            checkWins();
            updatePlayerInfo();
            highlightContextBoards();
        }
        if (movePlayed) {
            toggleInteractions(false);
            handler.postDelayed(pcMove, AI_MOVE_DURATION);
        }
    }

    private void aiMove() {
        Game game = GameUI.getInstance().getGame();
        CellVal player = game.getPlayer();
        boolean movePlayed = false;
        int block = 0, cell = 0;
        Random randomizer = new Random();
        while (!movePlayed) {
            try {
                if (game.getContextBoardIndex() == -1)
                    block = randomizer.nextInt(9);
                else
                    block = game.getContextBoardIndex();
                cell = cellGenAdvanced(block, player);
                Move m = game.playMove(block, cell);
                movePlayed = true;
            } catch (InvalidMoveException | InvalidBlockException | BoardSolvedException e) {
                movePlayed = false;
            } catch (GameOverException e) {
                buildGameOverDialog(e.getWinner());
                movePlayed = true;
            }
        }
        ((CellView) ((BoardView) gameBoard.getChildAt(block)).getChildAt(cell)).mark(player);
        checkWins();
        updatePlayerInfo();
        highlightContextBoards();
    }

    @Override
    @OnClick(R.id.bUndo)
    public void undoMove() {
        super.undoMove();
        super.undoMove();
    }

    private int cellGen(int block, CellVal player) {
        Board board = GameUI.getInstance().getGame().getBoards()[block];
        int defaultScore = board.getBoardScoreForPlayer(player);
        int index = -1;
        for (int i = 0; i < 9; i++) {
            Cell c = board.cellAt(i);
            if (c.getPlayer() == CellVal.B) {
                if (index == -1)
                    index = i;
                Board newBoard = new Board(board);
                newBoard.setCellAt(i, player);
                int newScore = newBoard.getBoardScoreForPlayer(player);
                if (defaultScore < newScore && !GameUI.getInstance().getGame().getBoards()[i]
                        .solved()) {
                    defaultScore = newScore;
                    index = i;
                }
            }
        }
        return index;
    }

    private int cellGenAdvanced(int block, CellVal player) {
        Board board = GameUI.getInstance().getGame().getBoards()[block];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            Cell c = board.cellAt(i);
            if (c.getPlayer() == CellVal.B) {
                Board newBoard = new Board(board);
                newBoard.setCellAt(i, player);
                int newScore = newBoard.getBoardScoreForPlayer(player);
                map.put(i, newScore);
            }
        }
        LinkedHashMap<Integer, Integer> sortedByValue = sort(map);

        int index = -1;
        Board[] boards = GameUI.getInstance().getGame().getBoards();
        Set<Map.Entry<Integer, Integer>> mappings = sortedByValue.entrySet();
        CellVal otherPlayer = (player == CellVal.X) ? CellVal.O : CellVal.X;
        for (Map.Entry<Integer, Integer> mapping : mappings) {
            index = mapping.getKey();
            if (boards[index].solved() && boards[index].winner() == otherPlayer) {
                continue;
            } else if (boards[index].getBoardScoreForPlayer(player) < boards[index]
                    .getBoardScoreForPlayer(otherPlayer)) {
                continue;
            } else {
                break;
            }
        }

        return index;
    }

    private LinkedHashMap<Integer, Integer> sort(HashMap map) {
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        Comparator<Map.Entry<Integer, Integer>> valueComparator = new Comparator<Map.Entry<Integer, Integer>>
                () {
            @Override
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                int v1 = e1.getValue();
                int v2 = e2.getValue();
                return (v1 < v2) ? 1 : -1;
            }
        };
        // Sort method needs a List, so let's first convert Set to List in Java
        List<Map.Entry<Integer, Integer>> listOfEntries = new ArrayList<>(entries);
        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<Integer, Integer> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        // copying entries from List to Map
        for (Map.Entry<Integer, Integer> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}
