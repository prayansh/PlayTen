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

package com.game.prayansh.ultimatetictactoe.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Iterator;

/**
 * Created by Prayansh on 2016-05-11.
 * INVARIANT: Size is always 9
 */

public class Board implements Iterable<Cell>, Parcelable {
    private Cell[] cells;
    private int score[];

    public Board() {
        cells = new Cell[9];
        score = new int[8];
        for (int i = 0; i < 9; i++)
            cells[i] = new Cell();
    }

    public Board(Cell[] cells) {
        this();
        for (int i = 0; i < cells.length; i++) {
            setCellAt(i, cells[i].getVal());
        }
    }

    public Board(CellVal[] cells) {
        this();
        for (int i = 0; i < cells.length; i++) {
            setCellAt(i, cells[i]);
        }
    }

    public Board(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Set the cell at index to the player value
     *
     * @return true if new val is set, false if invalid cell
     */
    public boolean setCellAt(int index, CellVal player) {
        checkRobust();
        if (cellAt(index).getVal() != CellVal.B)
            return false;
        cells[index].setVal(player);
        score[index / 3] += player.getVal();
        score[3 + index % 3] += player.getVal();
        if ((index / 3) == (index % 3)) score[2 * 3] += player.getVal();
        if (3 - 1 - (index % 3) == (index / 3)) score[2 * 3 + 1] += player.getVal();
        return true;
    }

    public boolean setCellAt(int row, int column, CellVal player) {
        return setCellAt(row * 3 + column, player);
    }

    public Cell cellAt(int row, int column) {
        return cellAt(row * 3 + column);
    }

    public Cell cellAt(int index) {
        checkRobust();
        if (index > 9)
            throw new AssertionError("Position not valid");
        return cells[index];
    }


    /**
     * Next Best Position to play at
     *
     * @param cv player
     * @return next best position
     */
    public Cell bestPos(CellVal cv) {
        if (cv.equals(CellVal.B))
            throw new AssertionError("Invalid Player");
        return new Cell();//TODO
    }

    public boolean solved() {
        return (solved(CellVal.X) || solved(CellVal.O));
    }

    /**
     * PRECONDITION:solved()
     *
     * @return
     */
    public CellVal winner() {
        if (!solved())
            throw new AssertionError("Winner has not been decided");
        if (solved(CellVal.X))
            return CellVal.X;
        return CellVal.O;
    }

    private boolean solved(CellVal player) {
        checkRobust();
        int check = player.getVal() * 3;
        for (int i = 0; i < score.length; i++) {
            if (score[i] == check)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s += (cells[i * 3 + j].getVal() + " ");
            }
            s += "\n";
        }
        return s;
    }

    public void checkRobust() {
        if (cells.length != 9)
            throw new AssertionError("Board is invalid");
    }

    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(cells);
        dest.writeIntArray(score);
    }

    private void readFromParcel(Parcel in) {
        cells = (Cell[]) in.readSerializable();
        in.readIntArray(score);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        public Board[] newArray(int size) {
            return new Board[size];
        }

    };

    private class CellIterator implements Iterator<Cell> {
        int current;

        public CellIterator() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < cells.length;
        }

        @Override
        public Cell next() {
            return cells[current++];
        }

        @Override
        public void remove() {
            throw new AssertionError("Cant delete a cell");
        }
    }
}

