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

/**
 * Created by Prayansh on 16-06-30.
 */
public class Move implements Parcelable {
    private int boardNo, cellNo;
    private boolean freeHit;

    public Move(int boardNo, int cellNo, boolean freeHit) {
        this.boardNo = boardNo;
        this.cellNo = cellNo;
        this.freeHit = freeHit;
    }

    public Move(Parcel p) {
        this.boardNo = p.readInt();
        this.cellNo = p.readInt();
        this.freeHit = p.readByte() != 0;
    }

    public static final Creator<Move> CREATOR = new Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    public int getBoardNo() {
        return boardNo;
    }

    public int getCellNo() {
        return cellNo;
    }

    public boolean isFreeHit() {
        return freeHit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(boardNo);
        dest.writeInt(cellNo);
        dest.writeByte((byte) (freeHit ? 1 : 0));
    }

    @Override
    public String toString() {
        return boardNo + "," + cellNo + ":" + freeHit;
    }
}

